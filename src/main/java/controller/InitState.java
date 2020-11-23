package controller;

import modele.CityMap;
import view.Window;
import xml.XMLCityMapParser;

public class InitState implements State {

	
	@Override
	public void loadMap(Controller c,Window w) {
		System.out.println("Affichage de la carte normalement");
		XMLCityMapParser p = new XMLCityMapParser("src/main/resources/largeMap.xml");
		CityMap cityMap = p.parse();

		w.graphicalView.setCityMap(cityMap);
	}
	
}
