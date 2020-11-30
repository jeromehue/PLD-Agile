package algo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;

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
		PriorityQueue<Integer> orderingUnvisited = new PriorityQueue<Integer>(customComparator);

		// id of unvisited delivery, index in costs matrix
		HashMap<Long, Integer> unvisitedDeliveries = new HashMap<Long, Integer>();

		for(Integer index : unvisited) {
			if(request.isPickUp(g.getIdFromIndex(index))) {
				orderingUnvisited.add(index);
			} else {
				unvisitedDeliveries.put(g.getIdFromIndex(index), index);
			}
		}

		ArrayList<Integer> orderedUnvisited = new ArrayList<>();
		
		
		while(!orderingUnvisited.isEmpty()) { // iterating through the ordered pickups
			Integer head = orderingUnvisited.poll();
			//System.out.println("test PQremovedHead (" + head + ")\n"+orderingUnvisited.toString());
			orderedUnvisited.add(head);
			Long deliveryId = request.getDeliveryFromPickUp(g.getIdFromIndex(head));
			if(deliveryId != null) { // add a delivery only if its corresponding pickup
				// was already removed from the queue
				//System.out.println("adding to queue : "+g.getIndex(deliveryId));
				Integer index = unvisitedDeliveries.get(deliveryId);
				if(index != null)
				{
					orderingUnvisited.add(g.getIndex(deliveryId)); 
				}
			}
		}
		
		//System.out.println("test ordered unvisited\n" + orderedUnvisited.toString()+"\n");
		
		for (Integer s : orderedUnvisited){
			//System.out.print("cout : " + g.getCost(currentVertex, s) + " ; ");
			if (g.isArc(currentVertex, s))
				candidates[nbCandidates++] = s;
		}
		//System.out.println();
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
