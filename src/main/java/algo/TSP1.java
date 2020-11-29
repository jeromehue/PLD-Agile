package algo;

import java.util.Collection;
import java.util.Iterator;
import java.util.PriorityQueue;

public class TSP1 extends TemplateTSP {
	private PriorityQueue<Double> vertexMinimum;
	
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
	protected int bound(Integer currentVertex, Collection<Integer> unvisited) {
		/*PriorityQueue<Double> minimumQueue = new PriorityQueue<>();
		for(Integer index : unvisited) {
			minimumQueue.add( g.getCost(currentVertex, index));
		}*/
		
		return (int)((unvisited.size()+1) * vertexMinimum.peek());
		//return 0;
	}

	@Override
	protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graph g) {
		//return new SeqIter(unvisited, currentVertex, g);
		return null;
	}

}
