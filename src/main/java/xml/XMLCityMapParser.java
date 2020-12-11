package xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modele.*;

/**
 * XML Parser for city maps.
 * 
 * @author H4414
 * 
 */

public class XMLCityMapParser extends XMLParser {

	/**
	 * @param filename The file to be parsed.
	 */
	public XMLCityMapParser(String filename) {
		super(filename);
	}

	/**
	 * Parse an XML file containing a map and return the map.
	 * 
	 * @return The citymap that was parsed from the XML file.
	 * @throws ParserConfigurationException indicates at serious configuration error
	 * @throws IOException                  signals that an I/O exception of some
	 *                                      sort has occurred. This class is the
	 *                                      general class of exceptions produced by
	 *                                      failed or interrupted I/O operations.
	 * @throws SAXException                 Encapsulate a general SAX error or
	 *                                      warning.
	 * @throws InvalidMapException          The xml file to be parsed is badly
	 *                                      formed.
	 */
	public CityMap parse() throws ParserConfigurationException, IOException, SAXException, InvalidMapException {

		List<Intersection> intersections = new ArrayList<Intersection>();
		List<Segment> segments = new ArrayList<Segment>();

		HashMap<Long, Intersection> mapIntersections = new HashMap<Long, Intersection>();

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

		// XML specification allows the use of entities that can be internal or external
		// (file system / network access ...).
		// Allowing access to external entities in XML parsing could lead to
		// vulnerabilities like confidential file disclosures or SSRFs.
		dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // Compliant
		dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); // compliant

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(this.file);

		// Optional, but recommended
		// Read this -
		// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

		NodeList nodes = doc.getElementsByTagName("map");

		if (nodes.getLength() != 1) {
			throw new InvalidMapException("Error while parsing XML map file, please load a correct one");
		}

		nodes = doc.getElementsByTagName("intersection");

		if (nodes.getLength() <= 1) {
			throw new InvalidMapException("Error while parsing XML map file, please load a correct one");
		}

		for (int i = 0; i < nodes.getLength(); ++i) {

			Node node = nodes.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) node;

				Intersection intersection = new Intersection(Long.parseLong(element.getAttribute("id")),
						Double.parseDouble((element.getAttribute("latitude"))),
						Double.parseDouble(element.getAttribute("longitude")), new ArrayList<Segment>());

				mapIntersections.put(intersection.getId(), intersection);
				intersections.add(intersection);
			}
		}

		nodes = doc.getElementsByTagName("segment");

		if (nodes.getLength() <= 1) {
			throw new InvalidMapException("Error while parsing XML map file, please load a correct one");
		}

		for (int i = 0; i < nodes.getLength(); ++i) {

			Node node = nodes.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) node;

				Intersection origin = mapIntersections.get(Long.parseLong(element.getAttribute("origin")));
				Intersection destination = mapIntersections.get(Long.parseLong(element.getAttribute("destination")));

				Segment segment = new Segment(Double.parseDouble(element.getAttribute("length")),
						element.getAttribute("name"), origin, destination);

				origin.addOutboundSegment(segment);
				segments.add(segment);
			}
		}

		return new CityMap(intersections, segments);
	}

}
