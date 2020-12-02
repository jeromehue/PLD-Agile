package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import modele.CityMap;
import modele.Intersection;
import modele.Request;
import modele.Segment;
import modele.Tour;
import modele.Visitor;
import modele.Way;
import observer.Observable;
import observer.Observer;

public class GraphicalView extends JPanel implements Observer, Visitor{

	private static final long serialVersionUID = 1L;

	private CityMap cityMap;
	private Request request;
	private Segment highlightedSegment;
	private Way highlightedWay;
	private Tour tour; // tour is unique -> modified in the controller and observed to display changes here

	public GraphicalView(Tour tour)  {
		super();
		this.setBorder(BorderFactory.createTitledBorder("Carte"));
		this.setLayout(null);
		this.cityMap = null;
		this.request = null;
		this.highlightedWay = null;
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
	
	public void setHighlightedWay(Way way) {
		this.highlightedWay = way;
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
	
	
	
	public void setTour(Tour tour) {
		this.tour = tour;
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

		// Smooth lines (anti-aliasing)
		//graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		//draw white background
		graphics.setColor(new Color(248, 255, 242));
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
				
				if(tour != null && this.highlightedWay != null) {
					graphics.setStroke(new BasicStroke(4));
					graphics.setColor(Color.red);
					graphics.fillOval(
							this.highlightedWay.getSegmentList().get(0).getOrigin().getCoordinates().getX()-10, 
							this.highlightedWay.getSegmentList().get(0).getOrigin().getCoordinates().getY()-10, 20, 20);
				}
			}
			
			if (this.highlightedSegment != null) {
				graphics.setColor(Color.black);
				graphics.setStroke(new BasicStroke(3));
				drawSegment(graphics, this.highlightedSegment);
			}
		}
		
		//draw white rectangle in Top-Left corner
		if(cityMap != null) {
			graphics.setColor(Color.white);
			graphics.fillRect(0, 0, 52, 18);
		}
	}
	
	private void drawTour(Graphics2D graphics) {

		Color from = new Color(3, 115, 252); // Blue
		Color to = new Color(227, 36, 30); // Red
		double progress = 0.0;
		
		graphics.setStroke(new BasicStroke(4));
		Iterator<Way> itWay = tour.getWaysListIterator();
		
		int i = 0;
		int red, green, blue, currentCount = 0;
		Way currrentWay;
		while(itWay.hasNext())
		{
			progress = (double) ++i / tour.getWaysList().size();
			red = (int)(to.getRed() * progress + from.getRed() * (1-progress));
			green = (int)(to.getGreen() * progress + from.getGreen() * (1-progress));
			blue = (int)(to.getBlue() * progress + from.getBlue() * (1-progress));
			graphics.setColor(new Color(red, green, blue));
			currrentWay = itWay.next();
			drawWay(graphics, currrentWay);
			if(currentCount != 0) {
				graphics.setColor(Color.black);
				graphics.setFont(graphics.getFont().deriveFont(Font.BOLD, 14f));
				graphics.drawString("Step " + currentCount, 
						currrentWay.getSegmentList().get(0).getOrigin().getCoordinates().getX() - 65, 
						currrentWay.getSegmentList().get(0).getOrigin().getCoordinates().getY() - 10);
			}
			++currentCount;
		}
		
		if(this.highlightedWay != null) {
			graphics.setColor(Color.black);
			graphics.setStroke(new BasicStroke(4));
			drawWay(graphics, this.highlightedWay);
			graphics.setColor(Color.red);
			graphics.fillOval(
					this.highlightedWay.getSegmentList().get(0).getOrigin().getCoordinates().getX()-10, 
					this.highlightedWay.getSegmentList().get(0).getOrigin().getCoordinates().getY()-10, 20, 20);
			
		}
	}
	
	private void drawWay(Graphics2D graphics, Way way) {
		Iterator<Segment> itSegment = way.getSegmentListIterator();
		while(itSegment.hasNext())
		{
			drawSegment(graphics,itSegment.next());
		}
	}
	
	private void drawCityMap(Graphics2D graphics) {
		graphics.setColor(Color.darkGray);
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
			pickUpAdresseToDraw = itPickUpTest.next();
			deliveryAdressToDraw = itDeliveryTest.next();

			Color color = new Color(pickUpAdresseToDraw.hashCode()).darker();
			graphics.setColor(color);
			
			// System.out.println(pickUpAdresseToDraw);
			if(pickUpAdresseToDraw != null ) 
			{ drawIntersectionSquare(graphics, pickUpAdresseToDraw); }
			
			// System.out.println(deliveryAdressToDraw);
			if(deliveryAdressToDraw != null ) 
			{ drawIntersection(graphics, deliveryAdressToDraw); }	
		}
	}
	
	private void drawIntersection(Graphics graphics, Intersection intersection){
		if(intersection.getId() != null) {
			graphics.setFont(graphics.getFont().deriveFont(Font.BOLD, 14f));
			graphics.drawString("Delivery", 
					intersection.getCoordinates().getX() + 5, 
					intersection.getCoordinates().getY() - 10 );
			graphics.fillOval(
					intersection.getCoordinates().getX()-5, 
					intersection.getCoordinates().getY()-5, 10, 10);
		}
	}
	
	private void drawIntersectionSquare(Graphics graphics, Intersection intersection){
		graphics.setFont(graphics.getFont().deriveFont(Font.BOLD, 14f));
		graphics.drawString("Pick-up", 
				intersection.getCoordinates().getX() + 5, 
				intersection.getCoordinates().getY() - 10);
		graphics.fillRect(
				intersection.getCoordinates().getX()-5, 
				intersection.getCoordinates().getY()-5, 
				10, 
				10); 
	}
	
	
	private void drawStartIntersection(Graphics graphics, Intersection intersection){
		graphics.setColor(Color.red);
		graphics.setFont(graphics.getFont().deriveFont(Font.BOLD, 14f));
		graphics.drawString("Start", 
				intersection.getCoordinates().getX() + 5, 
				intersection.getCoordinates().getY() - 10);
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
