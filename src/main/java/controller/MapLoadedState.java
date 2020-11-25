package controller;

import modele.CityMap;
import modele.Request;
import view.Window;
import xml.XMLCityMapParser;
import xml.XMLRequestParser;

public class MapLoadedState implements State {

	
		@Override
		public void loadMap(Controller c, Window w) {
			w.graphicalView.setRequest(null);
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
				w.setMessage("Echec du chargement de la carte lors de l'obtention du chemin avec la boite de dialogue");
			}
		}
	
		@Override
		public void loadRequest(Controller c, Window w) {
		
			String path = w.createDialogBoxToGetFilePath();
			if(path != null) 
			{
				System.out.println("Affichage de la requête");
				XMLRequestParser p = new XMLRequestParser(path);
				Request request = p.parse();
				w.graphicalView.setRequest(request);
				// Si pas d'erreur dans le fichier
				if (request != null) {
					c.setCurrentstate(c.requestLoadedState);
				}
				w.setMessage("Requêtes chargées avec succès");
				
			}
			else 
			{
				w.setMessage("Echec du chargement des requêtes lors de l'obtention du chemin avec la boite de dialogue");
			}
			
		}
}
