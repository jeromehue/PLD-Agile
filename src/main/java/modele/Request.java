package modele;

import java.util.ArrayList;

public class Request {
	private ArrayList<Intersection> pickUpLocations;
	private ArrayList<Intersection> deliveryLocations;
    private ArrayList<Integer> pickUpTime;
    private ArrayList<Integer> deliveryTime;

	public Request(ArrayList<Intersection> pickUps, ArrayList<Intersection> deliveries) {
		pickUpLocations = pickUps;
		deliveryLocations = deliveries;
	}

	public ArrayList<Intersection> getPickUpLocations() {
		return pickUpLocations;
	}

	public ArrayList<Intersection> getDeliveryLocations() {
		return deliveryLocations;
	}
	
}
