package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.Controller;

public class ButtonListener  implements ActionListener  {

	private Controller controler;
	
	public ButtonListener(Controller controler){
		this.controler = controler;
	}
	
	public void actionPerformed(ActionEvent e) { 
		// Methode appelee par l'ecouteur de boutons a chaque fois qu'un bouton est clique
		// Envoi au controleur du message correspondant au bouton clique
		switch (e.getActionCommand()){
		default: System.out.println("Action performed");
		}
	}

}
