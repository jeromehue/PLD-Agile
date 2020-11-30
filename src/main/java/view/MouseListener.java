package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import controller.Controller;

import modele.Point;
import modele.PointFactory;


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
		System.out.println("Souris en train de bouger - Récupération des coordonnées");
		Point p = coordinates(evt);
		if (p != null) {
			System.out.println("(" +p.getX() + "," + p.getY() + ")");
		}
		
	}
	
	private Point coordinates(MouseEvent evt){
		MouseEvent e = SwingUtilities.convertMouseEvent(window, evt, graphicalView);
		int x = Math.round((float)e.getX());
		int y = Math.round((float)e.getY());
		return PointFactory.createPoint(x, y);
	}
	
}
