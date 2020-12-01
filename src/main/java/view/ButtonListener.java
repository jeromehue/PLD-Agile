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
		// Méthode appelée par l'ecouteur de boutons a chaque fois qu'un bouton est clique
		// Envoi au controleur du message correspondant au bouton clique
		switch (e.getActionCommand()) {
		case Window.LOAD_REQUEST: System.out.println("Click on LOAD_REQUEST button");
		controller.loadRequest(); break;
		case Window.LOAD_MAP: System.out.println("Click on LOAD_MAP button");
		controller.loadMap(); break;
		case Window.COMPUTE_TOUR: System.out.println("Click on COMPUTE_TOUR button");
		controller.computeTour(); break;
		case Window.DISPLAY_WAY: System.out.println("Click on DISPLAY_WAY button");
		break;
		default: System.out.println("Click DEFAULT_CASE button"); break;
		}
	}
}
	
