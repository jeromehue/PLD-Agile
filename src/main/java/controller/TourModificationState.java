package controller;

import javax.swing.JButton;

import modele.Tour;
import modele.Way;
import view.Window;

public class TourModificationState  implements State {
	
	@Override
	public void clickOnStep( Controller c, Window w, Way wa, JButton button, Tour t) {
		w.setMessage("Choose a modification option before selecting a step.");
		
	}
	
	@Override
	public void modifyOrder(Controller c, Window w){
		w.setMessage("Choose which step to modify order");
		c.setCurrentstate(c.orderModificationState);
	}
	
	@Override
	public void deleteAStep(Controller c, Window w){
		w.setMessage("Choose a step to delete");
		c.setCurrentstate(c.deleteStepState);
	}
	
}
