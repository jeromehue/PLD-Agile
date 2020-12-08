package controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modele.Intersection;
import modele.Point;
import view.GraphicalView;
import view.Window;

/**
 * @author H4414
 *
 */

public class AddRequestState implements State {
	private static final Logger logger = LoggerFactory.getLogger(AddRequestState.class);
	
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
			List<Intersection> intersections = graphicalView.getCityMap().getIntersections();
			float mindist= (float) 100;
			int floor = 1;
			Intersection iclosest = null;
			for(Intersection i: intersections) {
				int distance = p.distanceWithCoordinates(i.getCoordinates().getX(), i.getCoordinates().getY());
				if(distance < mindist ) {
					mindist = distance;
					//System.out.println("distance : " +distance);
					iclosest = i;
					if (mindist < floor) 
						break;
				}
			}
			if (iclosest != null) {
				graphicalView.setHighlightInter(iclosest);
				w.setMessage("Select the pickup point");
			}
		}
	}
	
	public void leftClick(Point p, Controller c, Window w) {
		logger.info("Clicked on the map to add intersection");
		logger.info("Intersection : {}", w.getGraphicalView().getHighlightedIntersectionId());
		int pickupTime = w.displaySelectTimeDialog("Enter pickup duration : ");
		if( pickupTime < 0 ) {
			return ;
		}
		logger.info("pick-up time : {}", pickupTime);
		
		c.addRequestState2.enterAction( w.getGraphicalView().getHighlightedIntersectionId() , pickupTime);
		c.setCurrentstate(c.addRequestState2);
	}
	
	
}
