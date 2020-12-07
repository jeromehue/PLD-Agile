package controller;

import javax.swing.JButton;

import algo.Pcc;
import modele.Intersection;
import modele.Tour;
import modele.Way;
import view.Window;

public class DeleteStepState implements State{
	
	@Override
	public void clickOnStep(Controller c, Window w, ListOfCommands l, Way wa, JButton button, Tour t){	
		Intersection stepToDelete = wa.getDeparture();
		System.out.println(stepToDelete.toString());
		
		Pcc shortestPathComputer = new Pcc(w.getGraphicalView().getCityMap() , w.getGraphicalView().getRequest());
		shortestPathComputer.computePcc();
		t.setTour( shortestPathComputer.deleteIntersection(t, stepToDelete) );
		t.notifyObservers();
		
		c.setCurrentstate(c.tourModificationState);
	}
}
