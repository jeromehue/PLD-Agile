package algoTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import algo.CompleteGraph;
import algo.Pcc;
import modele.CityMap;
import modele.Request;
import modele.Segment;
import modele.Intersection;
import xml.InvalidRequestException;
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
		Request request = new Request();
		try {
			request = rp.parse();
		} catch (InvalidRequestException e) {
			e.printStackTrace();
			fail();
		}
		
		Pcc pcc = new Pcc(city, request);
		
		CompleteGraph graph = pcc.computePcc();
		//System.out.println(graph.toString());
		
		ArrayList<Intersection> deliveries = request.getDeliveryLocations();
		Integer randomDeliveryLocation = (int)((Math.random()*deliveries.size()));

		List<Segment> chemin = pcc.getRoads(request.getStartingLocation(), deliveries.get(randomDeliveryLocation));
		
		Intersection passage = chemin.get(0).getOrigin();

		System.out.println("\n\nOn commence au point (" + passage.getLatitude() + " ; " + passage.getLongitude() + ")");
		for(Segment s : chemin) {
			passage = s.getDestination();
			System.out.print("On prend la rue : ");
			System.out.println(s.getName());
			
			System.out.println("On passe au point (" + passage.getLatitude() + " ; " + passage.getLongitude() + ")");
		}
		System.out.println();
		
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
