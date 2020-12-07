package modele;

import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;

public class Way {
	private List<Segment> segmentList;
	private LocalTime startingTime;
	private LocalTime departureTime;
	private LocalTime arrivalTime;
	private Intersection departurePoint;
	private Intersection arrivalPoint;

	public Way(List<Segment> segmentList, LocalTime startingTime, LocalTime departureTime, LocalTime arrivalTime,
			Intersection departure, Intersection arrival) {
		this.segmentList = segmentList;
		this.startingTime = startingTime;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.departurePoint = departure;
		this.arrivalPoint = arrival;
	}

	/*
	 * public Way(List<Segment> segmentList, Intersection departure, Intersection
	 * arrival ) { this.segmentList = segmentList; this.startingTime =
	 * LocalTime.now(); this.departureTime = LocalTime.now(); this.arrivalTime =
	 * LocalTime.now(); this.departure = departure; this.arrival = arrival; }
	 */

	public Iterator<Segment> getSegmentListIterator() {
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
		return departurePoint;
	}

	public Intersection getArrival() {
		return arrivalPoint;
	}

	public Integer getWayDuration() {
		return ((arrivalTime.getHour() - departureTime.getHour()) * 3600
				+ (arrivalTime.getMinute() - departureTime.getMinute()) * 60 + arrivalTime.getSecond()
				- departureTime.getSecond());
	}

	public Integer getStayingDurationForDeparturePoint() {
		return ((departureTime.getHour() - startingTime.getHour()) * 60
				+ (departureTime.getMinute() - startingTime.getMinute()));
	}

}
