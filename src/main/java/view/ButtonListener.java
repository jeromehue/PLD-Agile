package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Controller;

public class ButtonListener  implements ActionListener  {

	private Controller controller;
	
	public ButtonListener(Controller controller){
		this.controller = controller;
	}
	
	public void actionPerformed(ActionEvent e) { 
		// Methode appelee par l'ecouteur de boutons a chaque fois qu'un bouton est clique
		// Envoi au controleur du message correspondant au bouton clique
		switch (e.getActionCommand()) {
		case Window.LOAD_REQUEST: System.out.println("Click on LOAD_REQUEST button");
		controller.loadRequest(); break;
		case Window.LOAD_MAP: System.out.println("Click on LOAD_MAP button");
		controller.loadMap(); break;
		default:  break;
		}
	}
}
	