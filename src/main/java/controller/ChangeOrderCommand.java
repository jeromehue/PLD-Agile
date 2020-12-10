package controller;

import algo.Pcc;
import modele.Intersection;
import modele.Tour;
import view.GraphicalView;

public class ChangeOrderCommand implements Command {

	private Pcc pcc;
	private Tour tour;
	private Intersection intersection;
	private int offset;

	/**
	 * Create the command which adds the shape s to the plan p
	 * 
	 * @param p the plan to which f is added
	 * @param s the shape added to p
	 */
	public ChangeOrderCommand(GraphicalView g, Pcc p, Tour t, Intersection i, int offset) {
		this.pcc = p;
		this.tour = g.getTour();
		this.intersection = i;
		this.offset = offset;
	}

	@Override
	/**
	 * Allow redo command
	 */
	public void doCommand() {
		this.pcc.changeOrder(tour, intersection, offset);
		tour.setTour(tour);
		tour.notifyObservers();
	}

	@Override
	/**
	 * Allow undo command.
	 */
	public void undoCommand() {
		this.pcc.changeOrder(tour, intersection, -offset);
		tour.setTour(tour);
		tour.notifyObservers();
	}

}
