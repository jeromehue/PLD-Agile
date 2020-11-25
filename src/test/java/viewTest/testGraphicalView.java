package viewTest;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import algo.CompleteGraph;
import algo.Pcc;
import modele.CityMap;
import modele.Intersection;
import modele.Request;
import modele.Segment;
import modele.Tour;
import view.GraphicalView;
import xml.XMLCityMapParser;
import xml.XMLRequestParser;

public class testGraphicalView {
	public static void main(String[] args) {
		
	try
	{
		JFrame frame = new JFrame("test vue grapique");
		
		XMLCityMapParser p = new XMLCityMapParser("src/main/resources/largeMap.xml");
		CityMap cityMap = p.parse();
		
		XMLRequestParser p2 = new XMLRequestParser("src/main/resources/requestsLarge7.xml", cityMap);
		Request request = p2.parse();
		
		GraphicalView graphicalView = new GraphicalView(cityMap);
		graphicalView.setRequest(request);
		
		//create a Tour 
		Tour tour = new Tour(request);
		Pcc shortestPathComputer = new Pcc(cityMap, request);
		shortestPathComputer.computePcc();
		
		Intersection pickUpAdressTest = request.getStartingLocation();
		Intersection oldPickUpAdressTest;
		ArrayList<Segment> paths = new ArrayList<Segment>();
		Iterator<Intersection> itPickUpTest = request.getPickUpLocationsIterator();
		while(itPickUpTest.hasNext())
		{
			oldPickUpAdressTest = pickUpAdressTest;
			pickUpAdressTest = itPickUpTest.next();
			
			List<Segment> localPaths = shortestPathComputer.getRoads(oldPickUpAdressTest,pickUpAdressTest);
			paths.addAll(localPaths);
			
		}
		tour.setPath(paths);
			
		graphicalView.setTour(tour);
		
		frame.getContentPane().add(graphicalView);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(1000,1000);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	catch(Exception e)
	{
		System.out.println("Exception when testing graphicalView: " + e);
	}
		
		
		
		
		
		
	}
}
