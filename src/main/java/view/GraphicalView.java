package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import modele.CityMap;
import modele.Coordinates;
import modele.Intersection;
import modele.Request;
import modele.Segment;
import xml.XMLRequestParser;

public class GraphicalView extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private CityMap cityMap;
	private Request request;

	public GraphicalView(CityMap cityMap) {
		super();
		this.setBorder(BorderFactory.createTitledBorder("Vue Graphique"));
		this.setLayout(null);
		this.cityMap=cityMap;
		XMLRequestParser p = new XMLRequestParser("src/main/resources/requestsLarge.xml");
		request = p.parse();
		
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
	
		//draw the cityMap
		graphics.setColor(Color.black);
		Iterator<Segment> itSegements = cityMap.getSegementsIterator();
		while(itSegements.hasNext())
		{
			drawSegement(graphics,itSegements.next());
		}
		//draw request
		/*Coordinates coordinates;
		for(Long l :  request.getDeliveryLocations())
		{
			coordinates = cityMap.getCoordinatesFromAddress(l);
			if (coordinates !=null)
			{
				graphics.setColor(Color.red);
				graphics.fillOval(latitudeToPixel(coordinates.latitude)-5, longitudeToPixel(coordinates.longitude)-5, 10, 10);

			}
			else
			{
				graphics.setColor(Color.blue);
				graphics.fillRect(0, 0, getWidth(), getHeight());
			}
		}*/
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
	private void drawSteps () {
		
	}
	
}
