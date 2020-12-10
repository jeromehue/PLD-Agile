package controller;

import algo.Pcc;
import modele.Intersection;
import modele.Tour;
import view.GraphicalView;

public class AddRequestCommand implements Command {

	private GraphicalView g;
	private Pcc pcc;

	private Tour oldTour, tour;
	private Intersection pickup, delivery;
	private int pickUpDuration, deliveryDuration, pickUpIndex, deliveryIndex;

	/**
	 * Create the command which adds the shape s to the plan p
	 * 
	 * @param p the plan to which f is added
	 * @param s the shape added to p
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

	/**
	 * Allow to use redo command
	 */
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

	/**
	 * Allow to use undo command
	 */
	@Override
	public void undoCommand() {
		this.tour.setTour(this.oldTour);
		g.getCityMap().setIntersectionCoordinates(g);
		this.tour.notifyObservers();
	}

}
