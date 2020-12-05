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
		public void loadRequest(Controller c, Window w, Tour t) {
			String path = w.createDialogBoxToGetFilePath();
			if(path != null) 
			{
				try {
					XMLRequestParser p = new XMLRequestParser(path, w.graphicalView.getCityMap());
					Request request = p.parse();
					w.graphicalView.setRequest(request);
					c.setCurrentstate(c.requestLoadedState);
					w.setMessage("The requests were successfully loaded. You may now compute the tour.");
				} catch (InvalidRequestException e) {
					w.setMessage(e.getMessage());
				}
			}
			else 
			{
				w.setMessage("Please load a XML file.");
			}
			
		}
		
		@Override
		public void computeTour(Controller c,Window w, Tour t) {
			w.setMessage("Before trying to compute a tour, please load a requests file.");
		}
}
