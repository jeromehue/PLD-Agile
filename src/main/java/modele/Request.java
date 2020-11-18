package modele;

public class Request {

	private Intersection pickUpAdress;
	private Intersection deliveryAdress;
	private int pickUpTime;
	private int deliveryTime;
	
	
	/**
	 * Default constructor
	 */
	public Request() {}


	/**
	 * @param pickUpAdress 		
	 * @param deliveryAdress	
	 * @param pickUpTime
	 * @param deliveryTime
	 */
	public Request(Intersection pickUpAdress, Intersection deliveryAdress, int pickUpTime, int deliveryTime) {
		this.pickUpAdress = pickUpAdress;
		this.deliveryAdress = deliveryAdress;
		this.pickUpTime = pickUpTime;
		this.deliveryTime = deliveryTime;
	}


	
	
	
}
