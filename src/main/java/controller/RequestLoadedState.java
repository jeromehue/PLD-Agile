package controller;

import java.util.List;
import java.io.IOException;

import javax.swing.JButton;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import algo.Pcc;
import modele.CityMap;
import modele.Point;
import modele.Request;
import modele.Segment;
import modele.Tour;
import modele.Way;
import view.GraphicalView;
import view.Window;
import xml.InvalidMapException;
import xml.InvalidRequestException;
import xml.XMLCityMapParser;
import xml.XMLRequestParser;

/**
 *  State reached after a request is loaded.
 *  @author H4414
 */

public class RequestLoadedState implements State {

	private static final Logger logger = LoggerFactory.getLogger(XMLRequestParser.class);

	@Override
	public void loadMap(Controller c, Window w, Tour t) {

		String path = w.createDialogBoxToGetFilePath();
		w.getGraphicalView().setHighlightedWay(null);
		if (path != null) {
			try {
				XMLCityMapParser p = new XMLCityMapParser(path);
				CityMap cityMap = p.parse();
				w.getGraphicalView().setCityMap(cityMap);
				c.setCurrentstate(c.mapLoadedState);
				t.clearTour();
				w.getGraphicalView().setRequest(null);
			} catch (InvalidMapException e) {
				w.setMessage("A problem occurred while trying to load the map file.");
				logger.error("Error while trying to load the map file because the file is not correct.");
			} catch (ParserConfigurationException e) {
				w.setMessage("A problem occurred while trying to load the map file.");
				logger.error("Error while trying to load the map file because of the parser configuration.");
			} catch (SAXException e) {
				w.setMessage("A problem occurred while trying to load the map file.");
				logger.error("Error while trying to load the map file because of the XML parser.");
			} catch (IOException e) {
				w.setMessage("A problem occurred while trying to load the map file.");
				logger.error("Error while trying to load the map file because of a I/O problem.");
			}

		} else {
			w.setMessage("You can load a new map, load a requests file or compute the tour.");
		}
	}

	@Override
	public void loadRequest(Controller c, Window w, Tour t) {
		String path = w.createDialogBoxToGetFilePath();
		w.getGraphicalView().setHighlightedWay(null);
		if (path != null) {
			try {
				t.clearTour();
				XMLRequestParser p = new XMLRequestParser(path, w.getGraphicalView().getCityMap());
				Request request = p.parse();
				w.getGraphicalView().setRequest(request);
				c.setCurrentstate(c.requestLoadedState);
				w.setMessage("The requests were successfully loaded. You may now compute the tour.");
			} catch (InvalidRequestException e) {
				w.setMessage("A problem occurred while trying to load the requests file.");
				logger.error(
						"Error while trying to load the request file because of invalid requests or incorrect file.");
			} catch (ParserConfigurationException e) {
				w.setMessage("A problem occurred while trying to load the requests file.");
				logger.error("Error while trying to load the request file because of the parser configuration.");
			} catch (SAXException e) {
				w.setMessage("A problem occurred while trying to load the requests file.");
				logger.error("Error while trying to load the request file because of the XML parser.");
			} catch (IOException e) {
				w.setMessage("A problem occurred while trying to load the requests file.");
				logger.error("Error while trying to load the request file because of a I/O problem.");
			}
		} else {
			w.setMessage("Please load a XML file.");
		}

	}

	@Override
	public void computeTour(Controller c, Window w, Tour t) {
		// Modify the tour
		Pcc shortestPathComputer = new Pcc(w.getGraphicalView().getCityMap(), w.getGraphicalView().getRequest());
		shortestPathComputer.computePcc();

		Tour t2 = shortestPathComputer.computeGooodTSPTour();
		t.setTour(t2);
		t.notifyObservers();

		w.getGraphicalView().setHighlightedWay(null);
		w.setMessage("You can modify the tour with the tour edition mode.");
	}

	@Override
	public void clickOnStep(Controller c, Window w, ListOfCommands l, Way wa, JButton button, Tour t) {
		w.getTextualView().clearAllTextArea();
		button.setContentAreaFilled(true);
		w.getGraphicalView().setHighlightedWay(wa);
	}

	@Override
	public void modifyTour(Controller c, Window w) {
		w.changeOptionalsButtonsVisibility();
		if (w.isOptionalsButtonsVisible()) {
			w.getGraphicalView().setHighlightedWay(null);
			c.setCurrentstate(c.tourModificationState);
		} else {
			c.setCurrentstate(c.requestLoadedState);
		}
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
					// System.out.println("distance : " +distance);
					sclosest = s;
				}
			}
			if (sclosest != null) {
				graphicalView.highlight(sclosest);
				w.setStreet(sclosest.getName());
			}
		}
	}

}
