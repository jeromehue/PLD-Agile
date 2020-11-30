package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import controller.Controller;

import modele.Point;
import modele.PointFactory;
import modele.Intersection;
import java.util.List;

import java.lang.*;

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


	private int  absoluteX (int X, int mX) {
		
		return Math.abs(mX -  X);
	}
	
	public void mouseMoved(MouseEvent evt) {
		// Methode appelee a chaque fois que la souris est bougee
		// Envoie au controleur les coordonnees de la souris.
		//System.out.println("Souris en train de bouger - Récupération des coordonnées");
		Point p = coordinates(evt);
		if (p != null) {
			//System.out.println("(" +p.getX() + "," + p.getY() + ")");

			//System.out.println("(" +p.getX() + "," + p.getY() + ") : ");
			if( graphicalView.getCityMap() != null ) {
				
				 int minX = p.getX() -10;
				 int maxX = p.getX() +10;
				 int minY = p.getY() -10;
				 int maxY = p.getY() +10;
				 System.out.println("minX, maxX, minY, maxY : " +minX +" , " +maxX + " , " +minY + " , " +maxY);
				 List<Intersection> listeInters = graphicalView.getCityMap().getIntersections(); 
				 for(Intersection i : listeInters) {
					 int iX = i.getCoordinates().getX();
					 int iY = i.getCoordinates().getY();
					 //System.out.println("ix, iy " + iX +"," + iY);
					 if( iX > minX && iX < maxX && iY < maxY && iY > minY) {
					 System.out.println("Coordonneés de l'intersection : " + i.getCoordinates().getX() +
							 ":" + i.getCoordinates().getY() );
					 }
				}
			}
		}
		
	}
	
	private Point coordinates(MouseEvent evt){
		MouseEvent e = SwingUtilities.convertMouseEvent(window, evt, graphicalView);
		int x = Math.round((float)e.getX());
		int y = Math.round((float)e.getY());
		return PointFactory.createPoint(x, y);
	}
	
}
