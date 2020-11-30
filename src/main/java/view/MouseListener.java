package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controller.Controller;



public class MouseListener extends MouseAdapter{

	private Controller controller;
	private Window window;
	private GraphicalView graphicalView;

	
	
	/**
	 * @param controller
	 * @param window
	 * @param graphicalView
	 */
	public MouseListener(Controller controller, Window window, GraphicalView graphicalView) {
		this.controller = controller;
		this.window = window;
		this.graphicalView = graphicalView;
	}



	public void mouseMoved(MouseEvent evt) {
		// Methode appelee a chaque fois que la souris est bougee
		// Envoie au controleur les coordonnees de la souris.
		//System.out.println("Souris en train de bouger");
	}
	
}
