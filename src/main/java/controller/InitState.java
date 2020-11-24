package controller;

import modele.CityMap;
import view.Window;
import xml.XMLCityMapParser;

public class InitState implements State {

	
	@Override
	public void loadMap(Controller c,Window w, String path) {
		System.out.println("Affichage de la carte normalement");
		XMLCityMapParser p = new XMLCityMapParser(path);
		CityMap cityMap = p.parse();
		w.graphicalView.setCityMap(cityMap);
	}
	
}
