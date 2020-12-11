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
 * 
 * @author H4414
 * @see java.awt.event.MouseAdapter
 * */

public class MouseListener extends MouseAdapter {

	/**
	 * The window on which everything is being displayed.
	 */
	private Window window;
	
	/**
	 * The graphical context of the view.
	 */
	private GraphicalView graphicalView;
	
	/**
	 * The controller of the application that this notifies
	 * of the actions performed with the mouse.
	 */
	private Controller controller;

	/**
	 * Default constructor to initialize all fields of this class.
	 * 
	 * @param controller The Controller of the application.
	 * @param window The Window of the application.
	 * @param graphicalView The current Graphical View.
	 */
	public MouseListener(Window window, GraphicalView graphicalView, Controller controller) {
		this.window = window;
		this.graphicalView = graphicalView;
		this.controller = controller;
	}
	
	/**
	 * Invoked when the mouse button has been clicked (pressed and released)
	 * on graphicalView.
	 * 
	 * @param evt The mouse event that was performed by the user.
	 */
	@Override
	public void mouseClicked(MouseEvent evt) {
		switch (evt.getButton()){
		case MouseEvent.BUTTON1: 
			Point p = coordinates(evt);
			if (p != null)
				controller.leftClick(p, window);
			break;
		default:
			
		}
	}

	
	/**
	 * Invoked when the mouse cursor has been moved onto a component but 
	 * no buttons have been pushed.
	 * 
	 * @param evt The mouse event that was performed by the user.
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
	 * Invoked by mouseClicked to determine the point whose coordinates are those
	 * of the mouse cursor.
	 * 
	 * @param evt The mouse event that was performed by the user.
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
