package controller;

import algo.Pcc;
import modele.Intersection;
import modele.Tour;
import view.GraphicalView;

public class ChangeOrderCommand implements Command {

	/**
	 * Contains all the shortest ways between intersection and delivery points.
	 */
	private Pcc pcc;
	/**
	 * The current tour.
	 */
	private Tour tour;
	/**
	 * The intersection which changes its order
	 */
	private Intersection intersection;
	/**
	 * Offset the shift of the intersection in the tour
	 */
	private int offset;

	/**
	 * Create the command which adds the shape s to the plan p
	 * 
	 * @param p the plan to which f is added
	 * @param g the graphical view to display
	 * @param t the tour to which we change the order
	 * @param i the intersection which changes its order
	 * @param offset the shift of the intersection in the tour
	 */
	public ChangeOrderCommand(GraphicalView g, Pcc p, Tour t, Intersection i, int offset) {
		this.pcc = p;
		this.tour = g.getTour();
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
