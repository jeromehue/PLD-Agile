package controller;

import java.util.List;

import javax.swing.JButton;

import modele.Intersection;
import modele.Point;
import modele.Tour;
import modele.Way;
import view.GraphicalView;
import view.Window;

public class TourModificationState implements State {

	@Override
	public void clickOnStep(Controller c, Window w, ListOfCommands l, Way wa, JButton button, Tour t) {
		w.setMessage("Choose a modification option before selecting a step.");
	}

	@Override
	public void modifyOrder(Controller c, Window w) {
		w.setMessage("Choose which step to modify order");
		c.setCurrentstate(c.orderModificationState);
	}

	public void addRequest(Controller c, Window w) {
		w.setMessage("Select the pickup intersection on the map");
		c.setCurrentstate(c.addRequestState);
		//Tour addRequest (Tour tour, Intersection pickup, Intersection delivery, Integer pickUpDuration, Integer deliveryDuration,
		//Integer pickupIndex, Integer deliveryIndex)
		// Tour addRequest (Tour tour, Intersection pickup, Intersection delivery,
		// Integer pickUpDuration, Integer deliveryDuration,
		// Integer pickupIndex, Integer deliveryIndex)
	}

	@Override
	public void mouseMoved(Controller c, Window w, Point p) {
		GraphicalView graphicalView = w.getGraphicalView();
		if (graphicalView.getCityMap() != null) {
			List<Intersection> intersections = graphicalView.getCityMap().getIntersections();
			float mindist = (float) 100;
			int floor = 1;
			Intersection iclosest = null;
			for (Intersection i : intersections) {
				int distance = p.distanceWithCoordinates(i.getCoordinates().getX(), i.getCoordinates().getY());
				if (distance < mindist) {
					mindist = distance;
					// System.out.println("distance : " +distance);
					iclosest = i;
					if (mindist < floor)
						break;
				}
			}
			if (iclosest != null) {
				graphicalView.setHighlightInter(iclosest);
				w.setMessage("truc");
			}
		}
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
	public void deleteAStep(Controller c, Window w) {
		w.setMessage("Choose a step to delete");
		c.setCurrentstate(c.deleteStepState);
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
