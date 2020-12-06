package controller;

import javax.swing.JButton;

import algo.Pcc;
import modele.Intersection;
import modele.Tour;
import modele.Way;
import view.Window;

public class DeleteStepState implements State{
	
	@Override
	public void clickOnStep(Window w, Way wa, JButton button, Tour t){	
		button.setContentAreaFilled(true);
		//w.graphicalView.setHighlightedWay(wa);
		Intersection stepToDelete = wa.getDeparture();
		
		Pcc shortestPathComputer = new Pcc(w.graphicalView.getCityMap() , w.graphicalView.getRequest());
		shortestPathComputer.deleteIntersection(t, stepToDelete);
	}
}
