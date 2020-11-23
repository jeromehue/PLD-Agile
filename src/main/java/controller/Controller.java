package controller;

import modele.CityMap;
import view.Window;

public class Controller {

	private Window window;
	private State currentState;
	protected final InitState initState = new InitState();
	
	public Controller() {
		window = new Window(this);
		currentState = initState;
	}
	
	public void loadMap() {
		currentState.loadMap(this.window);
	}
}
