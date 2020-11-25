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

import java.lang.Math;

public class GraphicalView extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private PointFactory pointFactory;
	private CityMap cityMap;
	private Request request;
	private Tour tour;

	public GraphicalView(CityMap cityMap) {
		super();
		this.setBorder(BorderFactory.createTitledBorder("Vue Graphique"));
		this.setLayout(null);
		PointFactory.initPointFactory(this, cityMap);
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
		}
	}
	
	private void drawTour(Graphics2D graphics) {
		graphics.setColor(Color.red);
		graphics.setStroke(new BasicStroke(4));
		Iterator<Segment> itSegements = tour.getSegementsIterator();
		while(itSegements.hasNext())
		{
			drawSegement(graphics,itSegements.next());
		}
	}
	
	private void drawCityMap(Graphics2D graphics) {
		graphics.setColor(Color.black);
		graphics.setStroke(new BasicStroke(1));
		Iterator<Segment> itSegements = cityMap.getSegementsIterator();
		while(itSegements.hasNext())
		{
			Segment segment = itSegements.next();
			drawSegement(graphics,segment);
		}	
	}
	private void drawSegement(Graphics graphics, Segment s) {
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
	
}
