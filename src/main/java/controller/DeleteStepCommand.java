package controller;

import algo.Pcc;
import modele.Intersection;
import modele.Tour;
import view.GraphicalView;

public class DeleteStepCommand implements Command {

	private Pcc pcc;
	private Tour oldTour, tour;
	private Intersection intersection;
	private GraphicalView g;

	/**
	 * Create the command which adds the shape s to the plan p
	 * 
	 * @param p the plan to which f is added
	 * @param s the shape added to p
	 */
	public DeleteStepCommand(GraphicalView g, Pcc p, Tour t, Intersection i) {
		this.pcc = p;
		this.oldTour = null;
		this.tour = g.getTour();
		this.intersection = i;
		this.g = g;
	}

	@Override
	/*
	 * Allow the redo command.
	 */
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
	/**
	 * Allow the undo method.
	 */
	public void undoCommand() {
		this.tour.setTour(this.oldTour);
		g.getCityMap().setIntersectionCoordinates(g);
		this.tour.notifyObservers();
	}

}
