package controller;

import modele.CityMap;
import view.Window;
//import xml.XMLCityMapParser;

public class Controller {
	private CityMap cityMap;
	public CityMap getCityMap() {
		return cityMap;
	}
	private Window window;
	private State currentState;
	protected final InitState initState = new InitState();
	
	public Controller() {
		window = new Window(this);
		currentState = initState;
	}
	
	public void loadMap() {
		currentState.loadMap(this, this.window);	
	}
	
	public void loadRequest() {
		currentState.loadRequest(this, this.window);
	}

}
