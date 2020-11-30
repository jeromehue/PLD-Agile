package modele;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Request {
	
	private Intersection startingLocation; 
	private LocalTime startingTime;
	// List of pickup/delivery points with their duration
    private ArrayList<Integer> pickUpDurations;
    private ArrayList<Integer> deliveryDurations;
    private ArrayList<Intersection> pickUpLocations;
	private ArrayList<Intersection> deliveryLocations;
	// id of pickup, id of corresponding delivery
	private HashMap<Long, Long> deliveryFromPickUp; 

    
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
		this.pickUpDurations = pickUpDurations;
		this.deliveryDurations = deliveryDurations;
		this.pickUpLocations = pickUpLocations;
		this.deliveryLocations = deliveryLocations;
		this.deliveryFromPickUp = new HashMap<Long, Long>();
		
		for(int i = 0 ; i < pickUpLocations.size(); ++i) {
			deliveryFromPickUp.put(pickUpLocations.get(i).getId(), deliveryLocations.get(i).getId());
		}
	}


	public Intersection getStartingLocation() {
		return startingLocation;
	}


	public LocalTime getStartingTime() {
		return startingTime;
	}


	public ArrayList<Integer> getPickUpDurations() {
		return pickUpDurations;
	}


	public ArrayList<Integer> getDeliveryDurations() {
		return deliveryDurations;
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
	
	public Long getDeliveryFromPickUp(Long id) {
		return deliveryFromPickUp.get(id);
	}
	
	public Boolean isPickUp(Long id) {
		return(deliveryFromPickUp.get(id) != null);
	}

	@Override
	public String toString() {
		return "Request [startingLocation=" + startingLocation + ", startingTime=" + startingTime + ", pickUpDurations="
				+ pickUpDurations + ", deliveryDurations=" + deliveryDurations + ", pickUpLocations=" + pickUpLocations
				+ ", deliveryLocations=" + deliveryLocations + "]";
	}
}
