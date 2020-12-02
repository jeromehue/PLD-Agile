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
			w.setMessage ("The map was successfully loaded. You may now load requests.");
		}
		else 
		{
			w.setMessage("Please load a XML file.");
		}
	}
	
	@Override
	public void loadRequest(Controller c,Window w, Tour t) {
		w.setMessage("Before trying to load a requests file, please load a map.");
	}
	
	@Override
	public void computeTour(Controller c,Window w, Tour t) {
		w.setMessage("Before trying to compute a tour, please load a map and a requests file.");
	}
	
}
