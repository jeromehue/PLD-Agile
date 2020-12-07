package algo;

/**
 * This class is an interface for a graph.
 * 
 * @author H4414
 *
 */
public interface Graph {
	/**
	 * @return the number of vertices in <code>this</code>
	 */
	public abstract int getNbVertices();

	/**
	 * @param i
	 * @param j
	 * @return the cost of arc (i,j) if (i,j) is an arc; -1 otherwise
	 */
	public abstract double getCost(int i, int j);

	/**
	 * @param i
	 * @param j
	 * @return true if <code>(i,j)</code> is an arc of <code>this</code>
	 */
	public abstract boolean isArc(int i, int j);

	/**
	 * @param id
	 * @return the index of the costs matrix corresponding to the
	 *         <code>Intersection<code>
	 */
	public abstract Integer getIndex(Long id);

	/**
	 * @param index
	 * @return the id of the <code>Intersection<code> corresponding to the index of
	 *         costs matrix
	 */
	public abstract Long getIdFromIndex(Integer index);
}
