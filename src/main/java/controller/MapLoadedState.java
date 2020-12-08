package controller;

import java.util.List;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import modele.CityMap;
import modele.Point;
import modele.Request;
import modele.Segment;
import modele.Tour;
import view.GraphicalView;
import view.Window;
import xml.InvalidMapException;
import xml.InvalidRequestException;
import xml.XMLCityMapParser;
import xml.XMLRequestParser;

/**
 * State reached from InitState when a map is loaded.
 * @author H4414
 */
public class MapLoadedState implements State {

	private static final Logger logger = LoggerFactory.getLogger(XMLRequestParser.class);

	@Override
	public void loadMap(Controller c, Window w, Tour t) {

		String path = w.createDialogBoxToGetFilePath();
		if (path != null) {
			try {
				XMLCityMapParser p = new XMLCityMapParser(path);
				CityMap cityMap = p.parse();
				w.getGraphicalView().setCityMap(cityMap);
				c.setCurrentstate(c.mapLoadedState);
				w.setMessage("The map was successfully loaded. You may now load requests.");
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
			w.setMessage("Please load a XML file.");
		}
	}

	@Override
	public void loadRequest(Controller c, Window w, Tour t) {
		String path = w.createDialogBoxToGetFilePath();
		if (path != null) {
			try {
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
		w.setMessage("Before trying to compute a tour, please load a requests file.");
	}

	@Override
	public void modifyTour(Controller c, Window w) {
		w.setMessage("Before trying to modify a tour, please load a map and a requests file, then compute the Tour");
	}

	@Override
	public void mouseMoved(Controller c, Window w, Point p) {
		GraphicalView graphicalView = w.getGraphicalView();
		if (graphicalView.getCityMap() != null) {
			List<Segment> allsegments = graphicalView.getCityMap().getSegments();
			float mindist = (float) 0.5;
			Segment sclosest = null;
			int x1;
			int y1;
			int x2;
			int y2;
			for (Segment s : allsegments) {
				try {
					x1 = s.getOrigin().getCoordinates().getX();
					y1 = s.getOrigin().getCoordinates().getY();
					x2 = s.getDestination().getCoordinates().getX();
					y2 = s.getDestination().getCoordinates().getY();
				} catch (Exception e) {
					logger.info("Error in mouseMoved method");
					break;
				}
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
