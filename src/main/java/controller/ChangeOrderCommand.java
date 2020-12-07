package controller;

import algo.Pcc;
import modele.Intersection;
import modele.Tour;

public class ChangeOrderCommand implements Command {
	
	private Pcc pcc;
	private Tour tour;
	private Intersection intersection;
	private int offset;
	
	/**
	 * Create the command which adds the shape s to the plan p
	 * @param p the plan to which f is added
	 * @param s the shape added to p
	 */
	public ChangeOrderCommand(Pcc p, Tour t, Intersection i, int offset) {
		this.pcc = p;
		this.tour = t;
		this.intersection = i;
		this.offset = offset;
	}

	@Override
	public void doCommand() {
		this.pcc.changeOrder(tour, intersection, offset);
		tour.setTour(tour);
		tour.notifyObservers();
	}

	@Override
	public void undoCommand() {
		this.pcc.changeOrder(tour, intersection, -offset);
		tour.setTour(tour);
		tour.notifyObservers();
	}

}
