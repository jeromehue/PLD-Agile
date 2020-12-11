package modele;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class stores all the informations given by the user concerning the tour
 * before the tour is computed to be optimal.
 * 
 * @author H4414
 * 
 */
public class Request {
	/**
	 * This intersection is the starting and ending point of the tour.
	 */
	private Intersection startingLocation;

	/**
	 * The time at which the tour is planned to begin.
	 */
	private LocalTime startingTime;

	/**
	 * List of all intersections corresponding to pick-up locations.
	 */
	private ArrayList<Intersection> pickUpLocations;

	/**
	 * List of all intersections corresponding to delivery locations.
	 */
	private ArrayList<Intersection> deliveryLocations;

	/**
	 * HashMap linking an Intersection (pick-up or delivery) to its duration (time
	 * spent on the location in minutes).
	 */
	private HashMap<Long, Integer> durations;

	/**
	 * HashMap linking each intersection corresponding to a pick-up to its delivery.
	 */
	private HashMap<Long, Long> deliveryFromPickUp;

	/**
	 * Empty constructor.
	 */
	public Request() {
	}

	/**
	 * Constructor to initialize all fields of this Request.
	 * 
	 * @param startingLocation  Starting and ending point of the tour.
	 * @param startingTime      The time at which the tour is planned to begin.
	 * @param pickUpDurations   All durations corresponding to pick-ups.
	 * @param deliveryDurations All durations corresponding to deliveries.
	 * @param pickUpLocations   All intersections corresponding to pick-ups.
	 * @param deliveryLocations All intersections corresponding to deliveries.
	 */
	public Request(Intersection startingLocation, LocalTime startingTime, ArrayList<Integer> pickUpDurations,
			ArrayList<Integer> deliveryDurations, ArrayList<Intersection> pickUpLocations,
			ArrayList<Intersection> deliveryLocations) {
		this.startingLocation = startingLocation;
		this.startingTime = startingTime;
		this.pickUpLocations = pickUpLocations;
		this.deliveryLocations = deliveryLocations;

		ArrayList<Integer> allDurations = new ArrayList<Integer>();
		allDurations.addAll(pickUpDurations);
		allDurations.addAll(deliveryDurations);
		ArrayList<Intersection> allLocations = new ArrayList<Intersection>();
		allLocations.addAll(0, pickUpLocations);
		allLocations.addAll(0, deliveryLocations);
		durations = new HashMap<Long, Integer>();
		for (int i = 0; i < allDurations.size(); i++) {
			durations.put(allLocations.get(i).getId(), allDurations.get(i));
		}
		this.deliveryFromPickUp = new HashMap<Long, Long>();

		for (int i = 0; i < pickUpLocations.size(); ++i) {
			deliveryFromPickUp.put(pickUpLocations.get(i).getId(), deliveryLocations.get(i).getId());
		}
	}

	/**
	 * Default copy constructor.
	 * 
	 * @param request The request to copy into this.
	 */
	public Request(Request request) {
		this.startingLocation = new Intersection(request.getStartingLocation());
		this.startingTime = request.getStartingTime();
		this.pickUpLocations = new ArrayList<>();
		for (Intersection i : request.getPickUpLocations()) {
			this.pickUpLocations.add(i);
		}
		this.deliveryLocations = new ArrayList<Intersection>();
		for (Intersection i : request.getDeliveryLocations()) {
			this.deliveryLocations.add(i);
		}
		this.durations = new HashMap<Long, Integer>(request.getDurations());
		this.deliveryFromPickUp = request.getDeliveryFromPickUp();
	}

	/**
	 * Default getter.
	 * 
	 * @return This Request's HashMap linking each intersection corresponding to a
	 *         pick-up to its delivery.
	 */
	public HashMap<Long, Long> getDeliveryFromPickUp() {
		return this.deliveryFromPickUp;
	}

	/**
	 * Default getter.
	 * 
	 * @return The starting and ending location of the tour.
	 */
	public Intersection getStartingLocation() {
		return startingLocation;
	}

	/**
	 * Default getter.
	 * 
	 * @return The starting time of the tour.
	 */
	public LocalTime getStartingTime() {
		return startingTime;
	}

	/**
	 * Default getter.
	 * 
	 * @return A list containing all Intersections of the tour which correspond to
	 *         pick-up points. It may not be ordered as the tour's optimal order.
	 */
	public ArrayList<Intersection> getPickUpLocations() {
		return pickUpLocations;
	}

	/**
	 * Default getter.
	 * 
	 * @return A list containing all Intersections of the tour which correspond to
	 *         delivery points. It may not be ordered as the tour's optimal order.
	 */
	public ArrayList<Intersection> getDeliveryLocations() {
		return deliveryLocations;
	}

	/**
	 * Default getter.
	 * 
	 * @return A HashMap linking every Intersection of the tour to its corresponding
	 *         duration.
	 */
	public HashMap<Long, Integer> getDurations() {
		return durations;
	}

	/**
	 * @return An iterator on all pick up locations in the request.
	 */
	public Iterator<Intersection> getPickUpLocationsIterator() {
		return pickUpLocations.iterator();
	}

	/**
	 * @return An iterator on all delivery locations in the request.
	 */
	public Iterator<Intersection> getDeliveryLocationsIterator() {
		return deliveryLocations.iterator();
	}

	/**
	 * Used to retrieve the intersection's id of the delivery corresponding to a
	 * given pick-up.
	 * 
	 * @param id A pick-up's intersection's id.
	 * @return The id of the corresponding delivery's intersection.
	 */
	public Long getDeliveryFromPickUp(Long id) {
		return deliveryFromPickUp.get(id);
	}

	public Intersection getDeliveryIntersectionFromPickUp(Long id) {
		Long deliveryId = deliveryFromPickUp.get(id);
		for (Intersection i : deliveryLocations) {
			if (deliveryId == i.getId()) {
				return i;
			}
		}
		return null;
	}

	/**
	 * Used to check whether a given Intersection is pick-up.
	 * 
	 * @param id The id of the Intersection to check.
	 * @return True if the intersection is a pick-up point, false if it is a
	 *         delivery point or the starting point.
	 */
	public Boolean isPickUp(Long id) {
		return (deliveryFromPickUp.get(id) != null);
	}

	/**
	 * Used to check whether an Intersection has a corresponding delivery point. It
	 * always returns false for delivery points and for the starting point.
	 * 
	 * @param id The intersection's id to check.
	 * @return True if the intersection's a pick-up point and if it has a
	 *         corresponding delivery, else false.
	 */
	public Boolean hasDelivery(Long id) {
		return (deliveryFromPickUp.get(id) != null && deliveryFromPickUp.get(id) != -1);
	}

	public Boolean hasPickup(Long id) {
		for (Intersection i : pickUpLocations) {
			if (this.getDeliveryFromPickUp(i.getId()) == id) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return A textual representation of the complete graph.
	 */
	@Override
	public String toString() {
		return "Request [startingLocation=" + startingLocation + ", startingTime=" + startingTime + ", Durations="
				+ durations + ", pickUpLocations=" + pickUpLocations + ", deliveryLocations=" + deliveryLocations + "]";
	}

	/**
	 * Used to retrieve the duration of a specific step of the tour.
	 * 
	 * @param id The id corresponding to the intersection whose duration we want to
	 *           know.
	 * @return The corresponding duration.
	 */
	public Integer getDurationPickUpDelivery(Long id) {
		return durations.get(id);
	}

	/**
	 * Used to add two steps to the tour (a pick-up and its corresponding delivery).
	 * 
	 * @param pickUp           The intersection corresponding to the pick-up point
	 *                         to add.
	 * @param delivery         The intersection corresponding to the delivery point
	 *                         to add.
	 * @param pickUpDuration   The time needed for the pick-up (in minutes).
	 * @param deliveryDuration The time needed for the delivery (in minutes).
	 */
	public void addRequest(Intersection pickUp, Intersection delivery, Integer pickUpDuration,
			Integer deliveryDuration) {
		this.deliveryFromPickUp.put(pickUp.getId(), delivery.getId());
		this.pickUpLocations.add(pickUp);
		this.deliveryLocations.add(delivery);
		this.durations.put(pickUp.getId(), pickUpDuration);
		this.durations.put(delivery.getId(), deliveryDuration);
	}
}