package modele;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all informations relative to an Intersection.
 * 
 * @author H4414 	
 *            
 */
public class Intersection {
	/**
	 * Unique identifier of an Intersection.
	 */
	private Long id;
	
	/**
	 * Latitude of the intersection (given by the XML file).
	 */
	private double latitude;
	
	/**
	 * Longitude of the intersection (given by the XML file).
	 */
	private double longitude;
	
	/**
	 * List of Segments whose origin is this Intersection.
	 */
	private ArrayList<Segment> outboundSegments;
	
	/**
	 * X and y coordinates on the window to draw the Intersection on the displayed map.
	 */
	private Point coordinates;

	/**
	 * Constructor to initialize an Intersection.
	 * 
	 * @param id 		This Intersection's id.
	 * @param latitude 	This Intersection's latitude.
	 * @param longitude	This Intersection's longitude.
	 * @param segments	The list of all segments whose origin is this Intersection.
	 */
	public Intersection(Long id, double latitude, double longitude, ArrayList<Segment> segments) {

		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.coordinates = null;
		if (segments != null) {
			this.outboundSegments = new ArrayList<Segment>(segments);
		} else {
			this.outboundSegments = new ArrayList<Segment>();
		}
	}

	/**
	 * @return The coordinates of the intersection.
	 */
	public Point getCoordinates() {
		return coordinates;
	}

	/**
	 * Default setter.
	 * 
	 * @param coordinates Used to update an Intersection's coordinates.
	 */
	public void setCoordinates(Point coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * @return The list of Intersections directly linked to this one via a Segment.
	 */
	public List<Intersection> getNeighbors() {
		ArrayList<Intersection> neighbors = new ArrayList<Intersection>();
		for (Segment seg : outboundSegments) {
			neighbors.add(seg.getDestination());
		}
		return neighbors;
	}

	/**
	 * Method used to add a Segment whose origin is this Intersection to this Intersection.
	 * 
	 * @param segment The segment to add.
	 */
	public void addOutboundSegment(Segment segment) {
		this.outboundSegments.add(segment);
	}

	/**
	 * Method used to remove a Segment whose origin is this Intersection from this Intersection.
	 * 
	 * @param segment The segment to remove.
	 */
	public void removeOutboundSegment(Segment segment) {
		this.outboundSegments.remove(segment);
	}

	/**
	 * @return A textual representation of this Intersection.
	 */
	@Override
	public String toString() {
		return "Intersection [Id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

	/**
	 * Default getter.
	 * 
	 * @return The latitude of this intersection.
	 */
	public double getLatitude() {
		return latitude;
	}
	
	/**
	 * Default setter.
	 * 
	 * @param latitude Used to update this Intersection's latitude.
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Default getter.
	 * 
	 * @return The longitude of this intersection.
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Default setter.
	 * 
	 * @param longitude Used to update this Intersection's longitude.
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Default getter.
	 * 
	 * @return The list of all segments whose origin is this intersection.
	 */
	public ArrayList<Segment> getOutboundSegments() {
		return outboundSegments;
	}

	/**
	 * Default getter.
	 * 
	 * @return The id of this intersection.
	 */
	public Long getId() {
		return id;
	}
}
