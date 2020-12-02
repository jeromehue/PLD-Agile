package controller;

import modele.Way;
import modele.Intersection;
import modele.Tour;
import view.Window;

public class OrderModificationState implements State {

	
	public void clickOnStep(Window w, Way wa) {
		System.out.println("[Order Modification State] Modify order of tour in controller");
		System.out.println("[Order Modification State] Intersection to be modify" + wa.getDeparture());
		
		// Testing purposes
		int newIndex = 1;
		Intersection intersection = wa.getDeparture();
		Tour t = w.graphicalView.getTour();
		//changeOrder (Tour tour, Intersection intersection, int newIndex)
		
	}
}
