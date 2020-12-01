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

    
    /**
     * Default constructor
     */
    public Request() {}


	/**
	 * @param startingLocation
	 * @param startingTime
	 * @param pickUpDurations
	 * @param deliveryDurations
	 * @param pickUpLocations
	 * @param deliveryLocations
	 */
	public Request(Intersection startingLocation, LocalTime startingTime, ArrayList<Integer> pickUpDurations,
			ArrayList<Integer> deliveryDurations, ArrayList<Intersection> pickUpLocations, ArrayList<Intersection> deliveryLocations) {
		this.startingLocation = startingLocation;
		this.startingTime = startingTime;
		this.pickUpLocations = pickUpLocations;
		this.deliveryLocations = deliveryLocations;
		ArrayList<Integer> allDurations = new ArrayList<Integer>();
		allDurations.addAll(pickUpDurations);
		allDurations.addAll(deliveryDurations);
		ArrayList<Intersection> allLocations = new ArrayList<Intersection>();
		allLocations.addAll(0,pickUpLocations);
		allLocations.addAll(0,deliveryLocations);
		durations = new HashMap<Long, Integer>();
		for(int i =0; i< allDurations.size(); i++ ) {
			durations.put(allLocations.get(i).getId(), allDurations.get(i));
		}
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
	
	/**
	 * @return an iterator on all pick up locations in the request
	 */
	public Iterator<Intersection> getPickUpLocationsIterator(){
		return pickUpLocations.iterator();
	}
	
	/**
	 * @return an iterator on all delivery locations in the request
	 */
	public Iterator<Intersection> getDeliveryLocationsIterator(){
		return deliveryLocations.iterator();
	}

	@Override
	public String toString() {
		return "Request [startingLocation=" + startingLocation + ", startingTime=" + startingTime + ", Durations=" + durations + ", pickUpLocations=" + pickUpLocations
				+ ", deliveryLocations=" + deliveryLocations + "]";
	}
	
	public Integer getDurationPickUpDelivery(Long id) {
		return durations.get(id);
	}


	
	

	
	
	
}
