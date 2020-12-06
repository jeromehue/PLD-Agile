package controller;

import javax.swing.JButton;

import modele.Tour;
import modele.Way;
import view.Window;

public class TourModificationState  implements State {
	
	@Override
	public void clickOnStep( Window w, Way wa, JButton button, Tour t) {
		System.out.println("[Tour Modification State] not implemented yet");
		
	}
	
	@Override
	public void deleteAStep(Controller c, Window w){
		w.setMessage("Choose a step to delete");
		c.setCurrentstate(c.deleteStepState);
	}
	
}
