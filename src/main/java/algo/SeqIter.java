package algo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import modele.Request;

public class SeqIter implements Iterator<Integer> {
	private Integer[] candidates;
	// private ArrayList candidates;
	private int nbCandidates;
	// private int maxDiscrepancy=2;
	// private int nbTrash=7;

	/**
	 * Create an iterator to traverse the set of vertices in <code>unvisited</code>
	 * which are successors of <code>currentVertex</code> in <code>g</code> Vertices
	 * are traversed in the same order as in <code>unvisited</code>
	 * 
	 * @param unvisited
	 * @param currentVertex
	 * @param g
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

		HashSet<Integer> deliveriesToRemove = new HashSet<>();
		Long verticeId;

		// int i=0;
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

	@Override
	public boolean hasNext() {
		return nbCandidates > 0;
	}

	@Override
	public Integer next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		nbCandidates--;
		return candidates[nbCandidates];
	}

	@Override
	public void remove() {
	}
}
