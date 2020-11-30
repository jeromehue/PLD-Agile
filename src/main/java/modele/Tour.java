package modele;

import java.util.List;

import observer.Observable;

import java.util.ArrayList;
import java.util.Iterator;


public class Tour extends Observable{

	
	private Intersection startingIntersection;
	private Request request;
	private List<Way> waysList;
	
	public Tour(Intersection start, Request request, List<Way> waysList) {
		this.startingIntersection = start;
		this.request = request;
		this.waysList = waysList;
	}
	
	public Tour(Request request) {
		this.request = request;
		this.waysList = new ArrayList<Way>();
	}
	
	public Tour() {
		this.startingIntersection = null;
		this.waysList = new ArrayList<Way>();
		this.request = null;
	}
	
	public void ClearTour() {
		waysList.clear();
		notifyObservers();
	}
	
	public void addNewWayInwaysList(List<Segment> s, Intersection start, Intersection arrival) {
		Way way = new Way(s, start, arrival);
		waysList.add(way);
		notifyObservers();
	}
	
	public void addWayInwaysList(Way s) {
		waysList.add(s);
		notifyObservers();
	}
	
	public void addAllWaysInwaysList(List<Way> listS) {
		waysList.addAll(listS);
		notifyObservers();
	}
	
	public void setStartingIntersection(Intersection startingIntersection) {
		this.startingIntersection = startingIntersection;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public void setwaysList(List<Way> waysList) {
		this.waysList = waysList;
	}

	/**
	 * @return an iterator on all Ways in the tour
	 */
	public Iterator<Way> getwaysListIterrator(){
		return waysList.iterator();
	}

	public Intersection getStartingIntersection() {
		return startingIntersection;
	}

	public Request getRequest() {
		return request;
	}

	public List<Way> getwaysList() {
		return waysList;
	}

}
