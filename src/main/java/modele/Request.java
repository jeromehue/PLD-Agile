package modele;

import java.util.ArrayList;
import java.util.Iterator;

public class Request {
	
	private Long startingLocation; 
	private String startingTime;
	// List of pickup/delivery points with their duration
    private ArrayList<Integer> pickUpDurations;
    private ArrayList<Integer> deliveryDurations;
    private ArrayList<Long> pickUpLocations;
	private ArrayList<Long> deliveryLocations;

    
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
	public Request(Long startingLocation, String startingTime, ArrayList<Integer> pickUpDurations,
			ArrayList<Integer> deliveryDurations, ArrayList<Long> pickUpLocations, ArrayList<Long> deliveryLocations) {
		this.startingLocation = startingLocation;
		this.startingTime = startingTime;
		this.pickUpDurations = pickUpDurations;
		this.deliveryDurations = deliveryDurations;
		this.pickUpLocations = pickUpLocations;
		this.deliveryLocations = deliveryLocations;
	}


	public Long getStartingLocation() {
		return startingLocation;
	}


	public String getStartingTime() {
		return startingTime;
	}


	public ArrayList<Integer> getPickUpDurations() {
		return pickUpDurations;
	}


	public ArrayList<Integer> getDeliveryDurations() {
		return deliveryDurations;
	}


	public ArrayList<Long> getPickUpLocations() {
		return pickUpLocations;
	}


	public ArrayList<Long> getDeliveryLocations() {
		return deliveryLocations;
	}
	
	/**
	 * @return an iterator on all pick up locations in the request
	 */
	public Iterator<Long> getPickUpLocationsIterator(){
		return pickUpLocations.iterator();
	}
	
	/**
	 * @return an iterator on all delivery locations in the request
	 */
	public Iterator<Long> getDeliveryLocationsIterator(){
		return deliveryLocations.iterator();
	}

	@Override
	public String toString() {
		return "Request [startingLocation=" + startingLocation + ", startingTime=" + startingTime + ", pickUpDurations="
				+ pickUpDurations + ", deliveryDurations=" + deliveryDurations + ", pickUpLocations=" + pickUpLocations
				+ ", deliveryLocations=" + deliveryLocations + "]";
	}


	
	

	
	
	
}
