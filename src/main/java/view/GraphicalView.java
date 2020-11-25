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
import modele.Tour;

import java.lang.Math;

public class GraphicalView extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private CityMap cityMap;
	private Request request;
	private Tour tour;

	public GraphicalView(CityMap cityMap) {
		super();
		this.setBorder(BorderFactory.createTitledBorder("Vue Graphique"));
		this.setLayout(null);
		this.cityMap = cityMap;
		this.request = null;
		this.tour = null;
	}
	
	public void setRequest(Request request) {
		this.request = request;
		this.repaint();
	}

	public void setCityMap(CityMap cityMap) {
		this.cityMap = cityMap;
		this.repaint();
	}
	
	public CityMap getCityMap() {
		return this.cityMap;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
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
		
		//draw the tour
		if(tour != null)
		{
			drawTour(graphics);
		}
	}
	
	private int intersectionToPixelLattitude(Intersection i) {
		double longPourcent = ( cityMap.getMaxLongitude() - i.getLongitude()) / ( cityMap.getMaxLongitude() - cityMap.getMinLongitude());
		double latitudePixel =( 1 - longPourcent ) * getWidth();
		return (int)latitudePixel;
	}
	
	private int intersectionToPixelLongitude(Intersection i) {
		double latPourcent = ( cityMap.getMaxLatitude() - i.getLatitude()) / (cityMap.getMaxLatitude() - cityMap.getMinLatitude());
		double longitudePixel =  latPourcent  * getHeight();
		return (int)longitudePixel;
	}
	
	private void drawTour(Graphics graphics) {
		graphics.setColor(Color.red);
		Iterator<Segment> itSegements = tour.getSegementsIterator();
		while(itSegements.hasNext())
		{
			drawSegement(graphics,itSegements.next());
		}	
	}
	
	private void drawCityMap(Graphics graphics) {
		graphics.setColor(Color.black);
		Iterator<Segment> itSegements = cityMap.getSegementsIterator();
		while(itSegements.hasNext())
		{
			Segment segment = itSegements.next();
			drawSegement(graphics,segment);
			/*Intersection i = new Intersection(null, 
					(segment.getOrigin().getLatitude() - segment.getDestination().getLatitude())/2.00, 
					(segment.getOrigin().getLongitude() - segment.getDestination().getLongitude())/2.00,
					null);
			graphics.drawString(segment.getName(),	
								,
								Math.abs());*/
		}	
	}
	
	
	private void drawRequest(Graphics graphics) {
		//draw start point
		Intersection startIntersection = request.getStartingLocation();
		if(startIntersection != null ) 
		{ drawStartIntersection(graphics, startIntersection); }
		
		Intersection pickUpAdresseToDraw;
		Intersection deliveryAdressToDraw;
		Iterator<Intersection> itPickUpTest = request.getPickUpLocationsIterator();
		Iterator<Intersection> itDeliveryTest = request.getDeliveryLocationsIterator();
		while(itPickUpTest.hasNext())
		{
			int red=  (int) (Math.random()*256);
			int green= (int) (Math.random()*256);
			int blue= (int) (Math.random()*256);
			Color color=new Color(red,green,blue);
			graphics.setColor(color);
			pickUpAdresseToDraw = itPickUpTest.next();
			deliveryAdressToDraw = itDeliveryTest.next();
			
			System.out.println(pickUpAdresseToDraw);
			if(pickUpAdresseToDraw != null ) 
			{ drawIntersectionSquare(graphics, pickUpAdresseToDraw); }
			
			System.out.println(deliveryAdressToDraw);
			if(deliveryAdressToDraw != null ) 
			{ drawIntersection(graphics, deliveryAdressToDraw); }	
		}
		
		/*
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
		}*/
	}
	
	private void drawIntersection(Graphics graphics, Intersection intersection){
		if(intersection.getId() != null) {
		graphics.drawString("(" + intersection.getId() + ")", 
				intersectionToPixelLattitude(intersection) + 5, 
				intersectionToPixelLongitude(intersection) - 10 );
		graphics.fillOval(intersectionToPixelLattitude(intersection)-5, 
				          intersectionToPixelLongitude(intersection)-5, 10, 10);
		}
	}
	
	private void drawIntersectionSquare(Graphics graphics, Intersection intersection){
		
		graphics.drawString("(" + intersection.getId() + ")", 
				intersectionToPixelLattitude(intersection) + 5, 
				intersectionToPixelLongitude(intersection) - 10 );
		graphics.fillRect(intersectionToPixelLattitude(intersection)-5, 
				          intersectionToPixelLongitude(intersection)-5, 10, 10); 
		
	}
	
	
	
	
	
	private void drawStartIntersection(Graphics graphics, Intersection intersection){
		graphics.setColor(Color.red);
		graphics.drawString("Start point (" + intersection.getId() + ")", 
				intersectionToPixelLattitude(intersection) + 5, 
				intersectionToPixelLongitude(intersection) - 10 );
		graphics.fillRect(intersectionToPixelLattitude(intersection)-5, 
				          intersectionToPixelLongitude(intersection)-5, 10, 10);
	}

	private void drawSegement(Graphics graphics, Segment s) {
		graphics.drawLine(intersectionToPixelLattitude(s.getOrigin()),
						  intersectionToPixelLongitude(s.getOrigin()),
						  intersectionToPixelLattitude(s.getDestination()),
						  intersectionToPixelLongitude(s.getDestination())
		);
	}
	
}
