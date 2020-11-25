package algoTest;

import algo.Pcc;
import algo.CompleteGraph;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import algo.TSP;
import algo.TSP1;

public class testTSP {
	@Test
	void test() {
		TSP tsp = new TSP1();
		Pcc pcc = new Pcc();
		CompleteGraph graph = pcc.initTest();		
		
		long startTime = System.currentTimeMillis();
		tsp.searchSolution(20000, graph);
		System.out.print("Solution of cost "+tsp.getSolutionCost()+" found in "
				+(System.currentTimeMillis() - startTime)+"ms : ");
		for (int i=0; i<graph.getNbVertices(); i++)
			System.out.print(tsp.getSolution(i)+" ");
		System.out.println("0");
		
		assertTrue(tsp != null);
		/*
		ArrayList<Segment> lseg = new ArrayList<Segment>();
		Intersection inter1 = new Intersection(new Long(1), 0.0, 0.0, lseg);
		Intersection inter2 = new Intersection(new Long(2), 0.0, 0.0, lseg);
		Intersection inter3 = new Intersection(new Long(3), 0.0, 0.0, lseg);
		Intersection inter4 = new Intersection(new Long(4), 0.0, 0.0, lseg);
		Intersection inter5 = new Intersection(new Long(5), 0.0, 0.0, lseg);
		Intersection inter6 = new Intersection(new Long(6), 0.0, 0.0, lseg);
		List<Intersection> list = new ArrayList<>();
		list.add(inter1);
		list.add(inter2);
		list.add(inter3);
		list.add(inter4);
		list.add(inter5);
		list.add(inter6);
		for (int nbVertices = 8; nbVertices <= 16; nbVertices += 2){
			Intersection inter7 = new Intersection(new Long(nbVertices-1), 0.0, 0.0, lseg);
			Intersection inter8 = new Intersection(new Long(nbVertices), 0.0, 0.0, lseg);
			list.add(inter7);
			list.add(inter8);
			
			System.out.println("Graphs with "+nbVertices+" vertices:");
			Graph g = new CompleteGraph(list);
			long startTime = System.currentTimeMillis();
			tsp.searchSolution(20000, g);
			System.out.print("Solution of cost "+tsp.getSolutionCost()+" found in "
					+(System.currentTimeMillis() - startTime)+"ms : ");
			for (int i=0; i<nbVertices; i++)
				System.out.print(tsp.getSolution(i)+" ");
			System.out.println("0");
		}*/
	}
}
