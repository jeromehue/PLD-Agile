package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import xml.XMLCityMapParser;

public class ButtonListener  implements ActionListener  {
	private static final Logger logger = LoggerFactory.getLogger(ButtonListener.class);
	
	private Controller controller;
	
	public ButtonListener(Controller controller){
		this.controller = controller;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof ButtonWay) {
			switch (e.getActionCommand()) {
			case Window.CLICK_STEP: System.out.println("Click on a Step label button");
			controller.clickOnStep(((ButtonWay) e.getSource()).getWay(), ((ButtonWay) e.getSource())); break;
			default: System.out.println("Click button: DEFAULT_CASE_1"); break;
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
				controller.changeOptionalsButtonsVisibility();
				break;
			case Window.MODIFY_ORDER: 
				System.out.println("Click on MODIFY_ORDER button");
				controller.modifyOrder();
				break;
			default: System.out.println("Click button: DEFAULT_CASE_2"); break;
			}
		}
	}
}
	
