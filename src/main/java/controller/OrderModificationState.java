package controller;

import modele.Way;
import view.Window;

public class OrderModificationState implements State {

	
	public void clickOnStep(Window w, Way wa) {
		System.out.println("[Order Modification State] Modify order of tour in controller");
		System.out.println("[Order Modification State] Intersection to be modify" + wa.getDeparture());
	}
}
