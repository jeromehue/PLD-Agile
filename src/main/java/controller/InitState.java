package controller;

import modele.CityMap;
import modele.Tour;
import view.Window;
import xml.XMLCityMapParser;

public class InitState implements State {

	@Override
	public void loadMap(Controller c,Window w, Tour t) {
		String path = w.createDialogBoxToGetFilePath();
		if(path != null) 
		{
			XMLCityMapParser p = new XMLCityMapParser(path);
			CityMap cityMap = p.parse();
			w.graphicalView.setCityMap(cityMap);
			c.setCurrentstate(c.mapLoadedState);
			w.setMessage ("La carte a été chargée avec succès. Vous pouvez charger une requête.");
		}
		else 
		{
			w.setMessage("Veuillez charger une carte au format XML.");
		}
	}
	
	@Override
	public void loadRequest(Controller c,Window w, Tour t) {
		w.setMessage("Avant de charger une requête, chargez une carte");
	}
	
	@Override
	public void computeTour(Controller c,Window w, Tour t) {
		w.setMessage("Avant de calculer la tournée, chargez une carte puis une requête");
	}
	
}
