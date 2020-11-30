package algo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;

import modele.Intersection;
import modele.Request;

public class SeqIter implements Iterator<Integer> {
	private Integer[] candidates;
	//private ArrayList candidates;
	private int nbCandidates;

	/**
	 * Create an iterator to traverse the set of vertices in <code>unvisited</code> 
	 * which are successors of <code>currentVertex</code> in <code>g</code>
	 * Vertices are traversed in the same order as in <code>unvisited</code>
	 * @param unvisited
	 * @param currentVertex
	 * @param g
	 */
	public SeqIter(Collection<Integer> unvisited, int currentVertex, Graph g, Request request){
		this.candidates = new Integer[unvisited.size()];
		// index of pickup, index of corresponding delivery 
		HashMap<Integer, Integer> isPickUpLocation = new HashMap<Integer, Integer>();
		
		ArrayList<Intersection> pickUps = request.getPickUpLocations();
		ArrayList<Intersection> deliveries = request.getDeliveryLocations();

		for(int i = 0 ; i < pickUps.size(); ++i) {
			isPickUpLocation.put(g.getIndex(pickUps.get(i).getId()), g.getIndex(deliveries.get(i).getId()));
		}
		
		Comparator<Integer> customComparator = new Comparator<Integer>() {
			 @Override
	            public int compare(Integer inter1, Integer inter2) {
	                double cost1 = g.getCost(currentVertex, inter1);
	                double cost2 = g.getCost(currentVertex, inter2);
	                if(cost1 > cost2) {
	                	return 1;
	                } else if(cost1 == cost2) {
	                	return 0;
	                } else {
	                	return -1;
	                }
	            }
		};
		PriorityQueue<Integer> orderedUnvisited = new PriorityQueue<Integer>(new Comparator<Integer>());
		
		
		for (Integer s : unvisited){
			if (g.isArc(currentVertex, s))
				candidates[nbCandidates++] = s;
		}
	}
	
	@Override
	public boolean hasNext() {
		return nbCandidates > 0;
	}

	@Override
	public Integer next() {
		nbCandidates--;
		return candidates[nbCandidates];
	}

	@Override
	public void remove() {}

}
