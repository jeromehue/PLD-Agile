package algoTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import algo.CompleteGraph;
import algo.Graph;
import algo.Pcc;
import modele.CityMap;
import modele.Request;
import modele.Segment;
import modele.Intersection;
import xml.XMLCityMapParser;
import xml.XMLRequestParser;

class TestPcc {

	@Test
	void globalTest() {
		System.out.println("GLOBAL_TEST\n-----------------" + "TestPcc.java : globalTest");
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

		Pcc pcc = new Pcc(city, request);

		CompleteGraph graph = pcc.computePcc();
		// System.out.println(graph.toString());

		ArrayList<Intersection> deliveries = request.getDeliveryLocations();
		Integer randomDeliveryLocation = (int) ((Math.random() * deliveries.size()));

		List<Segment> chemin = pcc.getRoads(request.getStartingLocation(), deliveries.get(randomDeliveryLocation));

		Intersection passage = chemin.get(0).getOrigin();

		System.out.println("\n\nOn commence au point (" + passage.getLatitude() + " ; " + passage.getLongitude() + ")");
		for (Segment s : chemin) {
			passage = s.getDestination();
			System.out.print("On prend la rue : ");
			System.out.println(s.getName());

			System.out.println("On passe au point (" + passage.getLatitude() + " ; " + passage.getLongitude() + ")");
		}
		System.out.println();

		for (int i = 0; i < graph.getNbVertices(); ++i) {
			for (int j = 0; j < graph.getNbVertices(); ++j) {
				if (i == j) {
					assertTrue(graph.getCost(i, j) == 0.0);
				} else {
					assertTrue(graph.getCost(i, j) != 0.0);
				}
			}
		}
	}

	@Test
	void testComputePcc() {
		System.out.println("TEST_COMPUTE_PCC\n-----------------" + "TestPcc.java : testComputePcc");
		XMLCityMapParser cmpp = new XMLCityMapParser("src/main/resources/smallMap.xml");
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

		XMLRequestParser rp = new XMLRequestParser("./src/main/resources/requestsSmall1.xml", city);
		Request request = new Request();
		try {
			request = rp.parse();
		} catch (Exception e) {
			System.err.println("Error while parsing requests");
			e.printStackTrace();
			fail();
		}

		Pcc pcc = new Pcc(city, request);

		Graph g = pcc.computePcc();
		double costs[][] = { { 0.0, 355.547331, 2387.5813504000002 }, { 231.600831, 0.0, 2376.2555774 },
				{ 2746.8408613999995, 2676.8069857000005, 0.0 } };
		boolean similarCosts = true;
		for (int i = 0; i < g.getNbVertices(); i++) {
			for (int j = 0; j < g.getNbVertices(); j++) {
				if (costs[i][j] != g.getCost(i, j)) {
					similarCosts = false;
					break;
				}
			}
		}
		assert (similarCosts);
	}

	@Test
	void testGetRoads() {
		System.out.println("TEST_COMPUTE_PCC\n-----------------" + "TestPcc.java : testGetRoads");
		XMLCityMapParser cmpp = new XMLCityMapParser("src/main/resources/smallMap.xml");
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

		XMLRequestParser rp = new XMLRequestParser("./src/main/resources/requestsSmall1.xml", city);
		Request request = new Request();
		try {
			request = rp.parse();
		} catch (Exception e) {
			System.err.println("Error while parsing requests");
			e.printStackTrace();
			fail();
		}

		Pcc pcc = new Pcc(city, request);
		pcc.computePcc();

		// Tour t = pcc.computeGooodTSPTour();
		System.out.print(request.getStartingLocation().getId() + " - ");
		for (Intersection inter : request.getDeliveryLocations()) {
			System.out.print(inter.getId() + " - ");
		}
		for (Intersection inter : request.getPickUpLocations()) {
			System.out.print(inter.getId() + " - ");
		}

		boolean sameName = true;
		Intersection start = new Intersection(342873658L, 0.0, 0.0, null);
		Intersection finish = new Intersection(25173820L, 0.0, 0.0, null);
		List<Segment> listToCompare = pcc.getRoads(start, finish);
		List<String> nameList = new ArrayList<String>();

		nameList.add("Rue Richelieu");
		nameList.add("Rue Richelieu");
		nameList.add("Rue Richelieu");
		nameList.add("");
		nameList.add("");
		nameList.add("Rue Richelieu");

		nameList.add("Rue Frédéric Mistral");
		nameList.add("Rue Frédéric Mistral");
		nameList.add("Rue Frédéric Mistral");
		nameList.add("Rue Frédéric Mistral");

		nameList.add("Avenue Félix Faure");
		nameList.add("Avenue Félix Faure");
		nameList.add("Avenue Félix Faure");
		nameList.add("Avenue Félix Faure");
		nameList.add("Avenue Félix Faure");
		nameList.add("Avenue Félix Faure");
		nameList.add("Avenue Félix Faure");
		nameList.add("Avenue Félix Faure");
		nameList.add("Avenue Félix Faure");
		nameList.add("Avenue Félix Faure");

		nameList.add("Rue du Général Mouton-Duvernet");
		nameList.add("Rue du Général Mouton-Duvernet");
		nameList.add("Rue du Général Mouton-Duvernet");
		nameList.add("Rue du Général Mouton-Duvernet");
		nameList.add("Rue du Général Mouton-Duvernet");

		nameList.add("Cours Albert Thomas");
		nameList.add("");
		nameList.add("Cours Gambetta");

		int i = 0;
		System.out.println("Affichage des rues:");
		for (Segment s : listToCompare) {
			System.out.println(s.getName() + " - ");
			if (!s.getName().equals(nameList.get(i))) {
				sameName = false;
			}
			i++;
		}
		assert (sameName);
	}

}
