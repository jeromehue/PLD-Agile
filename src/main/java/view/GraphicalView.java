package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import modele.CityMap;
import modele.Intersection;
import modele.PointFactory;
import modele.Request;
import modele.Segment;
import modele.Tour;
import modele.Visitor;
import modele.Way;
import observer.Observable;
import observer.Observer;

import java.lang.Math;

public class GraphicalView extends JPanel implements Observer, Visitor{

	private static final long serialVersionUID = 1L;

	private CityMap cityMap;
	private Request request;
	private Segment highlightedSegment;
	private Tour tour; // tour is unique -> modified in the controller and observed to display changes here

	public GraphicalView(Tour tour)  {
		super();
		this.setBorder(BorderFactory.createTitledBorder("Vue Graphique"));
		this.setLayout(null);
		PointFactory.initPointFactory(this, cityMap);
		this.cityMap = null;
		this.request = null;
		this.tour = tour;
		this.tour.addObserver(this); // observes tour changes
	}
	
	public void setRequest(Request request) {
		this.request = request;
		this.repaint();
	}

	public void setCityMap(CityMap cityMap) {
		this.cityMap = cityMap;
		this.repaint();
	}
	
	public void highlight(Segment segment) {
		this.highlightedSegment = segment;
		this.repaint();
	}
	
	public CityMap getCityMap() {
		return this.cityMap;
	}
	
	public Request getRequest() {
		return this.request;
	}
	
	public Tour getTour() {
		return this.tour;
	}
	
	@Override
	public void update(Observable observed, Object arg) {
		this.repaint();
	}
	
	/**
	 * Method called each this must be redrawn 
	 */
	@Override
	protected void paintComponent(Graphics _graphics) {
		Graphics2D graphics = (Graphics2D)_graphics;
		super.paintComponent(graphics);
		
		//draw white background
		graphics.setColor(Color.white);
		graphics.setStroke(new BasicStroke(1));
		graphics.fillRect(0, 0, getWidth(), getHeight());
		
		//draw the cityMap
		if(cityMap != null) {
			cityMap.setIntersectionCordinates(this);
			drawCityMap(graphics);
			
			//draw the request
			if(request != null)
			{
				//draw the tour
				if(tour != null)
				{
					drawTour(graphics);
				}
				
				drawRequest(graphics);
			}
			
			if (this.highlightedSegment != null) {
				graphics.setColor(Color.black);
				graphics.setStroke(new BasicStroke(6));
				drawSegment(graphics, this.highlightedSegment);
			}
		}
	}
	
	private void drawTour(Graphics2D graphics) {
		graphics.setColor(Color.red);
		graphics.setStroke(new BasicStroke(4));
		Iterator<Way> itSegments = tour.getSegmentsIterator();
		while(itSegments.hasNext())
		{
			drawSegment(graphics,itSegments.next());
		}
	}
	
	private void drawCityMap(Graphics2D graphics) {
		graphics.setColor(Color.black);
		graphics.setStroke(new BasicStroke(1));
		Iterator<Segment> itSegments = cityMap.getSegmentsIterator();
		while(itSegments.hasNext())
		{
			Segment segment = itSegments.next();
			drawSegment(graphics,segment);
		}	
	}
	private void drawSegment(Graphics graphics, Segment s) {
		graphics.drawLine(s.getOrigin().getCoordinates().getX(),
						  s.getOrigin().getCoordinates().getY(),
						  s.getDestination().getCoordinates().getX(),
						  s.getDestination().getCoordinates().getY()
		);
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
	}
	
	private void drawIntersection(Graphics graphics, Intersection intersection){
		if(intersection.getId() != null) {
		graphics.drawString("(" + intersection.getId() + ")", 
				intersection.getCoordinates().getX() + 5, 
				intersection.getCoordinates().getY() - 10 );
		graphics.fillOval(
				intersection.getCoordinates().getX()-5, 
				intersection.getCoordinates().getY()-5, 10, 10);
		}
	}
	
	private void drawIntersectionSquare(Graphics graphics, Intersection intersection){
		
		graphics.drawString("(" + intersection.getId() + ")", 
				intersection.getCoordinates().getX() + 5, 
				intersection.getCoordinates().getY() - 10 );
		graphics.fillRect(
				intersection.getCoordinates().getX()-5, 
				intersection.getCoordinates().getY()-5, 
				10, 
				10
		); 
	}
	
	
	private void drawStartIntersection(Graphics graphics, Intersection intersection){
		graphics.setColor(Color.red);
		graphics.drawString("Start point (" + intersection.getId() + ")", 
				intersection.getCoordinates().getX() + 5, 
				intersection.getCoordinates().getY() - 10 );
		graphics.fillRect(
				intersection.getCoordinates().getX() - 5, 
				intersection.getCoordinates().getY() - 5,
				10, 
				10);
	}

	@Override
	public void display(Segment s) {
		// TODO Auto-generated method stub
		
	}	
}
