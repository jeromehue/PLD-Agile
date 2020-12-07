package algo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This abstract class provides a template for the class responsible
 * for the execution of the TSP algorithm.
 * 
 * @author H4414
 * 
 * @param bestSol array storing the indexes of the vertices corresponding
 * to the best known solution
 * @param g the graph on which the TSP algorithm is being conducted
 * @param bestSolCost the total cost associated to the best known solution
 * @param timeLimit the time limit that can't be exceeded
 * @param startTime the time at which the computation was started
 * @param maxDiscrepancy the maximal discrepancy for the LDS meta-heuristic
 */

public abstract class TemplateTSP implements TSP {
	private Integer[] bestSol;
	protected Graph g;
	protected Double bestSolCost;
	private Integer timeLimit;
	private Long startTime;
	protected Integer maxDiscrepancy;

	/**
	 * the main method for the TSP, which updates the best solution.
	 * 
	 * @param timeLimit the time limit that can't be exceeded
	 */
	public void searchSolution(Integer timeLimit) {
		if (timeLimit <= 0)
			return;

		startTime = System.currentTimeMillis();
		this.timeLimit = timeLimit;
		bestSol = new Integer[g.getNbVertices()];
		Collection<Integer> unvisited = new ArrayList<Integer>(g.getNbVertices() - 1);

		for (Integer i = 1; i < g.getNbVertices(); i++) {
			unvisited.add(i);
		}

		Collection<Integer> visited = new ArrayList<Integer>(g.getNbVertices());
		visited.add(0); // The first visited vertex is 0
		bestSolCost = Double.MAX_VALUE;
		branchAndBound(0, unvisited, visited, 0.0, 0);
	}

	/**
	 * @return the index of the i-th vertex from the best known solution
	 */
	public Integer getSolution(Integer i) {
		if (g != null && i >= 0 && i < g.getNbVertices())
			return bestSol[i];
		return -1;
	}

	/**
	 * @return the total cost of the best known solution
	 */
	public Double getSolutionCost() {
		if (g != null)
			return bestSolCost;
		return -1.0;
	}

	/**
	 * Method that must be defined in TemplateTSP subclasses
	 * to instantiate variables.
	 */
	public abstract void init();

	/**
	 * Method that must be defined in TemplateTSP subclasses
	 * 
	 * @param currentVertex
	 * @param unvisited
	 * @return a lower bound of the cost of paths in <code>g</code> starting from
	 *         <code>currentVertex</code>, visiting every vertex in
	 *         <code>unvisited</code> exactly once, and returning back to vertex
	 *         <code>0</code>.
	 */
	protected abstract Double bound(Integer currentVertex, Collection<Integer> unvisited);

	/**
	 * Method that must be defined in TemplateTSP subclasses
	 * 
	 * @param currentVertex
	 * @param unvisited
	 * @param g
	 * @return an iterator for visiting all vertices in <code>unvisited</code> which
	 *         are successors of <code>currentVertex</code>
	 */
	protected abstract Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graph g);

	/**
	 * Template method of a branch and bound algorithm for solving the TSP in
	 * <code>g</code>.
	 * 
	 * @param currentVertex the last visited vertex
	 * @param unvisited     the set of vertex that have not yet been visited
	 * @param visited       the sequence of vertices that have been already visited
	 *                      (including currentVertex)
	 * @param currentCost   the cost of the path corresponding to
	 *                      <code>visited</code>
	 * @param discrepancy 	the current discrepancy for the LDS meta-heuristic
	 */
	private void branchAndBound(Integer currentVertex, Collection<Integer> unvisited, Collection<Integer> visited,
			Double currentCost, Integer discrepancy) {

		if (System.currentTimeMillis() - startTime > timeLimit)
			return;

		if (unvisited.size() == 0) {
			if (g.isArc(currentVertex, 0)) {
				if (currentCost + g.getCost(currentVertex, 0) < bestSolCost) {
					visited.toArray(bestSol);
					bestSolCost = currentCost + g.getCost(currentVertex, 0);
				}
			}
		} else if (discrepancy < maxDiscrepancy && currentCost + bound(currentVertex, unvisited) < bestSolCost) {
			Integer i = 0;
			Iterator<Integer> it = iterator(currentVertex, unvisited, g);
			while (it.hasNext()) {
				Integer nextVertex = it.next();
				visited.add(nextVertex);
				unvisited.remove(nextVertex);
				branchAndBound(nextVertex, unvisited, visited, currentCost + g.getCost(currentVertex, nextVertex),
						discrepancy + 2 * i);
				visited.remove(nextVertex);
				unvisited.add(nextVertex);
				++i;
			}
		}
	}

}