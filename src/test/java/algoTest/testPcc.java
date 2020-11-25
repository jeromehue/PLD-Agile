package algoTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import algo.CompleteGraph;
import algo.Pcc;
import modele.CityMap;
import modele.Request;
import modele.Intersection;
import xml.XMLCityMapParser;
import xml.XMLRequestParser;

class testPcc {

	@Test
	void test() {
		System.out.println("TEST\n-----------------" + "testPcc.java : test");
		XMLCityMapParser cmpp = new XMLCityMapParser("src/main/resources/smallMap.xml");
		CityMap city = cmpp.parse();
		
		assertTrue(city.getIntersections() != null);
		assertTrue(city.getIntersections().size() > 7);

		
		XMLRequestParser rp = new XMLRequestParser("./src/main/resources/requestsSmall1.xml", city);
		Request request = rp.parse();
		
		
		
		Pcc pcc = new Pcc(city, request);
		
		CompleteGraph graph = pcc.computePcc();
		//System.out.println(graph.toString());
		
		ArrayList<Intersection> deliveries = request.getDeliveryLocations();
		Integer randomDeliveryLocation = (int)((Math.random()*deliveries.size()));

		pcc.getRoads(request.getStartingLocation(), deliveries.get(randomDeliveryLocation));
		for(int i = 0 ; i < graph.getNbVertices() ; ++i) {
			for(int j = 0 ; j < graph.getNbVertices() ; ++j) {
				if(i == j) {
					assertTrue(graph.getCost(i, j) == 0.0);
				} else {
					assertTrue(graph.getCost(i, j) != 0.0);
				}
			}
		}
	}
	
	@Test
	void testCustomInit() {
		System.out.println("TEST\n-----------------" + "testPcc.java : testCustomInit");

		Pcc pcc = new Pcc();
		CompleteGraph graph = pcc.initTest();
		/*
		Intersection inter1 = new Intersection(new Long(1), 0.0, 0.0, new ArrayList<Segment>() ); 
		Intersection inter6 = new Intersection(new Long(6), 0.0, 0.0, new ArrayList<Segment>() ); 
		pcc.getRoads(inter1, inter6);
		*/
		//System.out.println(graph.toString());

		for(int i = 0 ; i < graph.getNbVertices() ; ++i) {
			for(int j = 0 ; j < graph.getNbVertices() ; ++j) {
				if(i == j) {
					assertTrue(graph.getCost(i, j) == 0.0);
				} else {
					assertTrue(graph.getCost(i, j) != 0.0);
				}
			}
		}
	}
}
