package algo;

import java.util.Collection;
import java.util.Iterator;
import java.util.PriorityQueue;

import modele.Request;

public class TSP1 extends TemplateTSP {
	private PriorityQueue<Double> vertexMinimum;
	private Request request;
	
	public TSP1(Graph graph, Request request, Integer dMax) {
		this.g = graph;
		this.request = request;
		this.maxDiscrepancy = dMax;
	}
	
	@Override
	public void init() {
		vertexMinimum = new PriorityQueue<>();
		Integer i=0;
		Integer j=0;
		for(i = 0; i < g.getNbVertices(); i++) {
			for(j=0; j < g.getNbVertices(); j++) {
				if(i != j) {
					vertexMinimum.add(g.getCost(i, j));
				}
			}
		}
	}
	
	@Override
	protected Double bound(Integer currentVertex, Collection<Integer> unvisited) {
		
		if(this.bestSolCost == Integer.MAX_VALUE) {
			return 0.0;
		}
		
		Double min = 0.0;
		Double localMin;
		Double currentVertexMin = Double.MAX_VALUE;
		
		for(Integer startVertex : unvisited) {
			localMin = g.getCost(startVertex, 0);
			for(Integer targetVertex : unvisited) {
				if(startVertex != targetVertex && g.getCost(startVertex, targetVertex) < localMin) {
					localMin = g.getCost(startVertex, targetVertex);
				}
			}
			min += localMin;
			if(g.getCost(currentVertex, startVertex) < currentVertexMin) {
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
