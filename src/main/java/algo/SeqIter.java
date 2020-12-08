package algo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import modele.Request;

/**
 * This class provides a custom iterator for the TSP algorithm.
 * It allows the implementation of an order heuristic
 * and to satisfy the constraint of visiting pick-up points
 * before their associated delivery points.
 * 
 * @author H4414
 *               
 */
public class SeqIter implements Iterator<Integer> {
	/**
	 * The indexes in the costs matrix of the Intersections
	 * to be iterated through.
	 */
	
	private Integer[] candidates;
	/**
	 * The number of considered Intersections.
	 */
	private int nbCandidates;

	/**
	 * Create an iterator to traverse the set of vertices in <code>unvisited</code>
	 * which are successors of <code>currentVertex</code> in <code>g</code>.
	 * 
	 * @param unvisited The collection of vertices to be iterated through.
	 * @param currentVertex The last visited vertex.
	 * @param g The whole graph.
	 * @param request The request containing all steps of the tour.
	 */
	public SeqIter(Collection<Integer> unvisited, int currentVertex, Graph g, Request request) {
		this.candidates = new Integer[unvisited.size()];

		Comparator<Integer> customComparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer inter1, Integer inter2) {
				double cost1 = g.getCost(currentVertex, inter1);
				double cost2 = g.getCost(currentVertex, inter2);
				if (cost1 > cost2) {
					return 1;
				} else if (cost1 == cost2) {
					return 0;
				} else {
					return -1;
				}
			}
		};

		ArrayList<Integer> toBeVisited = new ArrayList<Integer>(unvisited);

		HashSet<Integer> deliveriesToRemove = new HashSet<Integer>();
		Long verticeId;

		for (Integer vertice : toBeVisited) {
			verticeId = g.getIdFromIndex(vertice);
			if (request.isPickUp(verticeId)) {
				// store all deliveries that shouldn't be visited
				deliveriesToRemove.add(g.getIndex(request.getDeliveryFromPickUp(verticeId)));
			}
		}

		Iterator<Integer> it = toBeVisited.iterator();
		while (it.hasNext()) {

			if (deliveriesToRemove.contains(it.next())) {
				// remove deliveries that shouldn't be visited
				it.remove();
			}
		}

		Collections.sort(toBeVisited, customComparator);

		for (Integer s : toBeVisited) {
			if (g.isArc(currentVertex, s))
				candidates[nbCandidates++] = s;
		}
	}

	/**
	 * @return True if the iterator isn't pointing on
	 * the last element of the collection, else returns false.
	 */
	@Override
	public boolean hasNext() {
		return nbCandidates > 0;
	}

	/**
	 * @return The index in the collection of the next element.
	 */
	@Override
	public Integer next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		nbCandidates--;
		return candidates[nbCandidates];
	}
}
