package modele;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all informations relative to an Intersection.
 * 
 * @author H4414
 *
 * @param id   				unique identifier of an intersection
 * @param latitude 			latitude of the intersection
 * @param longitude  		longitude of the intersection
 * @param outboundSegments 	list of Segments whose origin is this Intersection
 * @param coordinates   	x and y coordinates to draw the Intersection on the map
 *            
 */
public class Intersection {
	private Long id;
	private double latitude;
	private double longitude;
	private ArrayList<Segment> outboundSegments;
	private Point coordinates;

	/**
	 * Constructor to initialize an Intersection.
	 * 
	 * @param Id
	 * @param latitude
	 * @param longitude
	 * @param segments
	 */
	public Intersection(Long Id, double latitude, double longitude, ArrayList<Segment> segments) {

		this.id = Id;
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
	 * @return the coordinates of the intersection
	 */
	public Point getCoordinates() {
		return coordinates;
	}

	/**
	 * @param coordinates used to update an Intersection's coordinates
	 */
	public void setCoordinates(Point coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * @return the list of Intersections directly linked to this one via a Segment
	 */
	public List<Intersection> getNeighbors() {
		ArrayList<Intersection> neighbors = new ArrayList<Intersection>();
		for (Segment seg : outboundSegments) {
			neighbors.add(seg.getDestination());
		}
		return neighbors;
	}

	/**
	 * Method used to add a Segment whose origin is this Intersection
	 * @param segment
	 */
	public void addOutboundSegment(Segment segment) {
		this.outboundSegments.add(segment);
	}

	/**
	 * Method used to remove a Segment whose origin is this Intersection
	 * @param segment
	 */
	public void removeOutboundSegment(Segment segment) {
		this.outboundSegments.remove(segment);
	}

	/**
	 * @return a textual representation of an Intersection
	 */
	@Override
	public String toString() {
		return "Intersection [Id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

	/**
	 * @return the latitude of the intersection
	 */
	public double getLatitude() {
		return latitude;
	}
	
	/**
	 * @param latitude used to update an Intersection's latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude of the intersection
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude used to update an Intersection's longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the segments whose origin is this intersection
	 */
	public ArrayList<Segment> getOutboundSegments() {
		return outboundSegments;
	}

	/**
	 * @return the id of the intersection
	 */
	public Long getId() {
		return id;
	}
}
