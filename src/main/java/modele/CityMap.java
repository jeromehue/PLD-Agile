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
	 * Empty constructor used for tests.
	 */
	public CityMap() {};

	/**
	 * Constructor to initialize a full map. The extrema are initialized by the 
	 * <code>findMinMax()</code> function.
	 * @param intersections 	List of all intersections of the map.
	 * @param segments 			List of all segments of the map.
	 */
	public CityMap(List<Intersection> intersections, List<Segment> segments) {
		this.intersections = intersections;
		this.segments = segments;
		findMinMax();
	}

	/**
	 * Initializes the coordinates on the window of all intersections
	 * @param graphicalView Used to create the Points containing the coordinates.
	 */
	public void setIntersectionCoordinates(GraphicalView graphicalView) {
		PointFactory.initPointFactory(graphicalView, this);
		Iterator<Intersection> itIntersections = getIntersectionsIterator();
		while (itIntersections.hasNext()) {
			Intersection intersection = itIntersections.next();
			Point coordinates = PointFactory.createPoint(intersection);
			intersection.setCoordinates(coordinates);
		}
	}

	/**
	 * Adds an intersection to the map.
	 * @param inter The intersection to add.
	 */
	public void addIntersection(Intersection inter) {
		intersections.add(inter);
		findMinMax();
	}

	/**
	 * Adds a segment to the map.
	 * @param seg The segment to add.
	 */
	public void addSegment(Segment seg) {
		segments.add(seg);
	}

	/**
	 * @return The list of all intersections of the map.
	 */
	public List<Intersection> getIntersections() {
		return intersections;
	}

	/**
	 * @return The list of all segments of the map.
	 */
	public List<Segment> getSegments() {
		return segments;
	}

	/**
	 * @return An iterator on all Segments of the CityMap.
	 */
	public Iterator<Segment> getSegmentsIterator() {
		return segments.iterator();
	}

	/**
	 * @return An iterator on all Intersections of the CityMap.
	 */
	public Iterator<Intersection> getIntersectionsIterator() {
		return intersections.iterator();
	}

	/**
	 * @return The minimal latitude of all intersections.
	 */
	public double getMinLatitude() {
		return minLatitude;
	}

	/**
	 * @return The minimal longitude of all intersections.
	 */
	public double getMinLongitude() {
		return minLongitude;
	}

	/**
	 * @return The maximal latitude of all intersections.
	 */
	public double getMaxLatitude() {
		return maxLatitude;
	}

	/**
	 * @return The maximal longitude of all intersections.
	 */
	public double getMaxLongitude() {
		return maxLongitude;
	}

	/**
	 * Used to update the minimal and maximal latitudes and longitudes.
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
	 * Finds the Intersection corresponding to a given id.
	 * @param id The id of the Intersection to find.
	 * @return	The Intersection corresponding to the given id.
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
