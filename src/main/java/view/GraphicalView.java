package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modele.CityMap;
import modele.Intersection;
import modele.Request;
import modele.Segment;
import modele.Tour;
import modele.Way;
import observer.Observable;
import observer.Observer;

/**
 * The area of the window that displays the map and the computed tour.
 * @author H4414
 * */

public class GraphicalView extends JPanel implements Observer {
	private static final Logger logger = LoggerFactory.getLogger(GraphicalView.class);

	private static final long serialVersionUID = 1L;

	private CityMap cityMap;
	private Request request;
	private Segment highlightedSegment;
	private Intersection highlightedIntersection;
	private Way highlightedWay;
	private Tour tour; // tour is unique -> modified in the controller and observed to display changes

	/**
	 * Create the graphical view for drawing Map, request and tour  
	 * @param tour the observed Tour to redraw on graphical view each time it is modified
	 */
	public GraphicalView(Tour tour) {
		super();
		this.setBorder(BorderFactory.createTitledBorder("Map"));
		this.setLayout(null);
		this.cityMap = null;
		this.request = null;
		this.highlightedWay = null;
		this.highlightedIntersection = null;
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

	public void setHighlightInter(Intersection intersection) {
		this.highlightedIntersection = intersection;
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

	
	public Long getHighlightedIntersectionId() {
		return this.highlightedIntersection.getId();
	}
	

	public void setTour(Tour tour) {
		this.tour = tour;
		
	}
	
	/**
	 * Method called by objects observed by this graphical view each time they are modified
	 */
	@Override
	public void update(Observable observed) {
		if (this.tour != null) {
			this.request = this.tour.getRequest();
		}
		this.repaint();
		logger.info(observed.getClass() + " object was modified: graphical view updated");
	}

	/**
	 * Method called each time this must be redrawn
	 * @param _graphics the graphic context of the view
	 */
	@Override
	protected void paintComponent(Graphics _graphics) {
		Graphics2D graphics = (Graphics2D) _graphics;
		super.paintComponent(graphics);

		// draw white background
		graphics.setColor(new Color(248, 255, 242));
		graphics.setStroke(new BasicStroke(1));
		graphics.fillRect(0, 0, getWidth(), getHeight());

		// draw the cityMap
		if (cityMap != null) {
			cityMap.setIntersectionCoordinates(this);
			drawCityMap(graphics);

			// draw the request
			if (request != null) {
				// draw the tour
				if (tour != null) {
					drawTour(graphics);
				}
				
				drawRequest(graphics);

				if (tour != null && this.highlightedWay != null) {
					graphics.setStroke(new BasicStroke(4));
					graphics.setColor(Color.red);
					graphics.fillOval(
							this.highlightedWay.getSegmentList().get(0).getOrigin().getCoordinates().getX() - 10,
							this.highlightedWay.getSegmentList().get(0).getOrigin().getCoordinates().getY() - 10, 20,
							20);
				}

				if (tour != null && this.highlightedIntersection != null) {
					graphics.setStroke(new BasicStroke(4));
					graphics.setColor(Color.cyan);
					graphics.fillOval(this.highlightedIntersection.getCoordinates().getX() - 10,
							this.highlightedIntersection.getCoordinates().getY() - 10, 20, 20);
				}
			}

			if (this.highlightedSegment != null) {
				graphics.setColor(Color.black);
				graphics.setStroke(new BasicStroke(3));
				drawSegment(graphics, this.highlightedSegment);
			}
		}

		// draw white rectangle in Top-Left corner
		if (cityMap != null) {
			graphics.setColor(new Color(248, 255, 242));
			graphics.fillRect(0, 0, 52, 18);
		}
	}
	
	/**
	 * Method called to draw this.tour 
	 * @param graphics the graphic context of the view
	 */
	private void drawTour(Graphics2D graphics) {
		Color from = new Color(3, 115, 252); // Blue
		Color to = new Color(227, 36, 30); // Red
		double progress = 0.0;

		graphics.setStroke(new BasicStroke(4));
		Iterator<Way> itWay = tour.getWaysListIterator();

		int i = 0;
		int red, green, blue, currentCount = 0;
		Way currrentWay;
		while (itWay.hasNext()) {
			progress = (double) ++i / tour.getWaysList().size();
			red = (int) (to.getRed() * progress + from.getRed() * (1 - progress));
			green = (int) (to.getGreen() * progress + from.getGreen() * (1 - progress));
			blue = (int) (to.getBlue() * progress + from.getBlue() * (1 - progress));
			graphics.setColor(new Color(red, green, blue));
			currrentWay = itWay.next();
			drawWay(graphics, currrentWay);
			if (currentCount != 0) {
				graphics.setColor(Color.black);
				graphics.setFont(graphics.getFont().deriveFont(Font.BOLD, 14f));
				graphics.drawString("Step " + currentCount,
						currrentWay.getSegmentList().get(0).getOrigin().getCoordinates().getX() - 65,
						currrentWay.getSegmentList().get(0).getOrigin().getCoordinates().getY() - 10);
			}
			++currentCount;
		}

		if (this.highlightedWay != null) {
			graphics.setColor(Color.black);
			graphics.setStroke(new BasicStroke(4));
			drawWay(graphics, this.highlightedWay);
			graphics.setColor(Color.red);
			graphics.fillOval(this.highlightedWay.getSegmentList().get(0).getOrigin().getCoordinates().getX() - 10,
					this.highlightedWay.getSegmentList().get(0).getOrigin().getCoordinates().getY() - 10, 20, 20);

		}
	}
	
	/**
	 * Method called to draw a Way
	 * @param graphics the graphic context of the view
	 * @param way the Way to draw   
	 */
	private void drawWay(Graphics2D graphics, Way way) {
		Iterator<Segment> itSegment = way.getSegmentListIterator();
		while (itSegment.hasNext()) {
			drawSegment(graphics, itSegment.next());
		}
	}
	
	/**
	 * Method called to draw this.cityMap 
	 * @param graphics the graphic context of the view
	 */
	private void drawCityMap(Graphics2D graphics) {
		graphics.setColor(Color.darkGray);
		graphics.setStroke(new BasicStroke(1));
		Iterator<Segment> itSegments = cityMap.getSegmentsIterator();
		while (itSegments.hasNext()) {
			Segment segment = itSegments.next();
			drawSegment(graphics, segment);
		}
	}
	
	/**
	 * Method called to draw a Segment
	 * @param graphics the graphic context of the view
	 * @param s the Segment to draw   
	 */
	private void drawSegment(Graphics graphics, Segment s) {
		graphics.drawLine(s.getOrigin().getCoordinates().getX(), s.getOrigin().getCoordinates().getY(),
				s.getDestination().getCoordinates().getX(), s.getDestination().getCoordinates().getY());
	}
	
	/**
	 * Method called to draw this.request
	 * @param graphics the graphic context of the view 
	 */
	private void drawRequest(Graphics graphics) {
		// draw start point
		Intersection startIntersection = request.getStartingLocation();
		if (startIntersection != null) {
			drawStartIntersection(graphics, startIntersection);
		}
		
		int requestNumber = 0;
		Intersection pickUpAddressToDraw;
		Intersection deliveryAdressToDraw;
		Iterator<Intersection> itPickUpTest = request.getPickUpLocationsIterator();
		Iterator<Intersection> itDeliveryTest = request.getDeliveryLocationsIterator();
		while (itPickUpTest.hasNext() && itDeliveryTest.hasNext()) {
			pickUpAddressToDraw = itPickUpTest.next();
			deliveryAdressToDraw = itDeliveryTest.next();

			//logger.info("Pickup 	: {} ", pickUpAddressToDraw.getId());
			//logger.info("Delivery 	: {} ", deliveryAdressToDraw.getId());
			
			Color color = new Color(pickUpAddressToDraw.hashCode()).darker();
			graphics.setColor(color);

			// System.out.println(pickUpAddressToDraw);
			if (pickUpAddressToDraw != null) {
				String label = "Pick-up " + (char)(requestNumber + 65);
				drawIntersectionSquare(graphics, pickUpAddressToDraw, label);
			}

			// System.out.println(deliveryAdressToDraw);
			if (deliveryAdressToDraw != null) {
				String label = "Delivery " + (char)(requestNumber + 65);
				drawIntersectionCircle(graphics, deliveryAdressToDraw, label);
			}
			++requestNumber;
		}
	}
	
	/**
	 * Method called to draw a delivery point
	 * @param graphics the graphic context of the view
	 * @param intersection the delivery point intersection to draw 
	 * @param label the label 
	 */
	private void drawIntersectionCircle(Graphics graphics, Intersection intersection, String label) {
		if (intersection.getId() != null) {
			graphics.setFont(graphics.getFont().deriveFont(Font.BOLD, 14f));
			graphics.drawString(label, intersection.getCoordinates().getX() + 5,
					intersection.getCoordinates().getY() - 10);
			graphics.fillOval(intersection.getCoordinates().getX() - 5, intersection.getCoordinates().getY() - 5, 10,
					10);
		}
	}
	
	/**
	 * Method called to draw a pick up point
	 * @param graphics the graphic context of the view
	 * @param intersection the pick up point intersection to draw  
	 * @param label the label 
	 */
	private void drawIntersectionSquare(Graphics graphics, Intersection intersection, String label) {
		graphics.setFont(graphics.getFont().deriveFont(Font.BOLD, 14f));
		graphics.drawString(label, intersection.getCoordinates().getX() + 5,
				intersection.getCoordinates().getY() - 10);
		graphics.fillRect(intersection.getCoordinates().getX() - 5, intersection.getCoordinates().getY() - 5, 10, 10);
	}

	/**
	 * Method called to draw the starting point
	 * @param graphics the graphic context of the view
	 * @param intersection of the starting point  
	 */
	private void drawStartIntersection(Graphics graphics, Intersection intersection) {
		graphics.setColor(Color.red);
		graphics.setFont(graphics.getFont().deriveFont(Font.BOLD, 14f));
		graphics.drawString("Start", intersection.getCoordinates().getX() + 5,
				intersection.getCoordinates().getY() - 10);
		graphics.fillRect(intersection.getCoordinates().getX() - 5, intersection.getCoordinates().getY() - 5, 10, 10);
	}
}