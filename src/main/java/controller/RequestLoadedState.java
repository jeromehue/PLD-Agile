package controller;

import java.util.List;

import javax.swing.JButton;

import algo.Pcc;
import modele.CityMap;
import modele.Point;
import modele.Request;
import modele.Segment;
import modele.Tour;
import modele.Way;
import view.GraphicalView;
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
		public void clickOnStep(Controller c, Window w, ListOfCommands l, Way wa, JButton button, Tour t){	
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
		
		@Override
		public void mouseMoved(Controller c, Window w, Point p) {
			GraphicalView graphicalView = w.getGraphicalView();
			if( graphicalView.getCityMap() != null ) {
				List<Segment> allsegments = graphicalView.getCityMap().getSegments();
				float mindist= (float) 0.5;
				Segment sclosest = null;
				for(Segment s: allsegments) {
					int x1 = s.getOrigin().getCoordinates().getX();
					int y1 = s.getOrigin().getCoordinates().getY();
					int x2 = s.getDestination().getCoordinates().getX();
					int y2 = s.getDestination().getCoordinates().getY();
					float distance = (float) 1.1;
					if ( p.inBox(x1, y1, x2, y2) ) {
						distance = p.distBetweenPointAndLine(x1,y1,x2,y2);
					}
					if(distance < mindist ) {
						mindist = distance;
						//System.out.println("distance : " +distance);
						sclosest = s;
					}
				}
				if (sclosest != null) {
					graphicalView.highlight(sclosest);
					w.setMessage(sclosest.getName());
				}
			}
		}
}
