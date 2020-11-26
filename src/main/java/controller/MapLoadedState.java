package controller;

import modele.CityMap;
import modele.Request;
import modele.Tour;
import view.Window;
import xml.InvalidRequestException;
import xml.XMLCityMapParser;
import xml.XMLRequestParser;

public class MapLoadedState implements State {

	
		@Override
		public void loadMap(Controller c, Window w, Tour t) {
			
			String path = w.createDialogBoxToGetFilePath();
			if(path != null) 
			{
				System.out.println("Affichage de la carte");
				XMLCityMapParser p = new XMLCityMapParser(path);
				CityMap cityMap = p.parse();
				w.graphicalView.setCityMap(cityMap);
				c.setCurrentstate(c.mapLoadedState);
				w.setMessage ("La carte a été chargée avec succès. Veuillez charger des requêtes.");
			}
			else 
			{
				w.setMessage("Veuillez charger une carte ou une requête au format XML.");
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
					w.setMessage("Requête chargée, vous pouvez calculer la tournée");
				} catch (InvalidRequestException e) {
					w.setMessage(e.getMessage());
				}
			}
			else 
			{
				System.out.println("Echec de l'obtention du chemin avec la boite de dialogue");
				w.setMessage("Veuillez charger une requête au format XML.");
			}
			
		}
		
		@Override
		public void computeTour(Controller c,Window w, Tour t) {
			w.setMessage("Avant de calculer la tournée, chargez une requête");
		}
}
