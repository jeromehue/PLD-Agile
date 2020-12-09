package controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algo.Pcc;
import modele.CityMap;
import modele.Intersection;
import modele.Point;
import modele.Request;
import modele.Tour;
import view.GraphicalView;
import view.Window;

/**
 * State reached after a pickup point is selected in AddRequestState.
 * @author H4414
 */

public class AddRequestState2 implements State {
	private static final Logger logger = LoggerFactory.getLogger(AddRequestState2.class);
	
	private Long pickUpId;
	private int pickUpDuration;
	private int pickUpIndex;
	
	@Override
	public void modifyTour(Controller c, Window w) {
		w.changeOptionalsButtonsVisibility();
		if (w.isOptionalsButtonsVisible()) {
			c.setCurrentstate(c.tourModificationState);
		} else {
			w.setMessage("");
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
				w.setMessage("Select the delivery point");
			}
		}
	}
	
	
	@Override
	public void leftClick(Point p, ListOfCommands l, Controller c, Window w) {
		logger.info("Clicked on the map to delivery intersection");
		logger.info("Intersection : {}", w.getGraphicalView().getHighlightedIntersectionId());
		int deliveryDuration = w.displaySelectTimeDialog("Enter delivery duration : ");
		while ( deliveryDuration < 0 ) {
			if( deliveryDuration == -1 ) {
				return ;
			}
			deliveryDuration = w.displaySelectTimeDialog("Wrong input ! Enter delivery duration : ");
		}
		deliveryDuration *= 60;
		int nbPoints = w.getGraphicalView().getTour().getWaysList().size();
		int deliveryIndex = w.displaySelectTimeDialog("Enter delivery index : ");
		while ( deliveryIndex <= 0 || deliveryIndex >=nbPoints +1 ) {
			if( deliveryIndex == -1 ) {
				return ;
			}
			deliveryIndex = w.displaySelectTimeDialog("Bad index ! Enter delivery index : ");
		}
		if( deliveryIndex == -1 ) {
			return ;
		}
		
		logger.info("delivery time : {}", deliveryDuration);
		
		CityMap cityMap = w.getGraphicalView().getCityMap();
		Request request = w.getGraphicalView().getRequest();
		Tour 	tour 	= w.getGraphicalView().getTour();
		Pcc shortestPathComputer = new Pcc(cityMap, request);
		
		Intersection pickup 	= cityMap.getIntersectionFromId(this.pickUpId);
		Intersection delivery 	= cityMap.getIntersectionFromId(w.getGraphicalView().getHighlightedIntersectionId());
		int pickUpDuration 	= this.pickUpDuration;
		int  pickUpIndex 	= this.pickUpIndex;
		
		logger.info("pickup : {} ", pickup.getId());
		logger.info("delivery : {}", delivery.getId());
		
		l.add(new AddRequestCommand(w.getGraphicalView(), shortestPathComputer, tour, pickup, delivery, 
				pickUpDuration, deliveryDuration, pickUpIndex, deliveryIndex));
		
		w.getGraphicalView().setHighlightedWay(null);
		w.setMessage("");
		c.setCurrentstate(c.tourModificationState);

	}
	
	protected void enterAction(Long pickUpId, Integer pickUpDuration, Integer pickUpIndex) {
		this.pickUpId = pickUpId;
		this.pickUpDuration = pickUpDuration;
		this.pickUpIndex = pickUpIndex;
	}
}
