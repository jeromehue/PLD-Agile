package controller;

import modele.Way;

import javax.swing.JButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import modele.Intersection;
import modele.Tour;
import view.Window;



public class OrderModificationState implements State {
	private static final Logger logger = LoggerFactory.getLogger(OrderModificationState.class);

	@Override
	public void clickOnStep(Window w, Way wa, JButton button) {
		logger.info("[Order Modification State] Modify order of tour in controller");
		logger.info("[Order Modification State] Intersection to be modify" + wa.getDeparture());
		
		// Testing purposes
		int newIndex = w.displaySelectOrderDialog();
		Intersection intersection = wa.getDeparture();
		Tour t = w.getGraphicalView().getTour();
		//CityMap cityMap = w.graphicalView.getCityMap();
		//Request request = w.graphicalView.getRequest();
		//Pcc shortestPathComputer = new Pcc(cityMap , request);
		//Tour newTour = shortestPathComputer.changeOrder( t,  intersection,  newIndex);
		logger.info("Tour : {} ,intersection : {} ,newIndex : {}", t, intersection, newIndex);
		
	}
}
