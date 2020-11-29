package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import algo.Pcc;
import modele.CityMap;
import modele.Intersection;
import modele.Request;
import modele.Segment;
import modele.Tour;
import view.Window;
import xml.InvalidRequestException;
import xml.XMLCityMapParser;
import xml.XMLRequestParser;

public class RequestLoadedState implements State {

	
		@Override
		public void loadMap(Controller c, Window w, Tour t) {
			
			String path = w.createDialogBoxToGetFilePath();
			if(path != null) 
			{
				System.out.println("Affichage de la carte normalement");
				XMLCityMapParser p = new XMLCityMapParser(path);
				CityMap cityMap = p.parse();
				w.graphicalView.setCityMap(cityMap);
				c.setCurrentstate(c.mapLoadedState);
				w.graphicalView.setRequest(null);
				t.ClearTour();
			}
			else 
			{
				w.setMessage("Vous pouvez charger une carte, charger une requête ou calculer la tournée.");
			}
		}
	
		@Override
		public void loadRequest(Controller c, Window w, Tour t) {
			String path = w.createDialogBoxToGetFilePath();
			if(path != null) 
			{
				try {
					XMLRequestParser p = new XMLRequestParser(path, w.graphicalView.getCityMap());
					Request request = p.parse();
					w.graphicalView.setRequest(request);
					c.setCurrentstate(c.requestLoadedState);
					w.setMessage("Requête chargée, vous pouvez charger une autre requête, charger une carte ou calculer la tournée.");
					t.ClearTour();
				} catch (InvalidRequestException e) {
					System.out.println(e.getMessage());
				}
			}
			else 
			{
				w.setMessage("Vous pouvez charger une carte, charger une requête ou calculer la tournée.");
			}
			
		}
		
		@Override
		public void computeTour(Controller c, Window w, Tour t) {
			Request request = w.graphicalView.getRequest();
			CityMap cityMap = w.graphicalView.getCityMap();
			
			//Modify the tour
			
			Pcc shortestPathComputer = new Pcc(cityMap , request);
			shortestPathComputer.computePcc();
			
			Intersection intersection = request.getStartingLocation();
			Intersection oldIntersecction;
			Iterator<Intersection> itPickUpTest = request.getPickUpLocationsIterator();
			while(itPickUpTest.hasNext())
			{
				oldIntersecction = intersection;
				intersection = itPickUpTest.next();
				
				List<Segment> localPaths = shortestPathComputer.getRoads(oldIntersecction,intersection);
				t.addAllSegmentsInPath(localPaths);
				
			}
			Iterator<Intersection> itDeliveryTest = request.getDeliveryLocationsIterator();
			while(itDeliveryTest.hasNext())
			{
				oldIntersecction = intersection;
				intersection = itDeliveryTest.next();
				
				List<Segment> localPaths2 = shortestPathComputer.getRoads(oldIntersecction,intersection);
				t.addAllSegmentsInPath(localPaths2);
			}
			w.setMessage("Votre tournée");
		}
}