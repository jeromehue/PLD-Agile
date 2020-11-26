package modele;

import java.util.List;

import observer.Observable;

import java.util.HashMap;
import java.util.Iterator;


public class Tour{

	
	private Intersection startingIntersection;
	private Request request;
	private List<Segment> path;
	private HashMap<Long, Integer> arrivalTime;
	private HashMap<Long, Integer> departureTime;
	
	public Tour(Intersection start, Request request, List<Segment> path, 
			HashMap<Long, Integer> arrivalTime, HashMap<Long, Integer> departureTime) {
		this.startingIntersection = start;
		this.request = request;
		this.path = path;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
	}
	
	public Tour(Request request) {
		this.request = request;
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

	public void setArrivalTime(HashMap<Long, Integer> arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public void setDepartureTime(HashMap<Long, Integer> departureTime) {
		this.departureTime = departureTime;
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

	public HashMap<Long, Integer> getArrivalTime() {
		return arrivalTime;
	}

	public HashMap<Long, Integer> getDepartureTime() {
		return departureTime;
	}
}
