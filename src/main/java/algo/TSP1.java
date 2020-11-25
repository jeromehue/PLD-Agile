package algo;

import java.util.Collection;
import java.util.Iterator;
import java.util.PriorityQueue;

public class TSP1 extends TemplateTSP {
	private Double [] minimumCost;
	
	protected void init () {
		minimumCost = new Double [g.getNbVertices()];
		PriorityQueue<Double> vertexMinimum;
		int i=0;
		int j=0;
		for(i=0; i<g.getNbVertices(); i++) {
			vertexMinimum = new PriorityQueue<>();
			for(j=0; j<g.getNbVertices(); j++) {
				if(i!=j) {
					vertexMinimum.add( g.getCost(i, i));
				}
			}
			minimumCost[i] = vertexMinimum.peek();
		}
		for (i=0; i< g.getNbVertices(); i++) { 
			System.out.println("minimum depuis le sommet "+i+" est "+minimumCost[i]);
		}
	}
	
	@Override
	protected int bound(Integer currentVertex, Collection<Integer> unvisited) {
		PriorityQueue<Double> minimumQueue = new PriorityQueue<>();
		for(Integer index : unvisited) {
			minimumQueue.add( g.getCost(currentVertex, index));
		}
		
		return (int)((unvisited.size()+1) * minimumQueue.peek());
		//return 0;
	}

	@Override
	protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graph g) {
		return new SeqIter(unvisited, currentVertex, g);
	}

}
