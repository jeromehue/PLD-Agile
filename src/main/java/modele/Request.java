package modele;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Request {

	private Intersection startingLocation;
	private LocalTime startingTime;
	// List of pickup/delivery points with their duration
	private ArrayList<Intersection> pickUpLocations;
	private ArrayList<Intersection> deliveryLocations;
	private HashMap<Long, Integer> durations;
	// id of pickup, id of corresponding delivery
	private HashMap<Long, Long> deliveryFromPickUp;

	/**
	 * Default constructor
	 */
	public Request() {
	}

	/**
	 * @param startingLocation
	 * @param startingTime
	 * @param pickUpDurations
	 * @param deliveryDurations
	 * @param pickUpLocations
	 * @param deliveryLocations
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
	
	// Ah c'est le constructeur par copie que j'ai ajouté
	public Request(Request request) {
		this.startingLocation = new Intersection(request.getStartingLocation());
		this.startingTime = request.getStartingTime();
		this.pickUpLocations = new ArrayList<Intersection>(request.getPickUpLocations());
		this.deliveryLocations = new ArrayList<Intersection>(request.getDeliveryLocations());
		this.durations = new HashMap<Long, Integer>(request.getDurations());
		//this.deliveryFromPickUp = new HashMap<Long, Long>();
		this.deliveryFromPickUp = request.getDeliveryFromPickUp();
	}
	
	public HashMap<Long, Long> getDeliveryFromPickUp() {
		return this.deliveryFromPickUp;
	}

	public Intersection getStartingLocation() {
		return startingLocation;
	}

	public LocalTime getStartingTime() {
		return startingTime;
	}

	public ArrayList<Intersection> getPickUpLocations() {
		return pickUpLocations;
	}

	public ArrayList<Intersection> getDeliveryLocations() {
		return deliveryLocations;
	}

	public HashMap<Long, Integer> getDurations() {
		return durations;
	}

	/**
	 * @return an iterator on all pick up locations in the request
	 */
	public Iterator<Intersection> getPickUpLocationsIterator() {
		return pickUpLocations.iterator();
	}

	/**
	 * @return an iterator on all delivery locations in the request
	 */
	public Iterator<Intersection> getDeliveryLocationsIterator() {
		return deliveryLocations.iterator();
	}

	public Long getDeliveryFromPickUp(Long id) {
		return deliveryFromPickUp.get(id);
	}

	public Boolean isPickUp(Long id) {
		return (deliveryFromPickUp.get(id) != null);
	}

	public Boolean hasDelivery(Long id) {
		return (deliveryFromPickUp.get(id) != null && deliveryFromPickUp.get(id) != -1);
	}

	@Override
	public String toString() {
		return "Request [startingLocation=" + startingLocation + ", startingTime=" + startingTime + ", Durations="
				+ durations + ", pickUpLocations=" + pickUpLocations + ", deliveryLocations=" + deliveryLocations + "]";
	}

	public Integer getDurationPickUpDelivery(Long id) {
		return durations.get(id);
	}

	public void addRequest(Intersection pickUp, Intersection delivery, Integer pickUpDuration,
			Integer deliveryDuration) {
		this.deliveryFromPickUp.put(pickUp.getId(), delivery.getId());
		this.pickUpLocations.add(pickUp);
		this.deliveryLocations.add(delivery);
		this.durations.put(pickUp.getId(), pickUpDuration);
		this.durations.put(delivery.getId(), deliveryDuration);
	}
}