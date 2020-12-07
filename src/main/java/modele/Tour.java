package modele;

import java.util.List;

import observer.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Tour extends Observable{

	
	private Intersection startingIntersection;
	private Request request;
	private List<Way> waysList;
	private HashMap<Long, Boolean> isPositionConsistent; //Id Intersection, isPositionConsistent (pickUp before Delivery)
	
	public Tour(Intersection start, Request request, List<Way> waysList) {
		this.startingIntersection = start;
		this.request = request;
		this.waysList = waysList;
		initPositionConsistent();
	}
	
	public Tour(Request request) {
		this.request = request;
		this.waysList = new ArrayList<Way>();
		initPositionConsistent();
	}
	
	public Tour() {
		this.startingIntersection = null;
		this.waysList = new ArrayList<Way>();
		this.request = null;	
	}
	
	public void initPositionConsistent() {
		isPositionConsistent = new HashMap<Long, Boolean>();
		for(int i = 0 ; i < request.getPickUpLocations().size(); ++i) {
			isPositionConsistent.put(request.getPickUpLocations().get(i).getId(), true);
		}
		for(int i = 0 ; i < request.getDeliveryLocations().size(); ++i) {
			isPositionConsistent.put(request.getDeliveryLocations().get(i).getId(), true);
		}
		isPositionConsistent.put(request.getStartingLocation().getId(), true);
	}
	
	public void setTour(Tour t2) {
		this.startingIntersection = t2.getStartingIntersection();
		this.request = t2.getRequest();
		this.waysList = t2.getWaysList();
		this.isPositionConsistent = t2.getIsPositionConsistent();
	}
	
	
	public void ClearTour() {
		waysList.clear();
		notifyObservers();
	}
	
	public void addWayInwaysList(Way s) {
		waysList.add(s);
	}
	
	public void addSegmentInPath(Way way) {
		waysList.add(way);
		notifyObservers();
	}
	
	public void addAllSegmentsInPath(List<Way> listSeg) {
		waysList.addAll(listSeg);
		notifyObservers();
	}
	
	public void setStartingIntersection(Intersection startingIntersection) {
		this.startingIntersection = startingIntersection;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public void setWaysList(List<Way> waysList) {
		this.waysList = waysList;
	}
	
	
	public Iterator<Way> getWaysListIterator() {
		return this.waysList.iterator();
	}


	public Intersection getStartingIntersection() {
		return startingIntersection;
	}

	public Request getRequest() {
		return request;
	}

	public List<Way> getWaysList() {
		return waysList;
	}

	
	public HashMap<Long, Boolean> getIsPositionConsistent(){
		return isPositionConsistent;
	}
	
	public void setIsPositionConsistent(HashMap<Long, Boolean> IsPositionConsistent) {
		this.isPositionConsistent = IsPositionConsistent;
	}
	
	public void updateIsPositionConsistent(Long idInterToCheck) {
		boolean isPositionConsistent=false;
		
		if(request.getStartingLocation().getId().equals(idInterToCheck)) {
			isPositionConsistent = true;
		} else if(request.isPickUp(idInterToCheck)) {
			isPositionConsistent = true;
		}else {
			int i=1;//Beginning is after start Point
			Intersection currentInter ;
		
			do {
				currentInter = waysList.get(i).getDeparture();
				i++;
				//If interToCheck is a delivery and associate pickup point is before
				if( idInterToCheck.equals(request.getDeliveryFromPickUp(currentInter.getId())) ) {
					isPositionConsistent = true;
				}
				/*else {
					System.out.println("[Tour.update] currentInter is null...");
				}*/
			} 
			while(!currentInter.getId().equals(idInterToCheck));
		}
		this.isPositionConsistent.put(idInterToCheck, isPositionConsistent);
	}
	
	public Boolean isPositionConsistent(Long idIntersection) {
		return this.isPositionConsistent.get(idIntersection);
	}

}
