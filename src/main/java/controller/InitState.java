package controller;

import modele.CityMap;
import modele.Request;
import view.Window;
import xml.XMLCityMapParser;
import xml.XMLRequestParser;

public class InitState implements State {

	
	@Override
	public void loadMap(Controller c,Window w, String path) {
		System.out.println("Affichage de la carte normalement");
		XMLCityMapParser p = new XMLCityMapParser(path);
		CityMap cityMap = p.parse();
		w.graphicalView.setCityMap(cityMap);
	}
	
	@Override
	public void loadRequest(Controller c,Window w, String path) {
		System.out.println("Affichage de la carte normalement");
		XMLRequestParser p = new XMLRequestParser(path);
		Request request = p.parse();
		w.graphicalView.setRequest(request);
	}
	
}
