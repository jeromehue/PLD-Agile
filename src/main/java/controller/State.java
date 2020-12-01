package controller;

import modele.Tour;
import modele.Way;
import view.Window;

public interface State {

	/**
	 * Méthode appelée lors du chargement de la carte
	 */
	public default void loadMap(Controller c, Window w, Tour t) {}
	
	public default void loadRequest(Controller c, Window w, Tour t) {}
	
	public default void computeTour(Controller c, Window w, Tour t) {}

	public default void highlightWay( Window w, Way wa) {}
}