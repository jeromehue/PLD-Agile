package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

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
						final JFileChooser fc = new JFileChooser();
						int returnVal = fc.showOpenDialog(null);
						String absPath = "";
				        if(returnVal == JFileChooser.APPROVE_OPTION) {
				            System.out.println("You chose to open this file: " +
				                    fc.getSelectedFile().getAbsolutePath()) ;
				            absPath = fc.getSelectedFile().getAbsolutePath();
				        }
						controller.loadMap(absPath);
						break;
		}
	}

}
	