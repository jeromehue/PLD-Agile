package viewTest;


import javax.swing.JFrame;

import modele.CityMap;
import modele.Request;
import view.GraphicalView;
import xml.XMLCityMapParser;
import xml.XMLRequestParser;

public class testGraphicalView {
	public static void main(String[] args) {
		JFrame frame = new JFrame("test vue grapique");
		
		XMLCityMapParser p = new XMLCityMapParser("src/main/resources/largeMap.xml");
		CityMap cityMap = p.parse();
		
		XMLRequestParser p2 = new XMLRequestParser("src/main/resources/requestsLarge7.xml", cityMap);
		Request request = p2.parse();
		
		GraphicalView graphicalView = new GraphicalView(cityMap);
		
		graphicalView.setRequest(request);
		
		//Tour tour = new Tour(request);
		
		/*//initialisation of tour
		long pickUpAdressTest = request.getStartingLocation();
		long oldPickUpAdressTest;
		//long deliveryAdressTest;
		Segment newPath;
		ArrayList<Segment> paths = new ArrayList<Segment>();
		Iterator<Long> itPickUpTest = request.getPickUpLocationsIterator();
		//Iterator<Long> itDeliveryTest = request.getDeliveryLocationsIterator();
		while(itPickUpTest.hasNext())
		{
			oldPickUpAdressTest = pickUpAdressTest;
			pickUpAdressTest = itPickUpTest.next();
			//deliveryAdressTest = itDeliveryTest.next();
			newPath = new Segment(1.0 , " " ,cityMap.getIntersectionFromAddress(oldPickUpAdressTest),cityMap.getIntersectionFromAddress(pickUpAdressTest));
			paths.add(newPath);
		}
		tour.setPath(paths);*/
		
		//graphicalView.setTour(tour);
		
		frame.getContentPane().add(graphicalView);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(1000,1000);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
