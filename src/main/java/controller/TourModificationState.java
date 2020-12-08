package controller;

import java.util.List;

import javax.swing.JButton;

import modele.Point;
import modele.Segment;
import modele.Tour;
import modele.Way;
import view.GraphicalView;
import view.Window;


/**
 * State reached when clicking on the 'Edit-> Enter the tour edition mode' 
 * button, or after a modification is successfully performed.
 * @author H4414
 */
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

	@Override
	public void addRequest(Controller c, Window w) {
		w.setMessage("Select the pickup intersection on the map");
		c.setCurrentstate(c.addRequestState);
	}

	@Override
	public void mouseMoved(Controller c, Window w, Point p) {
		GraphicalView graphicalView = w.getGraphicalView();
		if (graphicalView.getCityMap() != null) {
			List<Segment> allsegments = graphicalView.getCityMap().getSegments();
			float mindist = (float) 0.5;
			Segment sclosest = null;
			for (Segment s : allsegments) {
				int x1 = s.getOrigin().getCoordinates().getX();
				int y1 = s.getOrigin().getCoordinates().getY();
				int x2 = s.getDestination().getCoordinates().getX();
				int y2 = s.getDestination().getCoordinates().getY();
				float distance = (float) 1.1;
				if (p.inBox(x1, y1, x2, y2)) {
					distance = p.distBetweenPointAndLine(x1, y1, x2, y2);
				}
				if (distance < mindist) {
					mindist = distance;
					sclosest = s;
				}
			}
			if (sclosest != null) {
				graphicalView.highlight(sclosest);
				w.setStreet(sclosest.getName());
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
