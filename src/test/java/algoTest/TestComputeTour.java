package algoTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import algo.Pcc;
import modele.CityMap;
import modele.Intersection;
import modele.Request;
import modele.Tour;
import modele.Way;
import xml.InvalidRequestException;
import xml.XMLCityMapParser;
import xml.XMLRequestParser;

public class TestComputeTour {

	@Test
	void test() {
		

		System.out.println("TEST\n-----------------" + "TestComputeTour.java : test");
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
		
		System.out.println("1COUCOU");
		
		//All intersection :
		//4150019167 - 26155372 - 26317393 - 25610684 - 21717915 - 21992645 - 1400900990 - 1036842078 - 208769083 - 55444215 -
		
		Pcc pcc = new Pcc(city, request);
		System.out.println("111COUCOU");

		Tour tour = pcc.computeGooodTSPTour();
		System.out.println("2COUCOU");

		List<Way> oldWayList = tour.getWaysList();
		System.out.println("3COUCOU");

		System.out.println("\n");
		for(Way w : oldWayList) {
			System.out.print(w.getDeparture().getId()+ " - ");
			if(!tour.isPositionConsistent(w.getDeparture().getId())) {
				System.out.print("!!!");
			}
		}
		
		System.out.println("22COUCOU");
		
		Long id = new Long(21717915);
		Intersection inter = new Intersection(id, 0.0, 0.0, null);
		
		System.out.println("3COUCOU");

		
		tour = pcc.changeOrder(tour, inter, 10);
		//tour = pcc.deleteIntersection(tour, inter);
		
		System.out.println("4COUCOU");

		
		List<Way> wayList = tour.getWaysList();
		
		/*for(Way w : wayList) {
			System.out.println("start at "+w.getStartingTime()+", goes from "+w.getDeparture().getId() +" at "+w.getDepartureTime()+
					" -> arrives at "+w.getArrival().getId() +" at "+w.getArrivalTime());
		}*/
		
		System.out.println("5COUCOU");

		
		System.out.println("\n");
		for(Way w : wayList) {
			System.out.print(w.getDeparture().getId());
			if(!tour.isPositionConsistent(w.getDeparture().getId())) {
				System.out.print("!!!");
			}
			System.out.print(" - ");
		}
		
		System.out.println("6COUCOU");

		
		assertTrue(tour != null);
		assertTrue(wayList != null);

		
	}
}
