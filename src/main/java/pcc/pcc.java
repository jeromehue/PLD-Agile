package pcc;
import modele.Request;
import modele.Intersection;
import modele.CityMap;
import modele.Segment;
import tsp.CompleteGraph;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.HashMap;

public class pcc {
	private CityMap cityMap;
	private Request request;
	
	public pcc(Map city , Request request ){
		cityMap = city;
	}
	
	public CompleteGraph calculPcc() {
		List<Intersection> startVertexes = request.getDeliveryLocations();
		startVertexes.addAll(request.getPickUpLocations());
		startVertexes.add(request.getStartingLocation());
		List<Intersection> allVertexes = cityMap.getIntersections();
		List<IntersectionPcc> allVertexesPcc;
		PriorityQueue<IntersectionPcc> greyVertexes;
		HashMap<Long, Long> predecesors;//<Intersection, Intersection  >
		
		//On fait un Dijkstra par point à visiter
		for (Intersection start  :  startVertexes) {
			greyVertexes = new PriorityQueue<IntersectionPcc>();
			
			
			for (Intersection inter : allVertexes) {
				allVertexesPcc.add(new IntersectionPcc(inter, 0, Double.MAX_VALUE ));
				predecesors.put(inter.getId(), null);
			}
			
			greyVertexes.add(start);
			
			while(!greyVertexes.isEmpty()) {
				
			}
			
		}
		
		
		
		
		
	}

	HashMap<Long, Long> colors;
}
