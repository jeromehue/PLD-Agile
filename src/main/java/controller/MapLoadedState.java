package controller;

import java.util.List;

import modele.CityMap;
import modele.Point;
import modele.Request;
import modele.Segment;
import modele.Tour;
import view.GraphicalView;
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
				w.getGraphicalView().setCityMap(cityMap);
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
					XMLRequestParser p = new XMLRequestParser(path, w.getGraphicalView().getCityMap());
					Request request = p.parse();
					w.getGraphicalView().setRequest(request);
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
		
		@Override
		public void modifyTour(Controller c, Window w) {
			w.setMessage("Before trying to modify a tour, please load a map and a requests file, then compute the Tour");
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
