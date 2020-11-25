package viewTest;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import algo.Pcc;
import modele.CityMap;
import modele.Intersection;
import modele.Request;
import modele.Segment;
import modele.Tour;
import view.GraphicalView;
import xml.InvalidRequestException;
import xml.XMLCityMapParser;
import xml.XMLRequestParser;

public class testGraphicalView {
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("test vue grapique");
		
		XMLCityMapParser p = new XMLCityMapParser("src/main/resources/largeMap.xml");
		CityMap cityMap = p.parse();
		
		XMLRequestParser p2 = new XMLRequestParser("src/main/resources/requestsLarge7.xml", cityMap);
		Request request = new Request();
		try {
			request = p2.parse();
		} catch (InvalidRequestException e) {
			System.err.println("Error while parsing request");
			e.printStackTrace();
			System.exit(0);
		}
		
		GraphicalView graphicalView = new GraphicalView(cityMap);
		graphicalView.setRequest(request);
		
		//create a Tour 
		Tour tour = new Tour(request);
		Pcc shortestPathComputer = new Pcc(cityMap, request);
		shortestPathComputer.computePcc();
		
		Intersection intersection = request.getStartingLocation();
		Intersection oldIntersecction;
		ArrayList<Segment> paths = new ArrayList<Segment>();
		Iterator<Intersection> itPickUpTest = request.getPickUpLocationsIterator();
		while(itPickUpTest.hasNext())
		{
			oldIntersecction = intersection;
			intersection = itPickUpTest.next();
			
			List<Segment> localPaths = shortestPathComputer.getRoads(oldIntersecction,intersection);
			paths.addAll(localPaths);
			
		}
		Iterator<Intersection> itDeliveryTest = request.getDeliveryLocationsIterator();
		while(itDeliveryTest.hasNext())
		{
			oldIntersecction = intersection;
			intersection = itDeliveryTest.next();
			
			List<Segment> localPaths2 = shortestPathComputer.getRoads(oldIntersecction,intersection);
			paths.addAll(localPaths2);
			
		}
		tour.setPath(paths);
			
		graphicalView.setTour(tour);
		
		frame.getContentPane().add(graphicalView);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(1000,1000);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
