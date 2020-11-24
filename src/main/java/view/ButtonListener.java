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
		// Méthode appelée par l'ecouteur de boutons a chaque fois qu'un bouton est clique
		// Envoi au controleur du message correspondant au bouton clique
		switch (e.getActionCommand()){
		
			case Window.LOAD_REQUEST: 
					System.out.println("Click on LOAD_REQUEST button");
					final JFileChooser fcr = new JFileChooser();
					FileNameExtensionFilter fcr_filter = new FileNameExtensionFilter("XML files", "xml");
					fcr.setFileFilter(fcr_filter);
					fcr.setCurrentDirectory(new File("./src/main"));
					String absPathb = "";
			        if(fcr.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			            System.out.println("You chose to open this file: " +
			            fcr.getSelectedFile().getAbsolutePath()) ;
			            absPathb = fcr.getSelectedFile().getAbsolutePath();
			        }
					controller.loadRequest(absPathb); 
					break;
					
			case Window.LOAD_MAP: 	
					System.out.println("Action performed from " + e.getActionCommand());
					final JFileChooser fc = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("XML files", "xml");
					fc.setFileFilter(filter);
					fc.setCurrentDirectory(new File("./src/main"));
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
	