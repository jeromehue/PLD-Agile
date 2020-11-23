package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import modele.CityMap;
import modele.Intersection;
import modele.Segment;
import xml.XMLCityMapParser;
import xml.XMLRequestParser;

public class GraphicalView extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private CityMap cityMap;

	public GraphicalView() {
		super();
		/*maxLongitude = 20;
		minLongitude = 10;
		maxLatitude = 50;
		minLatitude = 3;*/
		this.setBorder(BorderFactory.createTitledBorder("Vue Graphique"));
		this.setLayout(null);

		XMLCityMapParser p = new XMLCityMapParser("src/main/resources/smallMap.xml");
		cityMap = p.parse();
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
		
		//draw the cityMap
		Iterator<Segment> itSegements = cityMap.getSegementsIterator();
		while(itSegements.hasNext())
		{
			drawSegement(graphics,itSegements.next());
		}
		/*Intersection i1 = new Intersection( null ,10,11,new ArrayList<Segment>());
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
		drawSegement(graphics, s4);*/
		//draw values
		graphics.setColor(Color.black);
		//graphics.drawString("Tests -> ici s'affichera la carte", (int)(getWidth()*0.51),(int)(getHeight()*0.52));
		
	}
	
	private void drawSegement(Graphics graphics, Segment s) {
		graphics.drawLine(latitudeToPixel(s.getOrigin().getLatitude()),
						  longitudeToPixel(s.getOrigin().getLongitude()),
						  latitudeToPixel(s.getDestination().getLatitude()),
						  longitudeToPixel(s.getDestination().getLongitude())
		);
	}
	
	private int longitudeToPixel(double longitude ) {
		return (int)((double)getHeight() * ( cityMap.getMaxLongitude() - longitude) / ( cityMap.getMaxLongitude() - cityMap.getMinLongitude()));
	}
	
	private int latitudeToPixel(double latitude ) {
		return (int)((double)getWidth() * ( cityMap.getMaxLatitude() - latitude) / (cityMap.getMaxLatitude() - cityMap.getMinLatitude()));
	}
}
