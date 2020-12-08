package algo;

/**
 * This class is an interface for a graph.
 * 
 * @author H4414
 *
 */
public interface Graph {
	/**
	 * @return The number of vertices in <code>this</code>.
	 */
	public abstract int getNbVertices();

	/**
	 * @param i The index of the first vertex.
	 * @param j The index of the second vertex.
	 * @return The cost of arc (i,j) if (i,j) is an arc; -1 otherwise.
	 */
	public abstract double getCost(int i, int j);

	/**
	 * @param i The index of the first vertex.
	 * @param j The index of the first vertex.
	 * @return True if <code>(i,j)</code> is an arc of <code>this</code>.
	 */
	public abstract boolean isArc(int i, int j);

	/**
	 * @return The index of the costs matrix of the intersection whose id is passed.
	 * 
	 * @param id Intersection's id whose index is wanted.
	 */
	public abstract Integer getIndex(Long id);

	/**
	 * @return The intersection whose index of the costs matrix is passed.
	 * 
	 * @param index Index in the costs matrix of an intersection.
	 */
	public abstract Long getIdFromIndex(Integer index);
}
