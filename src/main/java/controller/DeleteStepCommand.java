package controller;

import algo.Pcc;
import modele.Request;
import modele.Tour;

public class DeleteStepCommand implements Command {
	
	private Pcc pcc;
	private Tour tour;
	private Request request;
	
	/**
	 * Create the command which adds the shape s to the plan p
	 * @param p the plan to which f is added
	 * @param s the shape added to p
	 */
	public DeleteStepCommand(Pcc p, Tour t, Request r) {
		this.pcc = p;
		this.tour = t;
		this.request = r;
	}

	@Override
	public void doCommand() {
		// TODO
	}

	@Override
	public void undoCommand() {
		// TODO
	}

}
