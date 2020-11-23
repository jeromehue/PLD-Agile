package controller;

import view.Window;

public interface State {

	/**
	 * Méthode appelée lors du chargement de la carte
	 */
	public default void loadMap(Window w) {}
}
