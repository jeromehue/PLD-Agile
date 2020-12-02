package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.Controller;

public class ButtonListener  implements ActionListener  {

	private Controller controller;
	
	public ButtonListener(Controller controller){
		this.controller = controller;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof ButtonWay) {
			switch (e.getActionCommand()) {
			case Window.HIGHLIGHT_WAY: System.out.println("Click on DISPLAY_WAY button");
			controller.highlightWay(((ButtonWay) e.getSource()).getWay()); break;
			default: System.out.println("Click button: DEFAULT_CASE_1"); break;
			}
		}
		else {
			// Méthode appelée par l'ecouteur de boutons a chaque fois qu'un bouton est clique
			// Envoi au controleur du message correspondant au bouton clique
			switch (e.getActionCommand()) {
			case Window.LOAD_REQUEST: System.out.println("Click on LOAD_REQUEST button");
			controller.loadRequest(); break;
			case Window.LOAD_MAP: System.out.println("Click on LOAD_MAP button");
			controller.loadMap(); break;
			case Window.COMPUTE_TOUR: System.out.println("Click on COMPUTE_TOUR button");
			controller.computeTour(); break;
			case Window.MODIFY_TOUR: System.out.println("Click on MODIFY_TOUR button");
			controller.changeOptionalsButtonsVisibility();break;
			case Window.MODIFY_ORDER: System.out.println("Click on MODIFY_ORDER button");
			break;
			default: System.out.println("Click button: DEFAULT_CASE_2"); break;
			}
		}
	}
}
	
