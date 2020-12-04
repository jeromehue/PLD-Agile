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
		XMLCityMapParser cmpp = new XMLCityMapParser("src/main/resources/mediumMap.xml");
		CityMap city = cmpp.parse();
		
		assertTrue(city.getIntersections() != null);
		assertTrue(city.getIntersections().size() > 7);

		
		XMLRequestParser rp = new XMLRequestParser("./src/main/resources/requestsMedium5.xml", city);
		Request request = new Request();
		try {
			request = rp.parse();
		} catch (InvalidRequestException e) {
			e.printStackTrace();
			fail();
		}
		
		//All intersection :
		//4150019167 - 26155372 - 26317393 - 25610684 - 21717915 - 21992645 - 1400900990 - 1036842078 - 208769083 - 55444215 -
		
		Pcc pcc = new Pcc(city, request);
		
		Tour tour = pcc.computeGooodTSPTour();
		List<Way> oldWayList = tour.getWaysList();
		System.out.println("\n");
		for(Way w : oldWayList) {
			System.out.print(w.getDeparture().getId()+ " - ");
			if(!tour.isPositionConsistent(w.getDeparture().getId())) {
				System.out.print("!!!");
			}
		}
		
		Long id = new Long(21717915);
		Intersection inter = new Intersection(id, 0.0, 0.0, null);
		
		tour = pcc.changeOrder(tour, inter, 10);
		//tour = pcc.deleteIntersection(tour, inter);
		
		List<Way> wayList = tour.getWaysList();
		
		/*for(Way w : wayList) {
			System.out.println("start at "+w.getStartingTime()+", goes from "+w.getDeparture().getId() +" at "+w.getDepartureTime()+
					" -> arrives at "+w.getArrival().getId() +" at "+w.getArrivalTime());
		}*/
		
		System.out.println("\n");
		for(Way w : wayList) {
			System.out.print(w.getDeparture().getId());
			if(!tour.isPositionConsistent(w.getDeparture().getId())) {
				System.out.print("!!!");
			}
			System.out.print(" - ");
		}
		
		assertTrue(tour != null);
		assertTrue(wayList != null);

		
	}
}
