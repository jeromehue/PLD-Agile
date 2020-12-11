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
import xml.XMLCityMapParser;
import xml.XMLRequestParser;

public class TestComputeTour {

	@Test
	void test() {

		System.out.println("TEST\n-----------------" + "TestComputeTour.java : test");
		XMLCityMapParser cmpp = new XMLCityMapParser("src/main/resources/largeMap.xml");

		CityMap city = new CityMap();
		try {
			city = cmpp.parse();
		} catch (Exception e) {
			System.err.println("Error while parsing map");
			e.printStackTrace();
			fail();
		}

		assertTrue(city.getIntersections() != null);
		assertTrue(city.getIntersections().size() > 7);

		XMLRequestParser rp = new XMLRequestParser("./src/main/resources/requestsLarge7.xml", city);
		Request request = new Request();
		try {
			request = rp.parse();
		} catch (Exception e) {
			System.err.println("Error while parsing requests");
			e.printStackTrace();
			fail();
		}

		// All intersection request Large7 :
		// 25610888 - 26149156 - 26155372 - 34401989 - 2774590477 - 1362781062 -
		// 26084604 - 27359745 - 891129815 - 25624140 - 1382784520 - 143403 - 21702421 -
		// 194605322 - 26463669 -

		// All intersections request Large 9
		// 25327124 - 1678996781 - 1362204817 - 130144280 - 26035105 - 25319255 -
		// 25499154 - 1301805013 - 26084216 - 843129906 - 59654812 - 984553611 -
		// 25624158 - 26464256 - 239603465 - 1370403192 - 1368674802 - 25310896 -
		// 25316399 -

		Pcc pcc = new Pcc(city, request);

		Tour tour = pcc.computeGooodTSPTour();

		List<Way> oldWayList = tour.getWaysList();

		for (Way w : oldWayList) {
			System.out.print(w.getDeparture().getId() + " - ");
			if (!tour.isPositionConsistent(w.getDeparture().getId())) {
				System.out.print("!!!");
			}
		}

		Intersection inter = new Intersection(34401989L, 0.0, 0.0, null);

		tour = pcc.changeOrder(tour, inter, -3);

		List<Way> wayList = tour.getWaysList();

		for (Way w : wayList) {
			System.out.print(w.getDeparture().getId());
			if (!tour.isPositionConsistent(w.getDeparture().getId())) {
				System.out.print("!!!");
			}
			System.out.print(" - ");
		}

		assertTrue(tour != null);
		assertTrue(wayList != null);

	}
}
