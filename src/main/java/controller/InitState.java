package controller;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import modele.CityMap;
import modele.Tour;
import view.Window;
import xml.InvalidMapException;
import xml.XMLCityMapParser;
import xml.XMLRequestParser;

public class InitState implements State {

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
		w.setMessage("Before trying to load a requests file, please load a map.");
	}

	@Override
	public void computeTour(Controller c, Window w, Tour t) {
		w.setMessage("Before trying to compute a tour, please load a map and a requests file.");
	}

	public void modifyTour(Controller c, Window w) {
		w.setMessage("Before trying to modify a tour, please load a map and a requests file, then compute the Tour");
	}
}
