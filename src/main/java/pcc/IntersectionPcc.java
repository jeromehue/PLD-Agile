package pcc;
import modele.Intersection;

public class IntersectionPcc extends Intersection implements Comparable<IntersectionPcc>{
	Integer color;
	Double cost;
	
	public IntersectionPcc(Intersection inter, Integer color, Double cost) {
		super(inter.getId(), inter.getLatitude(), inter.getLongitude(), inter.getOutboundSegments());
		this.color = color;
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
