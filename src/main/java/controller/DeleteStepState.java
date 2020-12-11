package controller;

import javax.swing.JButton;

import algo.Pcc;
import modele.Intersection;
import modele.Tour;
import modele.Way;
import view.Window;

/**
 * State reached after a click on 'Delete a request ' button.
 * 
 * @author H4414
 */

public class DeleteStepState implements State {

	@Override
	public void clickOnStep(Controller c, Window w, ListOfCommands l, Way wa, JButton button, Tour t) {
		Intersection stepToDelete = wa.getDeparture();
		Long startId = t.getRequest().getStartingLocation().getId();
		System.out.println(stepToDelete.toString());

		if (stepToDelete.getId().equals(startId)) {
			w.setMessage("You can't delete the starting point !");
		} else {
			Pcc shortestPathComputer = new Pcc(w.getGraphicalView().getCityMap(), w.getGraphicalView().getRequest());
			shortestPathComputer.computePcc();

			l.add(new DeleteStepCommand(w.getGraphicalView(), shortestPathComputer, t, stepToDelete));
		}

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
