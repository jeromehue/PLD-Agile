package controller;

import algo.Pcc;
import modele.Intersection;
import modele.Tour;

public class DeleteStepCommand implements Command {

	private Pcc pcc;
	private Tour oldTour;
	private Tour tour;
	private Intersection intersection;

	/**
	 * Create the command which adds the shape s to the plan p
	 * 
	 * @param p the plan to which f is added
	 * @param s the shape added to p
	 */
	public DeleteStepCommand(Pcc p, Tour t, Intersection i) {
		this.pcc = p;
		this.tour = t;
		this.intersection = i;
	}

	@Override
	public void doCommand() {
		// TODO: Make it work
		this.oldTour = this.tour;
		this.tour = this.pcc.deleteIntersection(this.tour, this.intersection);
		tour.setTour(tour);
		tour.notifyObservers();
	}

	@Override
	public void undoCommand() {
		// TODO: Make it work
		tour.setTour(this.oldTour);
		tour.notifyObservers();
	}

}
