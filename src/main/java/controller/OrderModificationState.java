package controller;

import modele.Way;

import javax.swing.JButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import algo.Pcc;
import modele.CityMap;
import modele.Intersection;
import modele.Request;
import modele.Tour;
import view.Window;



public class OrderModificationState implements State {
	private static final Logger logger = LoggerFactory.getLogger(OrderModificationState.class);

	public void clickOnStep(Controller c, Window w, ListOfCommands l, Way wa, JButton button, Tour t) {
		logger.info("[Order Modification State] Modify order of tour in controller");
		logger.info("[Order Modification State] Intersection to be modify" + wa.getDeparture());
		
		// Testing purposes
		int newIndex = w.displaySelectOrderDialog();
		Intersection intersection = wa.getDeparture();

		Tour t1 = w.getGraphicalView().getTour(); 
		CityMap cityMap = w.getGraphicalView().getCityMap();
		Request request = w.getGraphicalView().getRequest();
		Pcc shortestPathComputer = new Pcc(cityMap , request);
		shortestPathComputer.computePcc();
		
		logger.info("Tour : {} ,intersection : {} ,newIndex : {}", t1, intersection, newIndex);
		l.add(new ChangeOrderCommand(shortestPathComputer, t1, intersection, newIndex));
		logger.info("Tour : {} ,intersection : {} ,newIndex : {}", t1, intersection, newIndex);
		
		c.setCurrentstate(c.tourModificationState);
	}

	@Override
	public void undo(ListOfCommands listOfCdes){
		listOfCdes.undo();
	}

	@Override
	public void redo(ListOfCommands listOfCdes){
		listOfCdes.redo();
	}
}
