package algo;

import modele.Intersection;

/**
 * This class is an extension of the class Intersection
 * made especially for the Dijkstra algorithm.
 * It stores the same informations as an Intersection,
 * with a <code>color</code> as an Integer and a <code>cost</code> as a Double.
 * It is also Comparable to other IntersectionPcc.
 * 
 * @author H4414
 *
 */
public class IntersectionPcc extends Intersection implements Comparable<IntersectionPcc> {
	/**
	 * The color of this (0 for white, 1 for grey and 2 for black).
	 */
	private Integer color;
	
	/**
	 * In the instance of a Dijkstra algorithm, it is the lowest known 
	 * cost needed to go from the starting point to this. 
	 */
	private Double cost;
	
	/**
	 * Default constructor.
	 * @param inter Intersection it is extending.
	 * @param color White (0), grey (1) or black (2).
	 * @param cost Cost to go from origin to destination.
	 */
	public IntersectionPcc(Intersection inter, Integer color, Double cost) {
		super(inter.getId(), inter.getLatitude(), inter.getLongitude(), inter.getOutboundSegments());
		this.color = color;
		this.cost = cost;
	}
	
	/**
	 * Default getter.
	 * @return The IntersectionPcc's color.
	 */
	public Integer getColor() {
		return color;
	}

	/**
	 * Default getter.
	 * @return The IntersectionPcc's cost.
	 */
	public Double getCost() {
		return cost;
	}

	/**
	 * Default setter.
	 * @param color The IntersectionPcc's color to update.
	 */
	public void setColor(Integer color) {
		this.color = color;
	}

	/**
	 * Default setter.
	 * @param cost The IntersectionPcc's cost to update.
	 */
	public void setCost(Double cost) {
		this.cost = cost;
	}

	/**
	 * Returns a textual representation of the IntersectionPcc.
	 */
	@Override
	public int compareTo(IntersectionPcc inter) {
		if ((this.cost - inter.cost) > 0) {
			return 1;
		} else if ((this.cost - inter.cost) == 0) {
			return 0;
		} else {
			return -1;
		}
	}
}
