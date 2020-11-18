package modele;

mport java.util.ArrayList;

public class Request {
	private ArrayList<Intersection> pickUpLocations;
	private ArrayList<Intersection> deliveryLocations;
	
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
