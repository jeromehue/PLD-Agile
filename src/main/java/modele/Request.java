package modele;

import java.util.ArrayList;

public class Request {
	private ArrayList<Intersection> pickUpLocations;
	private ArrayList<Intersection> deliveryLocations;
	private Intersection startingLocation; 
    private ArrayList<Integer> pickUpTime;
    private ArrayList<Integer> deliveryTime;

	public Request(ArrayList<Intersection> pickUps, ArrayList<Intersection> deliveries, Intersection start) {
		pickUpLocations = pickUps;
		deliveryLocations = deliveries;
		startingLocation = start;
	}

	public ArrayList<Intersection> getPickUpLocations() {
		return pickUpLocations;
	}

	public ArrayList<Intersection> getDeliveryLocations() {
		return deliveryLocations;
	}

	public Intersection getStartingLocation() {
		return startingLocation;
	}

	public ArrayList<Integer> getPickUpTime() {
		return pickUpTime;
	}

	public ArrayList<Integer> getDeliveryTime() {
		return deliveryTime;
	}

	
	
}
