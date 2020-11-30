package modele;

import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Iterator;
import java.util.List;

public class Way {
	private List<Segment> segmentList;
	private LocalTime startingTime;
	private LocalTime departureTime;
	private LocalTime arrivalTime;
	private Intersection departure;
	private Intersection arrival;
	
	
	public Way(List<Segment> segmentList, LocalTime startingTime, LocalTime departureTime, LocalTime arrivalTime, Intersection departure, Intersection arrival ) {
		this.segmentList = segmentList;
		this.startingTime = startingTime;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.departure = departure;
		this.arrival = arrival;
	}
	

	public Way(List<Segment> segmentList, Intersection departure, Intersection arrival ) {
		this.segmentList = segmentList;
		this.startingTime = LocalTime.now();
		this.departureTime = LocalTime.now();
		this.arrivalTime = LocalTime.now();
		this.departure = departure;
		this.arrival = arrival;
	}
	
	public Iterator<Segment> getSegmentListIterator(){
		return segmentList.iterator();
	}
	
	public List<Segment> getSegmentList() {
		return segmentList;
	}




	public LocalTime getStartingTime() {
		return startingTime;
	}




	public LocalTime getDepartureTime() {
		return departureTime;
	}




	public LocalTime getArrivalTime() {
		return arrivalTime;
	}




	public Intersection getDeparture() {
		return departure;
	}




	public Intersection getArrival() {
		return arrival;
	}




	public Integer getWayDuration() {
		return arrivalTime.getHour()*3600 + arrivalTime.getMinute()*60 + arrivalTime.getSecond() 
			- (departureTime.getHour()*3600 + departureTime.getMinute()*60 + departureTime.getSecond());
	}
	
	public Integer getStayingDurationDeparture() {
		return startingTime.getHour()*3600 + startingTime.getMinute()*60 + startingTime.getSecond() 
		- (departureTime.getHour()*3600 + departureTime.getMinute()*60 + departureTime.getSecond());
	}
	
	
	
}
