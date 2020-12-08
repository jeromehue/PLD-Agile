package controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modele.Intersection;
import modele.Point;
import modele.Segment;
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
					iclosest = i;
					if (mindist < floor) 
						break;
				}
			}
			if (iclosest != null) {
				graphicalView.setHighlightInter(iclosest);
				w.setMessage("Select the pickup point");
			}
			
			List<Segment> allsegments = graphicalView.getCityMap().getSegments();
			float mindist2 = (float) 0.5;
			Segment sclosest = null;
			for (Segment s : allsegments) {
				int x1 = s.getOrigin().getCoordinates().getX();
				int y1 = s.getOrigin().getCoordinates().getY();
				int x2 = s.getDestination().getCoordinates().getX();
				int y2 = s.getDestination().getCoordinates().getY();
				float distance = (float) 1.1;
				if (p.inBox(x1, y1, x2, y2)) {
					distance = p.distBetweenPointAndLine(x1, y1, x2, y2);
				}
				if (distance < mindist2) {
					mindist2 = distance;
					sclosest = s;
				}
			}
			if (sclosest != null && iclosest!= null) {
				w.setStreet("Adress is " + iclosest.getId() + ", " + sclosest.getName());
			}
		}
	}
	
	@Override
	public void leftClick(Point p, Controller c, Window w) {
		logger.info("Clicked on the map to add intersection");
		logger.info("Intersection : {}", w.getGraphicalView().getHighlightedIntersectionId());
		int pickupTime = w.displaySelectTimeDialog("Enter pickup duration : ");
		if( pickupTime < 0 ) {		// Cancel
			return ;
		}
		int nbPoints = w.getGraphicalView().getTour().getWaysList().size();
		int pickupIndex = w.displaySelectTimeDialog("Enter delivery index : ");
		while ( pickupIndex <= 0 || pickupIndex >=nbPoints +1 ) {
			pickupIndex = w.displaySelectTimeDialog("Bad index ! Enter delivery index : ");
			if( pickupIndex == -1 ) {
				return ;
			}
		}
		if( pickupIndex == -1 ) {
			return ;
		}
		logger.info("pick-up time : {}", pickupTime);
		c.addRequestState2.enterAction( w.getGraphicalView().getHighlightedIntersectionId() , pickupTime, pickupIndex);
		c.setCurrentstate(c.addRequestState2);
	}
	
	
}
