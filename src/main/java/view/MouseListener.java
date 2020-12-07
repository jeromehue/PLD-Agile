package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import controller.Controller;

import modele.Point;
import modele.PointFactory;


public class MouseListener extends MouseAdapter {

	private Window window;
	private GraphicalView graphicalView;
	private Controller controller;

	/**
	 * @param controller
	 * @param window
	 * @param graphicalView
	 */
	public MouseListener(Window window, GraphicalView graphicalView, Controller controller) {
		this.window = window;
		this.graphicalView = graphicalView;
		this.controller = controller;
	}

	//public void mousePressed(MouseEvent e) {
		//Point p = coordinates(e);
		//this.window.setMessage("HEY !!");
	//}
	
	@Override
	public void mouseClicked(MouseEvent evt) {
		switch (evt.getButton()){
		case MouseEvent.BUTTON1: 
			Point p = coordinates(evt);
			if (p != null)
				controller.leftClick(p, window);
			break;
		case MouseEvent.BUTTON3: 
			//controller.rightClick();
			break;
		default:
			
		}
	}

	public void mouseMoved(MouseEvent evt) {
		Point p = coordinates(evt);
		if (p != null) {
			controller.mouseMoved(p);
		} else {
			this.window.setMessage("");
			this.graphicalView.highlight(null);
			this.graphicalView.setHighlightInter(null);
		}

	}

	private Point coordinates(MouseEvent evt) {
		MouseEvent e = SwingUtilities.convertMouseEvent(window, evt, graphicalView);
		int x = Math.round(e.getX());
		int y = Math.round(e.getY());
		return PointFactory.createPoint(x, y);
	}
}
