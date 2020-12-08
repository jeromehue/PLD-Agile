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

public class AddRequestState2 implements State {
	private static final Logger logger = LoggerFactory.getLogger(AddRequestState2.class);
	
	private Long pickUpId;
	private int pickUpDuration;
	
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
				w.setMessage("Select the delivery point");
			}
		}
	}
	
	public void leftClick(Point p, Controller c, Window w) {
		logger.info("Clicked on the map to delivery intersection");
		logger.info("Intersection : {}", w.getGraphicalView().getHighlightedIntersectionId());
		int deliveryDuration = w.displaySelectTimeDialog("Enter delivery duration : ");
		if( deliveryDuration < 0 ) {
			return ;
		}
		logger.info("delivery time : {}", deliveryDuration);
		
		CityMap cityMap = w.getGraphicalView().getCityMap();
		Request request = w.getGraphicalView().getRequest();
		Tour 	tour 	= w.getGraphicalView().getTour();
		Pcc shortestPathComputer = new Pcc(cityMap, request);
		
		Intersection pickup 	= cityMap.getIntersectionFromId(this.pickUpId);
		Intersection delivery 	= cityMap.getIntersectionFromId(w.getGraphicalView().getHighlightedIntersectionId());
		Integer pickUpDuration 	= this.pickUpDuration;
		
		logger.info("pickup : {} ", pickup.getId());
		logger.info("delivery : {}", delivery.getId());
		
		//Tour tour, Intersection pickup, Intersection delivery, Integer pickUpDuration,
		//Integer deliveryDuration, Integer pickupIndex, Integer deliveryIndex
		Tour newTour = shortestPathComputer.addRequest(tour, pickup, delivery, 
				pickUpDuration, deliveryDuration, 1, 2);
		tour.setTour(newTour);
		tour.notifyObservers();
		w.getGraphicalView().setHighlightedWay(null);
		c.setCurrentstate(c.tourModificationState);
		
		
	}
	
	protected void enterAction(Long pickUpId, Integer pickUpDuration) {
		this.pickUpId = pickUpId;
		this.pickUpDuration = pickUpDuration;
	}
}
