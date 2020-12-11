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
