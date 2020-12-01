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
		//for (i=0; i< g.getNbVertices(); i++) { 
		System.out.println("le cout minimum est "+vertexMinimum.peek());
		//}
	}
	
	@Override
	protected double bound(Integer currentVertex, Collection<Integer> unvisited) {
		/*PriorityQueue<Double> minimumQueue = new PriorityQueue<>();
		for(Integer index : unvisited) {
			minimumQueue.add( g.getCost(currentVertex, index));
		}
		*/
		
		if(this.bestSolCost == Integer.MAX_VALUE) {
			return 0;
		}
		
		double min= 0;
		double localMin;
		for(Integer startVertice : unvisited) {
			localMin = g.getCost(startVertice, 0);
			for(Integer targetVertice : unvisited) {
				if(startVertice != targetVertice && g.getCost(startVertice, targetVertice) < localMin) {
					localMin = g.getCost(startVertice, targetVertice);
				}
			}
			min += localMin;
		}
		
		return min;
		
		//return (int)((unvisited.size()+1) * vertexMinimum.peek());
		//return 0;
	}

	@Override
	protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graph g) {
		return new SeqIter(unvisited, currentVertex, g, request);
	}

}
