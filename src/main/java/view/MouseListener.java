package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import controller.Controller;
import modele.Point;
import modele.PointFactory;

public class MouseListener extends MouseAdapter{

	private Window window;
	private GraphicalView graphicalView;
	private Controller controller;
	
	
	/**
	 * @param controller
	 * @param window
	 * @param graphicalView
	 */
	public MouseListener(Window window, GraphicalView graphicalView, Controller controller ) {
		this.window = window;
		this.graphicalView = graphicalView;
		this.controller = controller;
	}

	public void mousePressed(MouseEvent e) {
		Point p = coordinates(e);
		this.window.setMessage("HEY !!");
	}
	
	
	public void mouseMoved(MouseEvent evt) {
		// Methode appelee a chaque fois que la souris est bougee
		// Envoie au controleur les coordonnees de la souris.
		//System.out.println("Souris en train de bouger - Récupération des coordonnées");
		
		Point p = coordinates(evt);
		if (p != null) {
			controller.mouseMoved(p);
		} else {
			this.window.setMessage("");
			this.graphicalView.highlight(null);
			this.graphicalView.highlightInter(null);
		}
		
	}
	
	private Point coordinates(MouseEvent evt){
		MouseEvent e = SwingUtilities.convertMouseEvent(window, evt, graphicalView);
		int x = Math.round(e.getX());
		int y = Math.round(e.getY());
		return PointFactory.createPoint(x, y);
	}
	
	

	

	
	
	
	
	
}
