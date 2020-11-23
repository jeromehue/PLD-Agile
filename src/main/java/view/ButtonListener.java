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
		// Methode appelee par l'ecouteur de boutons a chaque fois qu'un bouton est clique
		// Envoi au controleur du message correspondant au bouton clique
		switch (e.getActionCommand()){
			default: 	System.out.println("Action performed from " + e.getActionCommand());
						controller.loadMap();
						break;
		}
	}

}
