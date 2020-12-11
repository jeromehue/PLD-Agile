package controller;

import algo.Pcc;
import modele.Intersection;
import modele.Tour;
import view.GraphicalView;

/**
 * Command that adds or remove a request from a tour, after a click on the 'Add
 * a request' button.
 * 
 * @author H4414
 */

public class AddRequestCommand implements Command {
	/**
	 * The graphical view displayed
	 */
	private GraphicalView g;
	/**
	 * Contains all the shortest ways between intersection and delivery points
	 */
	private Pcc pcc;
	/**
	 * Tour we save to the undo method
	 */
	private Tour oldTour;
	/**
	 * Current tour.
	 */
	private Tour tour;
	/**
	 * The pick up intersection to add to the request
	 */
	private Intersection pickup;
	/**
	 * The delivery intersection to add to the request
	 */
	private Intersection delivery;
	/**
	 * The duration to pick up the package
	 */
	private int pickUpDuration;
	/**
	 * The duration deliver the package
	 */
	private int deliveryDuration;
	/**
	 * The index of the pick up point
	 */
	private int pickUpIndex;
	/**
	 * The index of the delivery point
	 */
	private int deliveryIndex;

	/**
	 * Create the command which adds the shape s to the plan p
	 * 
	 * @param p                the plan to which f is added
	 * @param g                the graphical view displayed
	 * @param tour             the tour to which we add a request
	 * @param pickup           the pickup intersection we add to the tour
	 * @param delivery         the delivery intersection we add to the tour
	 * @param pickUpDuration   the duration to pick up the package
	 * @param deliveryDuration the duration to delivers the package
	 * @param pickUpIndex      the index of the pick up point
	 * @param deliveryIndex    the index of the delivery point
	 */
	public AddRequestCommand(GraphicalView g, Pcc p, Tour tour, Intersection pickup, Intersection delivery,
			int pickUpDuration, int deliveryDuration, int pickUpIndex, int deliveryIndex) {
		this.pcc = p;
		this.oldTour = null;
		this.tour = g.getTour();
		this.pickup = pickup;
		this.delivery = delivery;
		this.pickUpDuration = pickUpDuration;
		this.deliveryDuration = deliveryDuration;
		this.pickUpIndex = pickUpIndex;
		this.deliveryIndex = deliveryIndex;
		this.g = g;
	}

	@Override
	public void doCommand() {
		this.oldTour = new Tour(this.tour);
		this.oldTour.setTour(this.oldTour);
		this.oldTour.notifyObservers();

		Tour newTour = pcc.addRequest(tour, pickup, delivery, pickUpDuration, deliveryDuration, pickUpIndex,
				deliveryIndex);
		this.tour.setTour(newTour);
		g.getCityMap().setIntersectionCoordinates(g);
		this.tour.notifyObservers();
	}

	@Override
	public void undoCommand() {
		this.tour.setTour(this.oldTour);
		g.getCityMap().setIntersectionCoordinates(g);
		this.tour.notifyObservers();
	}

}
