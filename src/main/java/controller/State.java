package controller;

import javax.swing.JButton;

import modele.Point;
import modele.Tour;
import modele.Way;
import view.Window;

/**
 * Interface defining the methods implemented in the different states
 * @author H4414
 */

public interface State {

	/**
	 * Method called by the controller after a click on the "Load XML Map" button
	 * @param c the controller
	 * @param w the window 
	 * @param t the tour
	 */
	public default void loadMap(Controller c, Window w, Tour t) {
	}

	/**
	 * Method called by the controller after a click on the "Load XML Map" button
	 * 
	 * @param c the controller
	 * @param w the window
	 * @param t the tour
	 */
	public default void loadRequest(Controller c, Window w, Tour t) {
	}

	/**
	 * Method called by the controller after a click on the "Compute tour" button
	 * @param c the controller
	 * @param w the window
	 * @param t the tour
	 */
	public default void computeTour(Controller c, Window w, Tour t) {
	}

	/**
	 * Method called by the controller after a click on a step in the textual view panel
	 * @param c the controller
	 * @param w the window
	 * @param l the list of commands
	 * @param wa the way starting from the clicked step
	 * @param button the button clicked on
	 * @param t the tour
	 */
	public default void clickOnStep(Controller c, Window w, ListOfCommands l, Way wa, JButton button, Tour t) {
	}

	/**
	 * Method called by the controller after a click on the "Delete request" button
	 * @param c the controller
	 * @param w the window
	 */
	public default void deleteAStep(Controller c, Window w) {
	}

	/**
	 * Method called by the controller after a click on the "Enter/Exit step" button
	 * 
	 * @param c the controller
	 * @param w the window
	 */
	public default void modifyTour(Controller c, Window w) {
	}

	/**
	 * Method called by the controller after a click on the "Change order" button 
	 * @param c the controller
	 * @param w the window
	 */
	public default void modifyOrder(Controller c, Window w) {
	}

	/**
	 * Method called by the controller after a click on the "Add request" button
	 * @param c the controller
	 * @param w the window
	 */
	public default void addRequest(Controller c, Window w) {
	}

	/**
	 * Method called by the controller after a click on the "Add request" button
	 * @param c the controller	
	 * @param w	the window
	 * @param p the point
	 */
	public default void mouseMoved(Controller c, Window w, Point p) {
	}

	/**
	 * Method called by the controller after a click on the left mouse button
	 * @param p the point
	 * @param c the controller
	 * @param w the window
	 * @param l the list of commands
	 */
	public default void leftClick(Point p, ListOfCommands l, Controller c, Window w) {
	}
	
	/**
	 * Method called by the controller after a click on the button "Undo"
	 * 
	 * @param l the current list of commands
	 */
	public default void undo(ListOfCommands l) {
	};

	/**
	 * Method called by the controller after a click on the button "Redo"
	 * 
	 * @param l the current list of commands
	 */
	public default void redo(ListOfCommands l) {
	}

	
}