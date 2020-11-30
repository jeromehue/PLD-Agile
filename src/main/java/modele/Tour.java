package modele;

import java.util.List;

import observer.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Tour extends Observable{

	
	private Intersection startingIntersection;
	private Request request;
	private List<Chemin> waysList;
	
	public Tour(Intersection start, Request request, List<Segment> path) {
		this.startingIntersection = start;
		this.request = request;
		this.path = path;
	}
	
	public Tour(Request request) {
		this.request = request;
		this.path = new ArrayList<Segment>();
	}
	
	public Tour() {
		this.startingIntersection = null;
		this.path = new ArrayList<Segment>();
		this.request = null;
	}
	
	public void ClearTour() {
		path.clear();
		notifyObservers();
	}
	
	public void addSegmentInPath(Segment s) {
		path.add(s);
		notifyObservers();
	}
	
	public void addAllSegmentsInPath(List<Segment> listS) {
		path.addAll(listS);
		notifyObservers();
	}
	
	public void setStartingIntersection(Intersection startingIntersection) {
		this.startingIntersection = startingIntersection;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public void setPath(List<Segment> path) {
		this.path = path;
	}

	/**
	 * @return an iterator on all Segments in the tour
	 */
	public Iterator<Segment> getSegementsIterator(){
		return path.iterator();
	}

	public Intersection getStartingIntersection() {
		return startingIntersection;
	}

	public Request getRequest() {
		return request;
	}

	public List<Segment> getPath() {
		return path;
	}

}
