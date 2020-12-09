package modele;

/**
 * This class contains the informations relative to a Segment.
 * A Segment is a link between two Intersections (one way).
 * 
 * @author H4414
 *
 */
public class Segment {
	/**
	 * The length of the segment (in meters), as given by the XML file of the map.
	 */
	private double length;
	
	/**
	 * The name of the street (in French) from which this Segment
	 * is a part of.
	 */
	private String name;
	
	/**
	 * The Intersection corresponding to the origin of this Segment.
	 * It means that this Segment will be an outboundSegment of this
	 * Intersection. 
	 */
	private Intersection origin;
	
	/**
	 * The Intersection corresponding to the end of this Segment.
	 */
	private Intersection destination;

	/**
	 * Constructor used to initialize all fields of this Segment.
	 * The values are retrieved from an XML file passed as input.
	 * 
	 * @param length 		The length of this segment.
	 * @param name			The name of the road from which this Segment
	 * is a part of.
	 * @param origin	 	The Intersection corresponding to the origin 
	 * of this Segment.
	 * @param destination	The Intersection corresponding to the end 
	 * of this Segment.
	 */
	public Segment(double length, String name, Intersection origin, Intersection destination) {
		super();
		this.length = length;
		this.name = name;
		this.origin = origin;
		this.destination = destination;
	}

	/**
	 * @return A textual representation of the complete graph.
	 */
	@Override
	public String toString() {
		return "Segment [length=" + length + ", name=" + name + ", origin=" + origin + ", destination=" + destination
				+ "]";
	}

	/**
	 * Default getter.
	 * 
	 * @return The length of this Segment.
	 */
	public double getLength() {
		return length;
	}

	/**
	 * Default getter.
	 *  
	 * @return The name of the road from which this Segment is a part of.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Default getter.
	 * 
	 * @return The Intersection corresponding to the origin of this Segment.
	 */
	public Intersection getOrigin() {
		return origin;
	}

	/**
	 * Default getter.
	 * 
	 * @return The Intersection corresponding to the end of this Segment.
	 */
	public Intersection getDestination() {
		return destination;
	}
}
