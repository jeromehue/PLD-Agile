package algoTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import algo.Pcc;
import modele.CityMap;
import modele.Request;
import modele.Tour;
import modele.Way;
import xml.InvalidRequestException;
import xml.XMLCityMapParser;
import xml.XMLRequestParser;

public class TestComputeTour {

	@Test
	void test() {
		
		System.out.println("TEST\n-----------------" + "testTSP.java : test");
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
		
		Tour tour = pcc.computeTour();
		
		List<Way> wayList = tour.getwaysList();
		
		for(Way w : wayList) {
			System.out.println(w.getDeparture() +"->"+w.getArrival() );
		}
		
		assertTrue(tour != null);
	
	}
}
