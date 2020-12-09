package controller;

//import java.util.ArrayList;

import javax.swing.JButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modele.Point;
import modele.Tour;
import modele.Way;
import view.Window;

/**
 * Classe that call the methods defined in the State interface.
 */
public class Controller {
	private static final Logger logger = LoggerFactory.getLogger(Controller.class);

	private Window window;

	private ListOfCommands l;
	private State currentState;
	private Tour tour;

	// States
	protected final InitState initState = new InitState();
	protected final MapLoadedState mapLoadedState = new MapLoadedState();
	protected final RequestLoadedState requestLoadedState = new RequestLoadedState();
	protected final TourModificationState tourModificationState = new TourModificationState();
	protected final ChangeOrderState orderModificationState = new ChangeOrderState();
	protected final DeleteStepState deleteStepState = new DeleteStepState();
	protected final AddRequestState addRequestState = new AddRequestState();
	protected final AddRequestState2 addRequestState2 = new AddRequestState2();
	

	public Controller() {
		this.l = new ListOfCommands();
		this.tour = new Tour();
		window = new Window(this, tour);
		currentState = initState;
	}

	protected void setCurrentstate(State state) {
		currentState = state;
		logger.info("Updated state, CURRENT STATE is : " + currentState);
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

	public void clickOnStep(Way way, JButton button) {
		currentState.clickOnStep(this, this.window, l, way, button, this.tour);
	}

	public void modifyOrder() {
		currentState.modifyOrder(this, this.window);
	}

	public void deleteAStep() {
		currentState.deleteAStep(this, this.window);
	}

	public void modifyTour() {
		currentState.modifyTour(this, this.window);
	}

	public void addRequest() {
		currentState.addRequest(this, this.window);

	}

	public void mouseMoved(Point p) {
		// System.out.println("Ici");
		currentState.mouseMoved(this, this.window, p);
	}

	public void undo() {
		currentState.undo(l);
	}

	public void redo() {
		currentState.redo(l);
	}

	public void leftClick(Point p, Window w) {
		currentState.leftClick(p, l, this, w);
	}

	public void rightClick() {
		// TODO Auto-generated method stub	
	}

	

}
