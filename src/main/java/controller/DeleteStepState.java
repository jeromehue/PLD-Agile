package controller;

import javax.swing.JButton;

import algo.Pcc;
import modele.Intersection;
import modele.Tour;
import modele.Way;
import view.Window;

public class DeleteStepState implements State {

	@Override
	public void clickOnStep(Controller c, Window w, ListOfCommands l, Way wa, JButton button, Tour t) {
		Intersection stepToDelete = wa.getDeparture();
		System.out.println(stepToDelete.toString());

		Pcc shortestPathComputer = new Pcc(w.getGraphicalView().getCityMap(), w.getGraphicalView().getRequest());
		shortestPathComputer.computePcc();

		l.add(new DeleteStepCommand(shortestPathComputer, t, stepToDelete));

		c.setCurrentstate(c.tourModificationState);
	}

	@Override
	public void undo(ListOfCommands listOfCdes) {
		listOfCdes.undo();
	}

	@Override
	public void redo(ListOfCommands listOfCdes) {
		listOfCdes.redo();
	}
}
