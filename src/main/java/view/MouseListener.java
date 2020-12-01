package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import controller.Controller;

import modele.Point;
import modele.PointFactory;
import modele.Segment;
import java.util.List;

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
		//System.out.println("Souris en train de bouger - Récupération des coordonnées");
		Point p = coordinates(evt);
		if (p != null) {
			//System.out.println("(" +p.getX() + "," + p.getY() + ")");

			//System.out.println("(" +p.getX() + "," + p.getY() + ") : ");
			if( graphicalView.getCityMap() != null ) {
				int px = p.getX();
				int py = p.getY();
				/*
				
				//int minX = p.getX() -10;
				//int maxX = p.getX() +10;
				//int minY = p.getY() -10;
				//int maxY = p.getY() +10;
				//System.out.println("minX, maxX, minY, maxY : " +minX +" , " +maxX + " , " +minY + " , " +maxY);
				List<Intersection> listeInters = graphicalView.getCityMap().getIntersections(); 
				// Boucle sur les intersections pour sélectionner la plus proche
				int minDistInter = 1000;
				Intersection closest = null;
				for(Intersection i : listeInters) {
					int dist = p.distance(i.getCoordinates());
					if(dist < minDistInter) {
						minDistInter = dist;
						closest = i;
					}
				}
				System.out.println("closest intersection is  : "
						 + closest.getCoordinates().getX() + " : "+ closest.getCoordinates().getY());
				List<Segment> outbounds = closest.getOutboundSegments();
				float mindist = 100;
				Segment sclosest = null;
				for(Segment s : outbounds) {
					int x1 = s.getOrigin().getCoordinates().getX();
					int y1 = s.getOrigin().getCoordinates().getY();
					int x2 = s.getDestination().getCoordinates().getX();
					int y2 = s.getDestination().getCoordinates().getY();
					
					//if( (x2 - x1) >  0 && x2 > px && px > x1)
					//	this.graphicalView.highlight(s);
					//else if ( (x2 - x1) <=  0 && x2 < px && px < x1 )
					//	this.graphicalView.highlight(s);
					
					float distance = distBetweenPointAndLine(px,py,x1,y1,x2,y2);
					if(distance < mindist ) {
						mindist =distance;
						sclosest = s;
					}
					System.out.println("Calculated distance : " + distance);
				}
				this.graphicalView.highlight(sclosest);
				*/
				
				List<Segment> allsegments = graphicalView.getCityMap().getSegments();
				float mindist= (float) 0.5;
				Segment sclosest = null;
				for(Segment s: allsegments) {
					int x1 = s.getOrigin().getCoordinates().getX();
					int y1 = s.getOrigin().getCoordinates().getY();
					int x2 = s.getDestination().getCoordinates().getX();
					int y2 = s.getDestination().getCoordinates().getY();
					float distance = (float) 1.1;
					if (inBox(px, py, x1, y1, x2, y2) ) {
						//System.out.println(px +"," +py+"," +x1+"," +y1+"," +x2+"," +y2);
						distance = distBetweenPointAndLine(px,py,x1,y1,x2,y2);
						//System.out.println(" | distance = " +distance);
					}
						
					
					
					if(distance < mindist ) {
						mindist = distance;
						//System.out.println("distance : " +distance);
						sclosest = s;
					}
				}
				if (sclosest != null) {
				this.graphicalView.highlight(sclosest);
				this.window.setMessage(sclosest.getName());
				}
				
			}
		} else {
			this.window.setMessage("");
			this.graphicalView.highlight(null);
		}
		
	}
	
	private Point coordinates(MouseEvent evt){
		MouseEvent e = SwingUtilities.convertMouseEvent(window, evt, graphicalView);
		int x = Math.round((float)e.getX());
		int y = Math.round((float)e.getY());
		return PointFactory.createPoint(x, y);
	}
	
	private static float distBetweenPointAndLine(int x, int y, int x1, int y1, int x2, int y2) {
	    // A - the standalone point (x, y)
	    // B - start point of the line segment (x1, y1)
	    // C - end point of the line segment (x2, y2)
	    // D - the crossing point between line from A to BC

	    float AB = distBetween(x, y, x1, y1);
	    float BC = distBetween(x1, y1, x2, y2);
	    float AC = distBetween(x, y, x2, y2);

	    // Heron's formula
	    float s = (AB + BC + AC) / 2;
	    float area = (float) Math.sqrt(s * (s - AB) * (s - BC) * (s - AC));

	    // but also area == (BC * AD) / 2
	    // BC * AD == 2 * area
	    // AD == (2 * area) / BC
	    // TODO: check if BC == 0
	    float AD = (2 * area) / BC;
	    return AD;
	}

	private static float distBetween(int x, int y, int x1, int y1) {
	    float xx = x1 - x;
	    float yy = y1 - y;

	    return (float) Math.sqrt(xx * xx + yy * yy);
	}

	
	private boolean inBox(int x, int y, int x1, int y1,  int x2, int y2) {
		int maxX , maxY, minX, minY;
		if(x2 > x1) { maxX = x2; minX = x1; } else { maxX = x1;  minX = x2;}
		if(y2 > y1) { maxY = y2; minY = y1; } else { maxY = y1;  minY = y2;}
		
		if (x <= maxX && x >= minX && y <= maxY && y >= minY) 
			return true;
		return false;
	}
	
	private boolean isFlat(int x1, int y1,  int x2, int y2) {
		int diffX = (int) Math.abs(x2 - x1);
		int diffY = (int) Math.abs(y2 - y1);
		
		if (diffX < 5 || diffY < 5) 
			return true;
		return false;
	}
	
}
