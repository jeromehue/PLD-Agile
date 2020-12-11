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
 * 
 * @author H4414
 * */

public class GraphicalView extends JPanel implements Observer {
	/**
	 * Used to log properly actions and exceptions that may have to reported.
	 */
	private static final Logger logger = LoggerFactory.getLogger(GraphicalView.class);

	/**
	 * Recommended parameter to avoid unexpected InvalidClassExceptions.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This field contains all informations relative to the map that this
	 * has to display.
	 */
	private CityMap cityMap;
	
	/**
	 * This field contains all informations relative to the requests provided
	 * by the user in his XML file.
	 */
	private Request request;
	
	/**
	 * The segment that is nearest of the mouse cursor and that has to be highlighted
	 * on the map. The name of its street will also be displayed.
	 */
	private Segment highlightedSegment;
	
	/**
	 * If the user clicks on a step, the intersection corresponding to the origin
	 * of this step will be highlighted on the map.
	 */
	private Intersection highlightedIntersection;
	
	/**
	 * If the user clicks on a step, the list of all segments composing the step
	 * will be highlighted on the map.
	 */
	private Way highlightedWay;
	
	/**
	 * This field contains all informations relative to the current version of the tour.
	 * It is at first computed by the algo package, then modified by the controller if the
	 * user performs modifications on the tour.
	 * It is observed by this class to be notified of its changes, so that the updates
	 * can be displayed on the window.
	 */
	private Tour tour;

	/**
	 * Create the graphical view for drawing Map, request and tour.
	 * 
	 * @param tour The observed Tour to redraw on graphical view every time it is modified.
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
	
	/**
	 * Default setter.
	 * 
	 * @param request Used when a new set of requests is loaded into the application.
	 */
	public void setRequest(Request request) {
		this.request = request;
		this.repaint();
	}

	/**
	 * Default setter. 
	 * 
	 * @param cityMap Used when a new map is loaded into the application.
	 */
	public void setCityMap(CityMap cityMap) {
		this.cityMap = cityMap;
		this.repaint();
	}

	/**
	 * Used when the user moves his mouse over the map to highlight the nearest segment.
	 * 
	 * @param segment The segment that is nearest to the mouse cursor. 
	 */
	public void highlight(Segment segment) {
		this.highlightedSegment = segment;
		this.repaint();
	}

	/**
	 * When the user clicks on a step, the corresponding intersection is highlighted.
	 * 
	 * @param intersection The intersection to highlight.
	 */
	public void setHighlightInter(Intersection intersection) {
		this.highlightedIntersection = intersection;
		this.repaint();
	}

	
	/**
	 * When the user clicks on a step, the corresponding list way is highlighted.
	 * 
	 * @param intersection The way to highlight.
	 */
	public void setHighlightedWay(Way way) {
		this.highlightedWay = way;
		this.repaint();
	}

	/**
	 * Default getter.
	 * 
	 * @return The cityMap displayed on the screen.
	 */
	public CityMap getCityMap() {
		return this.cityMap;
	}

	/**
	 * Default getter.
	 * 
	 * @return The set of requests displayed on the screen.
	 */
	public Request getRequest() {
		return this.request;
	}

	/**
	 * Default getter.
	 * 
	 * @return The current version of the tour displayed on the screen.
	 */
	public Tour getTour() {
		return this.tour;
	}

	/**
	 * Default getter.
	 * 
	 * @return The id of the currently highlighted intersection, when the user
	 * has clicked on a step. 
	 */
	public Long getHighlightedIntersectionId() {
		return this.highlightedIntersection.getId();
	}
	
	/**
	 * Default setter.
	 * 
	 * @param tour The tour to be added (after computing the tour) or updated
	 * if it has been modified.
	 */
	public void setTour(Tour tour) {
		this.tour = tour;
		
	}
	
	/**
	 * Method called by objects (Tour) observed by this graphical view each time they are modified.
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
	 * Method called each time this must be redrawn.
	 * @param _graphics The graphical context of the view.
	 */
	@Override
	protected void paintComponent(Graphics _graphics) {
		Graphics2D graphics = (Graphics2D) _graphics;
		super.paintComponent(graphics);

		// draw white background
		graphics.setColor(new Color(255, 254, 237));
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
			graphics.fillRect(0, 0, 36, 18);
		}
	}
	
	/**
	 * Method called to draw this tour when it has been computed or updated.
	 * 
	 * @param graphics The graphical context of the view.
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
	 * Method called to draw a Way on the map.
	 * 
	 * @param graphics The graphical context of the view.
	 * @param way The list of Segments to draw on the map.   
	 */
	private void drawWay(Graphics2D graphics, Way way) {
		Iterator<Segment> itSegment = way.getSegmentListIterator();
		while (itSegment.hasNext()) {
			drawSegment(graphics, itSegment.next());
		}
	}
	
	/**
	 * Method called to draw the city map loaded into the application.
	 *  
	 * @param graphics The graphic context of the view.
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
	 * Method called to draw a specific Segment.
	 * 
	 * @param graphics The graphical context of the view.
	 * @param s The Segment to draw on the map.   
	 */
	private void drawSegment(Graphics graphics, Segment s) {
		graphics.drawLine(s.getOrigin().getCoordinates().getX(), s.getOrigin().getCoordinates().getY(),
				s.getDestination().getCoordinates().getX(), s.getDestination().getCoordinates().getY());
	}
	
	/**
	 * Method called to draw this request.
	 * 
	 * @param graphics The graphical context of the view. 
	 */
	private void drawRequest(Graphics graphics) {
		// draw start point
		Intersection startIntersection = request.getStartingLocation();
		if (startIntersection != null) {
			drawStartIntersection(graphics, startIntersection);
		}
		
		int requestNumber = 0;
		Intersection pickUpAddressToDraw;
		Intersection deliveryAddressToDraw;
		Iterator<Intersection> itPickUpTest = request.getPickUpLocationsIterator();
		Iterator<Intersection> itDeliveryTest = request.getDeliveryLocationsIterator();
		while (itPickUpTest.hasNext()) {
			pickUpAddressToDraw = itPickUpTest.next();
			deliveryAddressToDraw = this.request.getDeliveryIntersectionFromPickUp(pickUpAddressToDraw.getId());
			
			Color color = new Color(pickUpAddressToDraw.hashCode()).darker();
			graphics.setColor(color);

			String label;
			if (pickUpAddressToDraw != null) {
				if(deliveryAddressToDraw != null) {
					 label = "Pick-up " + (char)(requestNumber + 65);
				}
				else {
					 label = "Stand-alone Pick-up ";
				}
				drawIntersectionSquare(graphics, pickUpAddressToDraw, label);
			}
			
			if (deliveryAddressToDraw != null) {
				label = "Delivery " + (char)(requestNumber + 65);
				drawIntersectionCircle(graphics, deliveryAddressToDraw, label);
			}
			++requestNumber;
		}
		Intersection aloneDeliveryAdressToDraw;
		while (itDeliveryTest.hasNext()) { // draw alone delivery points
			aloneDeliveryAdressToDraw = itDeliveryTest.next();
			Color color = new Color(aloneDeliveryAdressToDraw.hashCode()).darker();
			graphics.setColor(color);
			if (aloneDeliveryAdressToDraw != null && !this.request.hasPickup(aloneDeliveryAdressToDraw.getId())) {
				String label = "Stand-alone delivery";
				drawIntersectionCircle(graphics, aloneDeliveryAdressToDraw, label);
			}
			++requestNumber;
		}
	}
	
	/**
	 * Method called to draw a delivery point.
	 * 
	 * @param graphics The graphical context of the view.
	 * @param intersection The intersection to draw corresponding
	 * to the delivery point.  
	 * @param label The label of the delivery point (containing its
	 * position in the tour). 
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
	 * Method called to draw a pick-up point.
	 * 
	 * @param graphics The graphical context of the view.
	 * @param intersection The intersection to draw corresponding
	 * to the pick-up point.  
	 * @param label The label of the pick-up point (containing its
	 * position in the tour). 
	 */
	private void drawIntersectionSquare(Graphics graphics, Intersection intersection, String label) {
		graphics.setFont(graphics.getFont().deriveFont(Font.BOLD, 14f));
		graphics.drawString(label, intersection.getCoordinates().getX() + 5,
				intersection.getCoordinates().getY() - 10);
		graphics.fillRect(intersection.getCoordinates().getX() - 5, intersection.getCoordinates().getY() - 5, 10, 10);
	}

	/**
	 * Method called to draw the starting point.
	 * 
	 * @param graphics The graphic context of the view.
	 * @param intersection The intersection to draw corresponding
	 * to the starting point. 
	 */
	private void drawStartIntersection(Graphics graphics, Intersection intersection) {
		graphics.setColor(Color.red);
		graphics.setFont(graphics.getFont().deriveFont(Font.BOLD, 14f));
		graphics.drawString("Start", intersection.getCoordinates().getX() + 5,
				intersection.getCoordinates().getY() - 10);
		graphics.fillRect(intersection.getCoordinates().getX() - 5, intersection.getCoordinates().getY() - 5, 10, 10);
	}
}