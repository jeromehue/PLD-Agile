package controller;

import modele.Way;
import view.Window;

public class TourModificationState  implements State {
	
	@Override
	public void clickOnStep( Window w, Way wa) {
		System.out.println("[Tour Modification State] not implemented yet");
	}
	
	public void modifyOrder() {
		System.out.println("[Tour Modification State] Modify order of tour in controller");
	}
}
