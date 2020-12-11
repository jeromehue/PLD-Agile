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

	/**
	 * The logger instance, used to log relevant information to the console.
	 */
	private static final Logger logger = LoggerFactory.getLogger(Controller.class);

	/**
	 * The window instance, used to control the interface.
	 */
	private Window window;

	/**
	 * The list of recorded commands, to be able to undo/redo them later.
	 */
	private ListOfCommands l;

	/**
	 * The current state of our application.
	 */
	private State currentState;

	/**
	 * The current Tour object that is being read or edited.
	 */
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

	/**
	 * Constructor to initialize the controller.
	 */
	public Controller() {
		this.l = new ListOfCommands();
		this.tour = new Tour();
		window = new Window(this, tour);
		currentState = initState;
	}

	/**
	 * Sets a new state for the application.
	 * 
	 * @param state
	 */
	protected void setCurrentstate(State state) {
		currentState = state;
		logger.info("Updated state, CURRENT STATE is : " + currentState);
	}

	/**
	 * Loads a map from the file explorer.
	 */
	public void loadMap() {
		currentState.loadMap(this, this.window, this.tour);
	}

	/**
	 * Loads a request from the file explorer.
	 */
	public void loadRequest() {
		currentState.loadRequest(this, this.window, this.tour);
	}

	/**
	 * Computes the tour based on the pick-up and delivery points of the requests.
	 */
	public void computeTour() {
		currentState.computeTour(this, this.window, this.tour);
	}

	/**
	 * Method triggered when the user clicks on a step in the textual view. It is
	 * used to highlight it and show the corresponding step on the graphical view.
	 * 
	 * @param way    The way object that should be highlighted
	 * @param button The button the click is coming from
	 */
	public void clickOnStep(Way way, JButton button) {
		currentState.clickOnStep(this, this.window, l, way, button, this.tour);
	}

	/**
	 * Swtiches the order of two steps in the tour.
	 */
	public void modifyOrder() {
		currentState.modifyOrder(this, this.window);
	}

	/**
	 * Deletes a step from the tour.
	 */
	public void deleteAStep() {
		currentState.deleteAStep(this, this.window);
	}

	/**
	 * Enter the "edit tour" mode.
	 */
	public void modifyTour() {
		currentState.modifyTour(this, this.window);
	}

	/**
	 * Adds a request (pick-up & delivery) to the tour.
	 */
	public void addRequest() {
		currentState.addRequest(this, this.window);

	}

	/**
	 * Method called when the mouse has been moved on the graphical view.
	 * 
	 * @param p The Point object representing the mouse position
	 */
	public void mouseMoved(Point p) {
		currentState.mouseMoved(this, this.window, p);
	}

	/**
	 * Undo the last command.
	 */
	public void undo() {
		currentState.undo(l);
	}

	/**
	 * Redo the last command.
	 */
	public void redo() {
		currentState.redo(l);
	}

	/**
	 * Method called when a left clicked has occured on the graphical view.
	 * 
	 * @param p The Point object representing the mouse position
	 * @param w The window instance
	 */
	public void leftClick(Point p, Window w) {
		currentState.leftClick(p, l, this, w);
	}

	/**
	 * Method called when a right clicked has occured on the graphical view.
	 * 
	 * @param p The Point object representing the mouse position
	 * @param w The window instance
	 */
	public void rightClick() {
	}

}
