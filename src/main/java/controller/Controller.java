package controller;

import modele.CityMap;
import view.Window;


public class Controller {
	
	private Window window;
	
	//States
	private State currentState;
	protected final InitState initState = new InitState();
	protected final MapLoadedState mapLoadedState = new MapLoadedState();
	protected final RequestLoadedState requestLoadedState = new RequestLoadedState();
	
	public Controller() {
		window = new Window(this);
		currentState = initState;
	}
	
	protected void setCurrentstate(State state){
		currentState = state;
		System.out.println("Updated state, CURRENT STATE is : "+ currentState);
	}
	
	public void loadMap() {
		currentState.loadMap(this, this.window);
		
	}
	
	public void loadRequest() {
		currentState.loadRequest(this, this.window);
	}

}
