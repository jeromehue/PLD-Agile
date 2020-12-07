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
	private Integer color;
	private Double cost;
	/**
	 * Default constructor.
	 * @param inter Intersection it is extending
	 * @param color White (0), grey (1) or black (2)
	 * @param cost cost to go from origin to destination
	 */
	public IntersectionPcc(Intersection inter, Integer color, Double cost) {
		super(inter.getId(), inter.getLatitude(), inter.getLongitude(), inter.getOutboundSegments());
		this.color = color;
		this.cost = cost;
	}
	
	/**
	 * Default getter.
	 * @return the IntersectionPcc's color
	 */
	public Integer getColor() {
		return color;
	}

	/**
	 * Default getter.
	 * @return the IntersectionPcc's cost
	 */
	public Double getCost() {
		return cost;
	}

	/**
	 * Default setter.
	 * @param color the IntersectionPcc's color to update
	 */
	public void setColor(Integer color) {
		this.color = color;
	}

	/**
	 * Default setter.
	 * @param cost the IntersectionPcc's cost to update
	 */
	public void setCost(Double cost) {
		this.cost = cost;
	}

	/**
	 * Returns a textual representation of the IntersectionPcc
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
