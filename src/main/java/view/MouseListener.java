package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import modele.Intersection;
import modele.Point;
import modele.PointFactory;
import modele.Segment;
import java.util.List;

public class MouseListener extends MouseAdapter{

	private Window window;
	private GraphicalView graphicalView;

	
	
	/**
	 * @param controller
	 * @param window
	 * @param graphicalView
	 */
	public MouseListener(Window window, GraphicalView graphicalView) {
		this.window = window;
		this.graphicalView = graphicalView;
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
			if( graphicalView.getCityMap() != null ) {
				int px = p.getX();
				int py = p.getY();
				
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
						distance = distBetweenPointAndLine(px,py,x1,y1,x2,y2);
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
		int x = Math.round(e.getX());
		int y = Math.round(e.getY());
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
	    float xx = (float) x1 - x;
	    float yy = (float) y1 - y;

	    return (float) Math.sqrt(xx * xx + yy * yy);
	}

	
	private boolean inBox(int x, int y, int x1, int y1,  int x2, int y2) {
		int maxX;
		int maxY;
		int minX;
		int minY;
		if(x2 > x1) { maxX = x2; minX = x1; } else { maxX = x1;  minX = x2;}
		if(y2 > y1) { maxY = y2; minY = y1; } else { maxY = y1;  minY = y2;}
		
		return  (x <= maxX && x >= minX && y <= maxY && y >= minY);

	}
	
	
	
}
