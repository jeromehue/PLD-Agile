package modele;

import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;

/**
 * This class contains the informations relative to a Way. A Way contains all
 * informations corresponding to the journey between two steps of the tour, as
 * computed by the TSP algorithm.
 * 
 * @author H4414
 *
 */
public class Way {
	/**
	 * The way linking the two steps of the tour. This ordered list of Segments is
	 * especially useful to display the way between two steps on the map.
	 */
	private List<Segment> segmentList;

	/**
	 * The time at which the delivery man is supposed to arrive at the departure
	 * point of this way.
	 */
	private LocalTime startingTime;

	/**
	 * The time at which the delivery man has to leave the departure point of this
	 * way. It is possible to retrieve the time spent on the departure point by
	 * subtracting the startingTime to this departureTime.
	 */
	private LocalTime departureTime;

	/**
	 * The time at which the delivery man is supposed to arrive at the destination
	 * of this way. It also corresponds to the startingTime of the next Way of the
	 * tour.
	 */
	private LocalTime arrivalTime;

	/**
	 * The Intersection corresponding to the starting point of this way.
	 */
	private Intersection departurePoint;

	/**
	 * The Intersection corresponding to the ending point of this way.
	 */
	private Intersection arrivalPoint;

	/**
	 * Constructor used to initialize all fields of this Way. The Ways are always
	 * initialized by the <code>computeWaysList
	 * </code> method of Pcc.java.
	 * 
	 * @param segmentList   The ordered list of segments composing this way.
	 * @param startingTime  The time at which the delivery man is supposed to arrive
	 *                      at the departure point of this way.
	 * @param departureTime The time at which the delivery man is supposed to leave
	 *                      the departure point of this way.
	 * @param arrivalTime   The time at which the delivery man is supposed to arrive
	 *                      at the ending point of this way.
	 * @param departure     The Intersection corresponding to the starting point of
	 *                      this way.
	 * @param arrival       The Intersection corresponding to the ending point of
	 *                      this way.
	 */
	public Way(List<Segment> segmentList, LocalTime startingTime, LocalTime departureTime, LocalTime arrivalTime,
			Intersection departure, Intersection arrival) {
		this.segmentList = segmentList;
		this.startingTime = startingTime;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.departurePoint = departure;
		this.arrivalPoint = arrival;
	}

	/**
	 * Used to iterate on the list of segments, for example to display the way on
	 * the map.
	 * 
	 * @return An iterator on the list of Segments composing this way.
	 */
	public Iterator<Segment> getSegmentListIterator() {
		return segmentList.iterator();
	}

	/**
	 * Default getter.
	 * 
	 * @return The list of Segments composing this way.
	 */
	public List<Segment> getSegmentList() {
		return segmentList;
	}

	/**
	 * Default getter.
	 * 
	 * @return The time at which the delivery man is supposed to arrive at the
	 *         departure point of this way.
	 */
	public LocalTime getStartingTime() {
		return startingTime;
	}

	/**
	 * Default getter.
	 * 
	 * @return The time at which the delivery man is supposed to leave the departure
	 *         point of this way.
	 */
	public LocalTime getDepartureTime() {
		return departureTime;
	}

	/**
	 * Default getter.
	 * 
	 * @return The time at which the delivery man is supposed to arrive at the
	 *         ending point of this way.
	 */
	public LocalTime getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * Default getter.
	 * 
	 * @return The intersection corresponding to the starting point of this way.
	 */
	public Intersection getDeparture() {
		return departurePoint;
	}

	/**
	 * Default getter.
	 * 
	 * @return The intersection corresponding to the ending point of this way.
	 */
	public Intersection getArrival() {
		return arrivalPoint;
	}

	/**
	 * Used to retrieve the time needed for the delivery man to travel from the
	 * departure to the arrival.
	 * 
	 * @return The time needed to travel from the departure of this way to its
	 *         arrival (in seconds).
	 */
	public Integer getWayDuration() {
		return ((arrivalTime.getHour() - departureTime.getHour()) * 3600
				+ (arrivalTime.getMinute() - departureTime.getMinute()) * 60 + arrivalTime.getSecond()
				- departureTime.getSecond());
	}

	/**
	 * Used to retrieve the time needed for the delivery man to pick-up or deliver
	 * on the departure point of this way.
	 * 
	 * @return The time needed to pick-up or deliver on the departure point of this
	 *         way (in seconds).
	 */
	public Integer getStayingDurationForDeparturePoint() {
		return ((departureTime.getHour() - startingTime.getHour()) * 60
				+ (departureTime.getMinute() - startingTime.getMinute()));
	}
}
