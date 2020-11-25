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
		public void loadMap(Controller c, Window w) {
			w.graphicalView.setRequest(null);
			String path = w.createDialogBoxToGetFilePath();
			if(path != null) 
			{
				System.out.println("Affichage de la carte normalement");
				XMLCityMapParser p = new XMLCityMapParser(path);
				CityMap cityMap = p.parse();
				
				w.graphicalView.setCityMap(cityMap);
				c.setCurrentstate(c.mapLoadedState);
			}
			else 
			{
				System.out.println("Echec de l'obtention du chemin avec la boite de dialogue");
			}
		}
	
		@Override
		public void loadRequest(Controller c, Window w) {
		
			String path = w.createDialogBoxToGetFilePath();
			if(path != null) 
			{
				try {
					XMLRequestParser p = new XMLRequestParser(path, w.graphicalView.getCityMap());
					Request request = p.parse();
					w.graphicalView.setRequest(request);
					c.setCurrentstate(c.requestLoadedState);
					w.setMessage("Requête chargée avecsuccès");
				} catch (InvalidRequestException e) {
					w.setMessage(e.getMessage());
				}
			}
			else 
			{
				System.out.println("Echec de l'obtention du chemin avec la boite de dialogue");
				w.setMessage("La requête n'a pas pu être chargé");
			}
			
		}
		
		@Override
		public void computeTour(Controller c, Window w) {
			Request request = w.graphicalView.getRequest();
			CityMap cityMap = w.graphicalView.getCityMap();
			
			//create a Tour 
			Tour tour = new Tour(request);
			Pcc shortestPathComputer = new Pcc(cityMap , request);
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
				
			w.graphicalView.setTour(tour);
		}
}