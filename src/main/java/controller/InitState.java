package controller;

import modele.CityMap;
import view.Window;
import xml.XMLCityMapParser;

public class InitState implements State {

	@Override
	public void loadMap(Controller c,Window w) {
		String path = w.createDialogBoxToGetFilePath();
		if(path != null) 
		{

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
	public void loadRequest(Controller c,Window w) {
		
		w.setMessage("Avant de charger des requêtes, commencez par charger une carte");
		/*
		String path = w.createDialogBoxToGetFilePath();
		if(path != null) 
		{
			System.out.println("Affichage de la requête normalement");
			XMLRequestParser p = new XMLRequestParser(path);
			Request request = p.parse();
			w.graphicalView.setRequest(request);
		}
		else 
		{
			System.out.println("Echec de l'obtention du chemin avec la boite de dialogue");
		}*/
		
	}
	
}
