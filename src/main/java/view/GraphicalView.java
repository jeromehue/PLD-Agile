package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import modele.CityMap;
import modele.Intersection;
import modele.Segment;

public class GraphicalView extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private double maxLongitude;
	private double minLongitude;
	private double maxLatitude;
	private double minLatitude;
	private CityMap cityMap;

	public GraphicalView() {
		super();
		maxLongitude = 20;
		minLongitude = 10;
		maxLatitude = 50;
		minLatitude = 3;
		this.setBorder(BorderFactory.createTitledBorder("Vue Graphique"));
		this.setLayout(null);
	}
	
	/**
	 * Method called each this must be redrawn 
	 */
	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		// white background
		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		
		// draw segment
		graphics.setColor(Color.black);
		Intersection i1 = new Intersection( null ,10,11,new ArrayList<Segment>());
		Intersection i2 = new Intersection(null,10,19,new ArrayList<Segment>());
		Intersection i3 = new Intersection( null ,5,15,new ArrayList<Segment>());
		Intersection i4 = new Intersection(null,15,15,new ArrayList<Segment>());
		
		Segment s1 = new Segment(0, " ", i1,  i2);
		Segment s2 = new Segment(0, " ", i3,  i4);
		Segment s3 = new Segment(0, " ", i2,  i3);
		Segment s4 = new Segment(0, " ", i1,  i4);
		
		
		drawSegement(graphics, s1);
		drawSegement(graphics, s2);
		drawSegement(graphics, s3);
		drawSegement(graphics, s4);
		//draw values
		graphics.setColor(Color.black);
		graphics.drawString("Tests -> ici s'affichera la carte", (int)(getWidth()*0.51),(int)(getHeight()*0.52));
		
	}
	
	private void drawSegement(Graphics graphics, Segment s) {
		graphics.drawLine(latitudeToPixel(s.getOrigin().getLatitude()),
						  longitudeToPixel(s.getOrigin().getLongitude()),
						  latitudeToPixel(s.getDestination().getLatitude()),
						  longitudeToPixel(s.getDestination().getLongitude())
		);
	}
	
	private int longitudeToPixel(double longitude ) {
		return (int)((double)getHeight() * ( maxLongitude - longitude) / (maxLongitude - minLongitude));
	}
	
	private int latitudeToPixel(double latitude ) {
		return (int)((double)getWidth() * ( maxLatitude - latitude) / (maxLatitude - minLatitude));
	}
}
