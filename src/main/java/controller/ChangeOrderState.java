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

/**
 * State Reached when 'Modify Order Button is clicked'.
 * @author H4414
 *
 */
public class ChangeOrderState implements State {
	private static final Logger logger = LoggerFactory.getLogger(ChangeOrderState.class);

	public void clickOnStep(Controller c, Window w, ListOfCommands l, Way wa, JButton button, Tour t) {
		logger.info("[Order Modification State] Modify order of tour in controller");
		logger.info("[Order Modification State] Intersection to be modify" + wa.getDeparture());

		logger.info("Modify order of tour in controller");

		Intersection intersection = wa.getDeparture();
		
		int newIndex=-1;
		int nbIntersectionTour = t.getWaysList().size();

		//Si on sélectionne pas le point de départ on ne peut pas changer la position
		if(!intersection.getId().equals(t.getRequest().getStartingLocation().getId())) {
			newIndex = w.displaySelectOrderDialog();
			while(newIndex < 0 || newIndex >= nbIntersectionTour) {
				newIndex = w.displaySelectOrderDialog();
				w.setMessage("You can't select this index.");
			}
		}
		else {
			w.setMessage("You can't change the order of the starting point.");
		}
		
		logger.info("New index : {}", newIndex);
		Tour t1 = w.getGraphicalView().getTour();
		int shift = newIndex - t1.getIndex(intersection.getId());
		CityMap cityMap = w.getGraphicalView().getCityMap();
		Request request = w.getGraphicalView().getRequest();
		Pcc shortestPathComputer = new Pcc(cityMap, request);
		shortestPathComputer.computePcc();

		logger.info("Tour : {} ,intersection : {} ,newIndex : {}", t1, intersection, newIndex);
		l.add(new ChangeOrderCommand(w.getGraphicalView(), shortestPathComputer, t1, intersection, shift));

		c.setCurrentstate(c.tourModificationState);
		
	}

	@Override
	public void modifyTour(Controller c, Window w) {
		w.changeOptionalsButtonsVisibility();
		if (w.isOptionalsButtonsVisible()) {
			c.setCurrentstate(c.tourModificationState);
		} else {
			c.setCurrentstate(c.requestLoadedState);
		}
	}

	@Override
	public void undo(ListOfCommands listOfCdes) {
		listOfCdes.undo();
	}

	@Override
	public void redo(ListOfCommands listOfCdes) {
		listOfCdes.redo();
	}
}
