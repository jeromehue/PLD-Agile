package algoTest;

import algo.Pcc;
import algo.CompleteGraph;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import algo.TSP1;
import modele.CityMap;
import modele.Request;
import xml.InvalidRequestException;
import xml.XMLCityMapParser;
import xml.XMLRequestParser;

public class testTSP {
	@Test
	void test() {
		
		System.out.println("TEST\n-----------------" + "testTSP.java : test");
		XMLCityMapParser cmpp = new XMLCityMapParser("src/main/resources/largeMap.xml");
		CityMap city = cmpp.parse();
		
		assertTrue(city.getIntersections() != null);
		assertTrue(city.getIntersections().size() > 7);

		
		XMLRequestParser rp = new XMLRequestParser("./src/main/resources/requestsLarge7.xml", city);
		Request request = new Request();
		try {
			request = rp.parse();
		} catch (InvalidRequestException e) {
			e.printStackTrace();
			fail();
		}
		
		
		Pcc pcc = new Pcc(city, request);
		
		CompleteGraph graph = pcc.computePcc();

		TSP1 tsp = new TSP1(graph, request);

		tsp.init();
		
		long startTime = System.currentTimeMillis();
		tsp.searchSolution(40000);
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
