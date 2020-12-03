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
		
		
		Pcc pcc = new Pcc(city, request);
		
		Tour tour = pcc.computeGooodTSPTour();
		
		Long id = new Long(60755991);
		Intersection inter = new Intersection(id, 0.0, 0.0, null);
		
		tour = pcc.changeOrder(tour, inter, -3);
		
		List<Way> wayList = tour.getwaysList();
		
		for(Way w : wayList) {
			System.out.println("start at "+w.getStartingTime()+", goes from "+w.getDeparture().getId() +" at "+w.getDepartureTime()+
					" -> arrives at "+w.getArrival().getId() +" at "+w.getArrivalTime());
		}
		
		assertTrue(tour != null);
		assertTrue(wayList != null);

		
	}
}
