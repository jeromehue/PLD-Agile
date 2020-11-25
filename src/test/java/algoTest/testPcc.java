package algoTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import algo.CompleteGraph;
import algo.Pcc;
import modele.CityMap;
import modele.Intersection;
import modele.Request;
import xml.XMLCityMapParser;
import xml.XMLRequestParser;

class testPcc {

	@Test
	void test() {
		
		XMLCityMapParser cmpp = new XMLCityMapParser("src/main/resources/smallMap.xml");
		CityMap city = cmpp.parse();
		
		assertTrue(city.getIntersections() != null);
		assertTrue(city.getIntersections().size() > 7);

		
		XMLRequestParser rp = new XMLRequestParser("./src/main/resources/requestsSmall1.xml", city);
		Request request = rp.parse();
		
		ArrayList<Intersection> del = new ArrayList<Intersection>();
		del.addAll(city.getIntersections().subList(1, 3));
		
		ArrayList<Intersection> pi = new ArrayList<Intersection>();
		pi.addAll(city.getIntersections().subList(4, 7));
		
		
		Pcc pcc = new Pcc(city, request);
		
		CompleteGraph graph = pcc.computePcc();
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
	
	@Test
	void testCustomInit() {
		Pcc pcc = new Pcc();
		CompleteGraph graph = pcc.initTest();
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
