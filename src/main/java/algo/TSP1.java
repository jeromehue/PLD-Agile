package algo;

import java.util.Collection;
import java.util.Iterator;
import java.util.PriorityQueue;

import modele.Request;

public class TSP1 extends TemplateTSP {
	private PriorityQueue<Double> vertexMinimum;
	private Request request;
	
	public TSP1(Graph graph, Request request) {
		this.g = graph;
		this.request = request;
	}
	
	@Override
	public void init() {
		vertexMinimum = new PriorityQueue<>();
		int i=0;
		int j=0;
		for(i=0; i<g.getNbVertices(); i++) {
			for(j=0; j<g.getNbVertices(); j++) {
				if(i!=j) {
					vertexMinimum.add( g.getCost(i, j));
				}
			}
		}
	}
	
	@Override
	protected double bound(Integer currentVertex, Collection<Integer> unvisited) {
		
		if(this.bestSolCost == Integer.MAX_VALUE) {
			return 0;
		}
		
		double min = 0;
		double localMin;
		double currentVertexMin = Double.MAX_VALUE;
		
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
