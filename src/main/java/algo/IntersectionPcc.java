package algo;
import modele.Intersection;

public class IntersectionPcc extends Intersection implements Comparable<IntersectionPcc>{
	private Integer color;
	private Double cost;
	
	public IntersectionPcc(Intersection inter, Integer color, Double cost) {
		super(inter.getId(), inter.getLatitude(), inter.getLongitude(), inter.getOutboundSegments());
		this.color = color;
		this.cost = cost;
	}
	
	
	public Integer getColor() {
		return color;
	}

	public Double getCost() {
		return cost;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	@Override 
	public int compareTo (IntersectionPcc inter) {
		if (this.cost - inter.cost > 0) {
			return 1;
		} else  if (this.cost - inter.cost == 0){
			return 0;
		} else {
			return -1;
		}
	}
	
}
