package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;

/**
 * The button listener of the window, receives button clicks events and calls
 * the controller to update data and view.
 * 
 * @author H4414
 */

public class ButtonListener implements ActionListener {
	private static final Logger logger = LoggerFactory.getLogger(ButtonListener.class);

	private Controller controller;

	/**
	 * Default constructor for this class.
	 * 
	 * @param controller The controller to notify.
	 */
	public ButtonListener(Controller controller) {
		this.controller = controller;
	}

	/**
	 * Call controller after a button event.
	 * 
	 * @param e The action that was performed on the window.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof ButtonWay) {

			if (e.getActionCommand().equals(Window.CLICK_STEP)) {
				logger.info("Click on a Step label button");
				controller.clickOnStep(((ButtonWay) e.getSource()).getWay(), ((ButtonWay) e.getSource()));
			} else {
				logger.info("Click button: DEFAULT_CASE_1");
			}
		} else {
			switch (e.getActionCommand()) {
			case Window.LOAD_REQUEST:
				logger.info("Click on LOAD_REQUEST button");
				controller.loadRequest();
				break;
			case Window.LOAD_MAP:
				logger.info("Click on LOAD_MAP button");
				controller.loadMap();
				break;
			case Window.COMPUTE_TOUR:
				logger.info("Click on COMPUTE_TOUR button");
				controller.computeTour();
				break;
			case Window.MODIFY_TOUR:
				logger.info("Click on MODIFY_TOUR button");
				controller.modifyTour();
				break;
			case Window.QUIT_TOUR_EDITION:
				logger.info("Click on MODIFY_TOUR button");
				controller.modifyTour();
				break;
			case Window.EXIT_MODIFY_TOUR:
				logger.info("Click on EXIT_MODIFY_TOUR button");
				controller.modifyTour();
				break;
			case Window.MODIFY_ORDER:
				logger.info("Click on MODIFY_ORDER button");
				controller.modifyOrder();
				break;
			case Window.REMOVE_REQUEST:
				logger.info("Click on REMOVE_REQUEST button");
				controller.deleteAStep();
				break;
			case Window.UNDO:
				logger.info("Click on UNDO button");
				controller.undo();
				break;
			case Window.REDO:
				logger.info("Click on REDO button");
				controller.redo();
				break;
			case Window.ADD_REQUEST:
				logger.info("Click on ADD_REQUEST button");
				controller.addRequest();
				break;
			default:
				logger.info("Click button: DEFAULT_CASE_2");
				break;
			}
		}
	}
}
