package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;


public class ButtonListener  implements ActionListener  {
	private static final Logger logger = LoggerFactory.getLogger(ButtonListener.class);
	
	private Controller controller;
	
	public ButtonListener(Controller controller){
		this.controller = controller;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof ButtonWay) {
			
			if (e.getActionCommand().equals(Window.CLICK_STEP)) {
				logger.info("Click on a Step label button");
				controller.clickOnStep(((ButtonWay) e.getSource()).getWay(), ((ButtonWay) e.getSource())); 
			}
			else 
			{ 
				logger.info("Click button: DEFAULT_CASE_1"); 
			}
		}
		else {
			// Méthode appelée par l'ecouteur de boutons a chaque fois qu'un bouton est clique
			// Envoi au controleur du message correspondant au bouton clique
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
			case Window.ADD_REQUEST:
				logger.info("Click on ADD_REQUEST button");
				//controller.addRequest();
				break;
			default: 
				logger.info("Click button: DEFAULT_CASE_2"); 
				break;
			}
		}
	}
}
	
