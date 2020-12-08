package modele;

import java.util.Iterator;
import java.util.List;

import view.GraphicalView;

/**
 * This class contains all the elements to draw a map.
 * 
 * @author H4414
 *            
 */

public class CityMap {
	/**
	 * List of all intersections of the map gotten from the XML parser.
	 */
	private List<Intersection> intersections;
	
	/**
	 * List of all segments of the map gotten from the XML parser.
	 */
	private List<Segment> segments;
	
	/**
	 * The latitude of the farthest south intersection of the map.
	 */
	private double minLatitude;
	
	/**
	 * The longitude of the farthest west intersection of the map.
	 */
	private double minLongitude;
	
	/**
	 * The latitude of the farthest north intersection of the map.
	 */
	private double maxLatitude;
	
	/**
	 * The longitude of the farthest east intersection of the map.
	 */
	private double maxLongitude;

	/**
	 * Default constructor.
	 */
	public CityMap() {
	}

	/**
	 * Constructor to initialize a full map.
	 * @param intersections 	List of intersections.
	 * @param segments 			List of segments.
	 */
	public CityMap(List<Intersection> intersections, List<Segment> segments) {
		this.intersections = intersections;
		this.segments = segments;
		findMinMax();
	}

	/**
	 * Initializes the coordinates on the window of all intersections
	 * @param graphicalView
	 */
	public void setIntersectionCordinates(GraphicalView graphicalView) {
		PointFactory.initPointFactory(graphicalView, this);
		Iterator<Intersection> itIntersections = getIntersectionsIterator();
		while (itIntersections.hasNext()) {
			Intersection intersection = itIntersections.next();
			Point coordinates = PointFactory.createPoint(intersection);
			intersection.setCoordinates(coordinates);
		}
	}

	/**
	 * Adds an intersection to the map
	 * @param inter the intersection to add
	 */
	public void addIntersection(Intersection inter) {
		intersections.add(inter);
	}

	/**
	 * Adds a segment to the map
	 * @param seg the segment to add
	 */
	public void addSegment(Segment seg) {
		segments.add(seg);
	}

	/**
	 * @return the list of all intersections of the map
	 */
	public List<Intersection> getIntersections() {
		return intersections;
	}

	/**
	 * @return the list of all segments of the map
	 */
	public List<Segment> getSegments() {
		return segments;
	}

	/**
	 * @return an iterator on all Segments in the CityMap
	 */
	public Iterator<Segment> getSegmentsIterator() {
		return segments.iterator();
	}

	/**
	 * @return an iterator on all Intersections in the CityMap
	 */
	public Iterator<Intersection> getIntersectionsIterator() {
		return intersections.iterator();
	}

	/**
	 * @return the minimal latitude of all intersections
	 */
	public double getMinLatitude() {
		return minLatitude;
	}

	/**
	 * @return the minimal longitude of all intersections
	 */
	public double getMinLongitude() {
		return minLongitude;
	}

	/**
	 * @return the maximal latitude of all intersections
	 */
	public double getMaxLatitude() {
		return maxLatitude;
	}

	/**
	 * @return the maximal longitude of all intersections
	 */
	public double getMaxLongitude() {
		return maxLongitude;
	}

	/**
	 * Used to update the minimal and maximal latitudes and longitudes
	 */
	private void findMinMax() {
		minLatitude = intersections.get(0).getLatitude();
		minLongitude = intersections.get(0).getLongitude();
		maxLatitude = intersections.get(0).getLatitude();
		maxLongitude = intersections.get(0).getLongitude();

		for (Intersection i : intersections) {
			if (minLatitude > i.getLatitude()) {
				minLatitude = i.getLatitude();
			}
			if (minLongitude > i.getLongitude()) {
				minLongitude = i.getLongitude();
			}
			if (maxLatitude < i.getLatitude()) {
				maxLatitude = i.getLatitude();
			}
			if (maxLongitude < i.getLongitude()) {
				maxLongitude = i.getLongitude();
			}
		}
	}

	/**
	 * Finds the Intersection corresponding to an Id
	 * @param 	address the Id of the Intersection to find
	 * @return	the Intersection
	 */
	public Intersection getIntersectionFromId(Long id) {
		for (Intersection intersection : intersections) {
			if (id.equals(intersection.getId())) {
				return intersection;
			}
		}
		return null;
	}
}
