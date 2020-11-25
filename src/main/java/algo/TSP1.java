package algo;

import java.util.Collection;
import java.util.Iterator;
import java.util.PriorityQueue;

public class TSP1 extends TemplateTSP {
	private PriorityQueue<Double> minimumQueue;
	
	/*
	protected void init () {
		minimumQueue = new PriorityQueue<Double>();
		int i=0;
		int j=0;
		for(i=0; i<g.getNbVertices(); i++) {
			for(j=0; j<g.getNbVertices(); j++) {
				if(i!=j) {
					minimumQueue.add( g.getCost(i, i));
				}
			}
		}
		System.out.println("minimum = "+minimumQueue.peek());
	}*/
	
	@Override
	protected int bound(Integer currentVertex, Collection<Integer> unvisited) {
		minimumQueue = new PriorityQueue<>();
		for(Integer index : unvisited) {
			minimumQueue.add( g.getCost(currentVertex, index));
		}
		
		//return (int)((unvisited.size()+1) * minimumQueue.peek());
		return 0;
	}

	@Override
	protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graph g) {
		return new SeqIter(unvisited, currentVertex, g);
	}

}
