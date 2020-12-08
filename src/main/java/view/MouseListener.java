package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import controller.Controller;

import modele.Point;
import modele.PointFactory;

/**
 * Receives interesting events from the mouse and 
 * calls the controller to update data and view.
 * @author H4414
 * @see java.awt.event.MouseAdapter
 * */

public class MouseListener extends MouseAdapter {

	private Window window;
	private GraphicalView graphicalView;
	private Controller controller;

	/**
	 * Constructor
	 * 
	 * @param controller The Controller.
	 * @param window The Window.
	 * @param graphicalView The Graphical View.
	 */
	public MouseListener(Window window, GraphicalView graphicalView, Controller controller) {
		this.window = window;
		this.graphicalView = graphicalView;
		this.controller = controller;
	}
	
	
	
	/**
	 * Invoked when the mouse button has been clicked (pressed and released)
	 * on graphicalView.
	 */
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

	
	/**
	 * Invoked when the mouse cursor has been moved onto a component but 
	 * no buttons have been pushed.
	 */
	@Override
	public void mouseMoved(MouseEvent evt) {
		Point p = coordinates(evt);
		if (p != null) {
			controller.mouseMoved(p);
		} else {
			this.window.setStreet("");
			this.graphicalView.highlight(null);
			this.graphicalView.setHighlightInter(null);
		}

	}

	/**
	 * Invoked by mouseClicked to determine the point whose coordinates a those
	 * of mouse cursor
	 * @return A point whose coordinates are those of the mouse.
	 * @see view.MouseListenr#mouseClicked(MouseEvent)
	 */
	private Point coordinates(MouseEvent evt) {
		MouseEvent e = SwingUtilities.convertMouseEvent(window, evt, graphicalView);
		int x = Math.round(e.getX());
		int y = Math.round(e.getY());
		return PointFactory.createPoint(x, y);
	}
}
