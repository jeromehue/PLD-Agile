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
		
		//All intersection request Large7 :
		//25610888 - 26149156 - 26155372 - 34401989 - 2774590477 - 1362781062 - 26084604 - 27359745 - 891129815 - 25624140 - 1382784520 - 143403 - 21702421 - 194605322 - 26463669 -
		
		//All intersections request Large 9
		//25327124 - 1678996781 - 1362204817 - 130144280 - 26035105 - 25319255 - 25499154 - 1301805013 - 26084216 - 843129906 - 59654812 - 984553611 - 25624158 - 26464256 - 239603465 - 1370403192 - 1368674802 - 25310896 - 25316399 -
		
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
		
		System.out.println("");
		System.out.println("22COUCOU");
		
		//Long id = new Long(1362781062);
		long id = 2774590477L;
		Intersection inter = new Intersection(id, 0.0, 0.0, null);
		Intersection newPU = new Intersection(25327124L, 0.0, 0.0, null );
		Intersection newD = new Intersection(1678996781L, 0.0, 0.0, null );
		
		System.out.println("33COUCOU");

		
		//tour = pcc.changeOrder(tour, inter, -3);
		//tour = pcc.deleteIntersection(tour, inter);
		tour = pcc.addRequest(tour, newPU, newD, 120, 120, 3, 2);
		
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
