package controller;

import javax.swing.JButton;

import modele.Point;
import modele.Tour;
import modele.Way;
import view.Window;

public interface State {

	/**
	 * Méthode appelée lors du chargement de la carte
	 */
	public default void loadMap(Controller c, Window w, Tour t) {
	}

	public default void loadRequest(Controller c, Window w, Tour t) {
	}

	public default void computeTour(Controller c, Window w, Tour t) {
	}

	public default void clickOnStep(Controller c, Window w, ListOfCommands l, Way wa, JButton button, Tour t) {
	}

	public default void deleteAStep(Controller c, Window w) {
	}

	public default void modifyTour(Controller c, Window w) {
	}

	public default void modifyOrder(Controller c, Window w) {
	}

	public default void addRequest(Controller c, Window w) {
	}

	public default void mouseMoved(Controller c, Window w, Point p) {
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
	};
}