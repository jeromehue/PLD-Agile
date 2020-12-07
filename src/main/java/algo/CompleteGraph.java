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
 * Create a complete directed graph such that each edge is a step of the tour
 * 
 * @param nbVertices   number of steps
 * @param costsMatrix  the matrix containing the lowest known costs between
 *                     vertices
 * @param index        hashmap linking an intersection's id to its index in the
 *                     costs matrix
 * @param reverseIndex hashmap linking an index in the costs matrix to its
 *                     intersection's id
 */
public class CompleteGraph implements Graph {
	private int nbVertices;
	private double[][] costsMatrix;
	private HashMap<Long, Integer> index;
	private HashMap<Integer, Long> reverseIndex;

	/**
	 * Constructor of CompleteGraph, initializes the costs matrix with
	 * DOUBLE.MAX_VALUE
	 * 
	 * @param startVertices list of the vertices for the graph (steps of the tour)
	 */
	public CompleteGraph(List<Intersection> startVertices) {
		this.nbVertices = startVertices.size();
		index = new HashMap<Long, Integer>();
		reverseIndex = new HashMap<Integer, Long>();

		this.costsMatrix = new double[nbVertices][nbVertices];
		for (int i = 0; i < nbVertices; ++i) {
			Intersection inter = startVertices.get(i);
			index.put(inter.getId(), i); // initialisation de index
			reverseIndex.put(i, inter.getId()); // init de reverse index

			for (int j = 0; j < nbVertices; ++j) {
				costsMatrix[i][j] = Double.MAX_VALUE;
			}
		}
	}

	/**
	 * updating a line of the costs matrix when a vertex has been released
	 * 
	 * @param startId the id of the intersection whose departing edges are being
	 *                updated
	 * @param costs   hashmap linking a target intersection id to its new cost
	 * @param targets list of intersections linked to the one being released
	 */
	public void updateCompleteGraph(Long startId, HashMap<Long, IntersectionPcc> costs, List<Intersection> targets) {
		Integer startIndex = index.get(startId);
		for (Intersection targetIntersection : targets) {
			costsMatrix[startIndex][index.get(targetIntersection.getId())] = costs.get(targetIntersection.getId())
					.getCost();
		}
	}

	/**
	 * Returns the number of vertices of the complete graph
	 */
	@Override
	public int getNbVertices() {
		return nbVertices;
	}

	/**
	 * Returns the cost between two vertices of the graph
	 * 
	 * @param i index in the costs matrix of the first vertex
	 * @param j index in the costs matrix of the second vertex
	 */
	@Override
	public double getCost(int i, int j) {
		if ((i < 0) || (i >= nbVertices) || (j < 0) || (j >= nbVertices)) {
			return -1;
		}
		return costsMatrix[i][j];
	}

	/**
	 * Returns true if the two vertices are linked by an edge, else false
	 * 
	 * @param i index in the costs matrix of the first vertex
	 * @param j index in the costs matrix of the second vertex
	 */
	@Override
	public boolean isArc(int i, int j) {
		if ((i < 0) || (i >= nbVertices) || (j < 0) || (j >= nbVertices)) {
			return false;
		}
		return i != j;
	}

	/**
	 * Returns the index of the costs matrix of the intersection whose id is passed
	 * 
	 * @param id intersection's id whose index is wanted
	 */
	public Integer getIndex(Long id) {
		return index.get(id);
	}

	/**
	 * Returns a textual representation of the complete graph
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

	/**
	 * Returns the intersection whose index of the costs matrix is passed
	 * 
	 * @param i index in the costs matrix of an intersection
	 */
	@Override
	public Long getIdFromIndex(Integer i) {
		return reverseIndex.get(i);
	}
}
