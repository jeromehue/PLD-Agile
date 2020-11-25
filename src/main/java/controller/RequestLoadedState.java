package controller;

import modele.CityMap;
import modele.Request;
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
					w.setMessage("Requests loaded successfully.");
				} catch (InvalidRequestException e) {
					w.setMessage(e.getMessage());
				}
			}
			else 
			{
				System.out.println("Echec de l'obtention du chemin avec la boite de dialogue");
				w.setMessage("Echec du chargement des requêtes lors de l'obtention du chemin avec la boite de dialogue.");
			}
			
		}
}