package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import modele.CityMap;
import modele.Segment;

public class GraphicalView extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private CityMap cityMap;

	
	
	
	public void setCityMap(CityMap cityMap) {
		this.cityMap = cityMap;
		this.repaint();
	}

	public GraphicalView(CityMap cityMap) {
		super();
		/*maxLongitude = 20;
		minLongitude = 10;
		maxLatitude = 50;
		minLatitude = 3;*/
		this.setBorder(BorderFactory.createTitledBorder("Vue Graphique"));
		this.setLayout(null);
		this.cityMap=cityMap;
	}
	
	/**
	 * Method called each this must be redrawn 
	 */
	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		// draw white background
		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, getWidth(), getHeight());
	
		if(cityMap != null) {
			//draw the cityMap
			graphics.setColor(Color.black);
			Iterator<Segment> itSegements = cityMap.getSegementsIterator();
			while(itSegements.hasNext())
			{
				drawSegement(graphics,itSegements.next());
			}
		}
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
