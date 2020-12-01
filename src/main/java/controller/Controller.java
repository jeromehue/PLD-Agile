package controller;

import modele.Tour;
import modele.Way;
import view.Window;


public class Controller {
	
	private Window window;
	
	//States
	private State currentState;
	private Tour tour;
	protected final InitState initState = new InitState();
	protected final MapLoadedState mapLoadedState = new MapLoadedState();
	protected final RequestLoadedState requestLoadedState = new RequestLoadedState();
	
	public Controller() {
		this.tour = new Tour();
		window = new Window(this, tour);
		currentState = initState;
	}
	
	protected void setCurrentstate(State state){
		currentState = state;
		System.out.println("Updated state, CURRENT STATE is : "+ currentState);
	}
	
	public void loadMap() {
		currentState.loadMap(this, this.window, this.tour);
		
	}
	
	public void loadRequest() {
		currentState.loadRequest(this, this.window, this.tour);
	}
	
	public void computeTour() {
		currentState.computeTour(this, this.window, this.tour);
	}
	
	public void highlightWay(Way w) {
		currentState.highlightWay(this.window, w);
	}

}
