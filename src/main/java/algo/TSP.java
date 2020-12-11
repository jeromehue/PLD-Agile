package algo;

/**
 * This class is an interface for computing the Traveling Salesman Problem on a
 * directed asymmetric graph.
 * 
 * @author H4414
 *
 */
public interface TSP {
	/**
	 * Search for a shortest cost hamiltonian circuit in <code>g</code> within
	 * <code>timeLimit</code> milliseconds (returns the best found tour whenever the
	 * time limit is reached). Warning: The computed tour always start from vertex
	 * 0.
	 * 
	 * @param timeLimit The time limit to not exceed.
	 */
	public abstract void searchSolution(Integer timeLimit);

	/**
	 * This method is supposed to be looped over in order to get
	 * 
	 * @param i The index of the tour's step for which we wish the index of the
	 *          corresponding intersection, according to the best known solution.
	 * 
	 * @return The i-th vertex in the solution computed by
	 *         <code>searchSolution</code> (-1 if <code>searcheSolution</code> has
	 *         not been called yet, or if i is invalid).
	 */
	public abstract Integer getSolution(Integer i);

	/**
	 * @return The total cost of the solution computed by
	 *         <code>searchSolution</code> (-1 if <code>searcheSolution</code> has
	 *         not been called yet).
	 */
	public abstract Double getSolutionCost();

}
