package controller;

import algo.Pcc;
import modele.Intersection;
import modele.Tour;
import view.GraphicalView;

/**
 * Command that deletes or puts back a step in a tour,
 * after a click on the 'Delete step' button.
 * @author H4414
 */

public class DeleteStepCommand implements Command {
	
	/**
	 * Contains all the shortest ways between intersection and delivery points
	 */
	private Pcc pcc;
	/**
	 * Tour we save to the undo method
	 */
	private Tour oldTour;
	/**
	 * The pick up intersection to add to the request
	 */
	private Tour tour;
	/**
	 * Intersection to delete in the tour
	 */
	private Intersection intersection;
	/**
	 *  The graphical view displayed
	 */
	private GraphicalView g;

	/**
	 * Create the command which adds the shape s to the plan p
	 * 
	 * @param p the plan to which f is added
	 * @param g the graphical view to display
	 * @param t the tour to which we delete the intersection
	 * @param i the intersection to delete
	 */
	public DeleteStepCommand(GraphicalView g, Pcc p, Tour t, Intersection i) {
		this.pcc = p;
		this.oldTour = null;
		this.tour = g.getTour();
		this.intersection = i;
		this.g = g;
	}

	@Override
	public void doCommand() {
		this.oldTour = new Tour(this.tour); // Copie du tour (contenant le step)
		this.oldTour.setTour(this.oldTour);
		this.oldTour.notifyObservers();
		
		Tour newTour = this.pcc.deleteStep(this.tour, this.intersection); // On modifie le tour
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
