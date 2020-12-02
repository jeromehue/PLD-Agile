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
		XMLCityMapParser cmpp = new XMLCityMapParser("src/main/resources/smallMap.xml");
		CityMap city = cmpp.parse();
		
		assertTrue(city.getIntersections() != null);
		assertTrue(city.getIntersections().size() > 7);

		
		XMLRequestParser rp = new XMLRequestParser("./src/main/resources/requestsSmall2.xml", city);
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
		
		Long startTime = System.currentTimeMillis();
		tsp.searchSolution(40000);
		
		System.out.print("Solution of cost "+(int)Math.round(tsp.getSolutionCost())+"m found in "
				+(System.currentTimeMillis() - startTime)+"ms : ");
		System.out.println("0 " + graph.getIdFromIndex(tsp.getSolution(0)));
		
		for (Integer i=0; i<graph.getNbVertices(); i++) {
			System.out.println(tsp.getSolution(i)+" "+graph.getIdFromIndex(tsp.getSolution(i)));
		}
		System.out.println("0 " + graph.getIdFromIndex(tsp.getSolution(0)));
		
		assertTrue(tsp != null);
	}
}
