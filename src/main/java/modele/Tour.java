package modele;

import java.util.List;
import java.util.HashMap;


public class Tour {

	
	private Intersection startingIntersection;
	private Request request;
	private List<Segment> path;
	private HashMap<Long, Integer> arrivalTime;
	private HashMap<Long, Integer> departureTime;
	
	public Tour(Intersection start, List<Request> request, List<Segment> path, 
			HashMap<Long, Integer> arrivalTime, HashMap<Long, Integer> departureTime) {
		this.startingIntersection = start;
		this.request = request;
		this.path = path;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
	}

	public Intersection getStartingIntersection() {
		return startingIntersection;
	}

	public List<Request> getRequest() {
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
