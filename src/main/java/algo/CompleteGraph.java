package algo;

import java.util.List;
import java.util.HashMap;

import modele.Intersection;

/**
 * This class computes and stores the shortest ways between pickUp and delivery
 * points.
 * 
 * @author H4414
 * 
 */
public class CompleteGraph implements Graph {
	/**
	 * The number of vertices in the graph.
	 */
	private int nbVertices;

	/**
	 * The costs matrix of the graph. At the index (i, j), it contains the lowest
	 * known cost to go from the Intersection corresponding to the index i to the
	 * Intersection corresponding to the index j.
	 */
	private double[][] costsMatrix;

	/**
	 * A HashMap linking an intersection's id to its index in the costs matrix.
	 */
	private HashMap<Long, Integer> index;

	/**
	 * A HashMap linking an index in the costs matrix to its intersection's id.
	 */
	private HashMap<Integer, Long> reverseIndex;

	/**
	 * Constructor of CompleteGraph, initializes the costs matrix with
	 * DOUBLE.MAX_VALUE.
	 * 
	 * @param startVertices List of the vertices for the graph (steps of the tour).
	 */
	public CompleteGraph(List<Intersection> startVertices) {
		this.nbVertices = startVertices.size();
		index = new HashMap<Long, Integer>();
		reverseIndex = new HashMap<Integer, Long>();

		this.costsMatrix = new double[nbVertices][nbVertices];
		for (int i = 0; i < nbVertices; ++i) {
			Intersection inter = startVertices.get(i);
			index.put(inter.getId(), i);
			reverseIndex.put(i, inter.getId());

			for (int j = 0; j < nbVertices; ++j) {
				costsMatrix[i][j] = Double.MAX_VALUE;
			}
		}
	}

	/**
	 * Updating a line of the costs matrix when a vertex has been released.
	 * 
	 * @param startId The id of the intersection whose departing edges are being
	 *                updated.
	 * @param costs   Hashmap linking a target intersection id to its new cost.
	 * @param targets List of intersections linked to the one being released.
	 */
	public void updateCompleteGraph(Long startId, HashMap<Long, IntersectionPcc> costs, List<Intersection> targets) {
		Integer startIndex = index.get(startId);
		for (Intersection targetIntersection : targets) {
			costsMatrix[startIndex][index.get(targetIntersection.getId())] = costs.get(targetIntersection.getId())
					.getCost();
		}
	}

	@Override
	public int getNbVertices() {
		return nbVertices;
	}

	@Override
	public double getCost(int i, int j) {
		if ((i < 0) || (i >= nbVertices) || (j < 0) || (j >= nbVertices)) {
			return -1;
		}
		return costsMatrix[i][j];
	}

	@Override
	public boolean isArc(int i, int j) {
		if ((i < 0) || (i >= nbVertices) || (j < 0) || (j >= nbVertices)) {
			return false;
		}
		return i != j;
	}

	/**
	 * @return The index of the costs matrix of the intersection whose id is passed.
	 * 
	 * @param id Intersection's id whose index is wanted.
	 */
	public Integer getIndex(Long id) {
		return index.get(id);
	}

	/**
	 * @return A textual representation of the complete graph.
	 */
	@Override
	public String toString() {
		String ret = "";

		for (int i = 0; i < nbVertices; ++i) {
			ret += "Cout pour aller de " + i + " a :\n";
			for (int j = 0; j < nbVertices; ++j) {
				if (costsMatrix[i][j] == Double.MAX_VALUE) {
					ret += j + " = Unreachable\n";
				} else {
					ret += j + " = " + costsMatrix[i][j] + "\n";
				}
			}
		}
		return ret;
	}

	@Override
	public Long getIdFromIndex(Integer i) {
		return reverseIndex.get(i);
	}
}
