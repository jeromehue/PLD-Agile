package viewTest;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import modele.CityMap;
import modele.Request;
import modele.Tour;
import view.GraphicalView;
import xml.XMLCityMapParser;
import xml.XMLRequestParser;

public class testGraphicalView {
	@Test
	void test() {
		JFrame frame = new JFrame("test vue grapique");

		XMLCityMapParser p = new XMLCityMapParser("src/main/resources/largeMap.xml");
		CityMap cityMap = new CityMap();
		try {
			cityMap = p.parse();
		} catch (Exception e) {
			System.err.println("Error while parsing requests");
			e.printStackTrace();
			System.exit(0);
		}

		XMLRequestParser p2 = new XMLRequestParser("src/main/resources/requestsLarge7.xml", cityMap);
		Request request = new Request();
		try {
			request = p2.parse();
		} catch (Exception e) {
			System.err.println("Error while parsing requests");
			e.printStackTrace();
			System.exit(0);
		}

		// set graphical view
		GraphicalView graphicalView = new GraphicalView(new Tour());
		graphicalView.setCityMap(cityMap);
		graphicalView.setRequest(request);

		frame.getContentPane().add(graphicalView);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(1000, 1000);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
