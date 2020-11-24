package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import modele.CityMap;
import modele.Intersection;
import modele.Request;
import modele.Segment;

public class GraphicalView extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private CityMap cityMap;
	private Request request;

	public GraphicalView(CityMap cityMap) {
		super();
		this.setBorder(BorderFactory.createTitledBorder("Vue Graphique"));
		this.setLayout(null);
		this.cityMap = cityMap;
		this.request = null;
	}
	
	public void setRequest(Request request) {
		this.request = request;
		this.repaint();
	}
	
	public void setCityMap(CityMap cityMap) {
		this.cityMap = cityMap;
		this.repaint();
	}
	
	/**
	 * Method called each this must be redrawn 
	 */
	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		//draw white background
		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		
		//draw the cityMap
		if(cityMap != null) {
			drawCityMap(graphics);
		}
		
		//draw the request
		if(request != null)
		{
			drawRequest(graphics);
		}
	}
	
	private int longitudeToPixel(double longitude ) {
		return (int)((double)getHeight() * ( cityMap.getMaxLongitude() - longitude) / ( cityMap.getMaxLongitude() - cityMap.getMinLongitude()));
	}
	
	private int latitudeToPixel(double latitude ) {
		return (int)((double)getWidth() * ( cityMap.getMaxLatitude() - latitude) / (cityMap.getMaxLatitude() - cityMap.getMinLatitude()));
	}
	
	private void drawCityMap(Graphics graphics) {
		graphics.setColor(Color.black);
		Iterator<Segment> itSegements = cityMap.getSegementsIterator();
		while(itSegements.hasNext())
		{
			drawSegement(graphics,itSegements.next());
		}	
	}
	
	private void drawRequest(Graphics graphics) {
		//draw start point
		Long startAdress = request.getStartingLocation();
		Intersection startIntersection = cityMap.getIntersectionFromAddress(startAdress);
		drawStartIntersection(graphics, startIntersection);
		
		//draw pick up points
		graphics.setColor(Color.blue);
		Long pickUpAdress;
		Iterator<Long> itPickUpLocations = request.getPickUpLocationsIterator();
		while(itPickUpLocations.hasNext())
		{
			pickUpAdress = itPickUpLocations.next();
			Intersection intersectionToDraw = cityMap.getIntersectionFromAddress(pickUpAdress);
			if (intersectionToDraw != null)
			{
				drawIntersection(graphics, intersectionToDraw);
			}
		}

		//draw delivery points
		graphics.setColor(Color.magenta);
		Long deliveryAdress;
		Iterator<Long> itDeliveryLocations = request.getDeliveryLocationsIterator();
		while(itDeliveryLocations.hasNext())
		{
			deliveryAdress = itDeliveryLocations.next();
			Intersection intersectionToDraw = cityMap.getIntersectionFromAddress(deliveryAdress);
			if (intersectionToDraw != null)
			{
				drawIntersection(graphics, intersectionToDraw);
			}
		}
	}
	
	private void drawIntersection(Graphics graphics, Intersection intersection){
		graphics.drawString("(" + intersection.getId() + ")", 
				latitudeToPixel(intersection.getLatitude()) + 5, 
				longitudeToPixel(intersection.getLongitude()) - 10 );
		graphics.fillOval(latitudeToPixel(intersection.getLatitude())-5, 
				          longitudeToPixel(intersection.getLongitude())-5, 10, 10);
	}
	
	private void drawStartIntersection(Graphics graphics, Intersection intersection){
		graphics.setColor(Color.red);
		graphics.drawString("Start point (" + intersection.getId() + ")", 
				latitudeToPixel(intersection.getLatitude()) + 5, 
				longitudeToPixel(intersection.getLongitude()) - 10 );
		graphics.fillRect(latitudeToPixel(intersection.getLatitude())-5, 
				          longitudeToPixel(intersection.getLongitude())-5, 10, 10);
	}

	private void drawSegement(Graphics graphics, Segment s) {
		graphics.drawLine(latitudeToPixel(s.getOrigin().getLatitude()),
						  longitudeToPixel(s.getOrigin().getLongitude()),
						  latitudeToPixel(s.getDestination().getLatitude()),
						  longitudeToPixel(s.getDestination().getLongitude())
		);
	}
	
}
