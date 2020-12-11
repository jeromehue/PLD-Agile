package algo;

import java.util.Collection;
import java.util.Iterator;

import modele.Request;

/**
 * This class is an utility for computing the Traveling Salesman Problem on a
 * directed asymmetric graph.
 * 
 * @author H4414
 *
 */
public class TSP1 extends TemplateTSP {

	/**
	 * The request containing the tour's steps entered by the user.
	 */
	private Request request;

	/**
	 * Constructor to initialize all parameters of this.
	 * 
	 * @param graph   The graph on which the TSP will be computed.
	 * @param request The request containing the tour's steps entered by the user.
	 * @param dMax    The maximal discrepancy used for the LDS meta-heuristic.
	 */
	public TSP1(Graph graph, Request request, Integer dMax) {
		this.g = graph;
		this.request = request;
		this.maxDiscrepancy = dMax;
	}

	@Override
	protected Double bound(Integer currentVertex, Collection<Integer> unvisited) {

		if (this.bestSolCost == Integer.MAX_VALUE) {
			return 0.0;
		}

		Double min = 0.0;
		Double localMin;
		Double currentVertexMin = Double.MAX_VALUE;

		for (Integer startVertex : unvisited) {
			localMin = g.getCost(startVertex, 0);
			for (Integer targetVertex : unvisited) {
				if (!startVertex.equals(targetVertex) && g.getCost(startVertex, targetVertex) < localMin) {
					localMin = g.getCost(startVertex, targetVertex);
				}
			}
			min += localMin;
			if (g.getCost(currentVertex, startVertex) < currentVertexMin) {
				currentVertexMin = g.getCost(currentVertex, startVertex);
			}
		}

		min += currentVertexMin;

		return min;
	}

	@Override
	protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graph g) {
		return new SeqIter(unvisited, currentVertex, g, request);
	}
}
