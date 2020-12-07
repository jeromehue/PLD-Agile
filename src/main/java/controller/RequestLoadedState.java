package controller;

import javax.swing.JButton;

import algo.Pcc;
import modele.CityMap;
import modele.Request;
import modele.Tour;
import modele.Way;
import view.Window;
import xml.InvalidRequestException;
import xml.XMLCityMapParser;
import xml.XMLRequestParser;

public class RequestLoadedState implements State {

	
		@Override
		public void loadMap(Controller c, Window w, Tour t) {
			
			String path = w.createDialogBoxToGetFilePath();
			w.getGraphicalView().setHighlightedWay(null);
			if(path != null) 
			{
				XMLCityMapParser p = new XMLCityMapParser(path);
				CityMap cityMap = p.parse();
				w.getGraphicalView().setCityMap(cityMap);
				c.setCurrentstate(c.mapLoadedState);
				w.getGraphicalView().setRequest(null);
				t.ClearTour();
			}
			else 
			{
				w.setMessage("You can load a new map, load a requests file or compute the tour.");
			}
		}
	
		@Override
		public void loadRequest(Controller c, Window w, Tour t) {
			String path = w.createDialogBoxToGetFilePath();
			w.getGraphicalView().setHighlightedWay(null);
			if(path != null) 
			{
				try {
					XMLRequestParser p = new XMLRequestParser(path, w.getGraphicalView().getCityMap());
					Request request = p.parse();
					w.getGraphicalView().setRequest(request);
					c.setCurrentstate(c.requestLoadedState);
					w.setMessage("The requests were successfully loaded. You may now compute the tour.");
					t.ClearTour();
				} catch (InvalidRequestException e) {
					System.out.println(e.getMessage());
				}
			}
			else 
			{
				w.setMessage("Please load a XML file.");
			}
			
		}
		
		@Override
		public void computeTour(Controller c, Window w, Tour t) {
			//Modify the tour
			Pcc shortestPathComputer = new Pcc(w.getGraphicalView().getCityMap() ,  w.getGraphicalView().getRequest() );
			shortestPathComputer.computePcc();
			
			Tour t2 = shortestPathComputer.computeGooodTSPTour();
			t.setTour(t2);
			t.notifyObservers();
			
			w.getGraphicalView().setHighlightedWay(null);
			w.setMessage("Your tour");
		}
		
		@Override
		public void clickOnStep(Controller c, Window w, Way wa, JButton button, Tour t){	
			w.getTextualView().clearAllTextArea();
			button.setContentAreaFilled(true);
			w.getGraphicalView().setHighlightedWay(wa);
		}
		
		@Override
		public void modifyTour(Controller c, Window w) {
			w.changeOptionalsButtonsVisibility();
			if (w.isOptionalsButtonsVisible()) {
				c.setCurrentstate(c.tourModificationState);
			} else {
				c.setCurrentstate(c.requestLoadedState);
			}
		}
}
