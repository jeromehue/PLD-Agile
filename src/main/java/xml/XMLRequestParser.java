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
import java.time.LocalTime;
import java.util.ArrayList;

import modele.*;

/**
 * XML Parser for requests. From
 * https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 * 
 * @author H4414
 * 
 */

public class XMLRequestParser extends XMLParser {

	private CityMap cityMap;

	/**
	 * Default constructor for this class.
	 * 
	 * @param filename The file to be parsed.
	 * @param cityMap  The CityMap object that contains all segments and
	 *                 intersections.
	 */
	public XMLRequestParser(String filename, CityMap cityMap) {
		super(filename);
		this.cityMap = cityMap;
	}

	/**
	 * Parse an XML file containing a request ( a starting point, a list of pickup
	 * and delivery points and delivery and pickup duration for each point ) and
	 * return the map.
	 * 
	 * @return The citymap that was parsed from the XML file.
	 * @throws ParserConfigurationException Indicates a serious configuration error.
	 * @throws IOException                  Signals that an I/O exception of some
	 *                                      sort has occurred. This class is the
	 *                                      general class of exceptions produced by
	 *                                      failed or interrupted I/O operations.
	 * @throws SAXException                 Encapsulate a general SAX error or
	 *                                      warning.
	 * @throws InvalidRequestException      The xml file to be parsed is badly
	 *                                      formed.
	 */
	public Request parse() throws InvalidRequestException, ParserConfigurationException, SAXException, IOException {

		ArrayList<Intersection> pickUpLocations = new ArrayList<Intersection>();
		ArrayList<Intersection> deliveryLocations = new ArrayList<Intersection>();
		Intersection startingLocation = null;
		LocalTime startingTime = null;
		ArrayList<Integer> pickUpDurations = new ArrayList<Integer>();
		ArrayList<Integer> deliveryDurations = new ArrayList<Integer>();

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
		// http://stackoverflow.com/questions/13786607/
		doc.getDocumentElement().normalize();

		NodeList nodes = doc.getElementsByTagName("depot");

		if (nodes.getLength() != 1) {
			throw new InvalidRequestException("Error while parsing XML request file, please load a correct one");
		}

		for (int i = 0; i < nodes.getLength(); ++i) {

			Node node = nodes.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) node;
				Long startingLocationId = Long.parseLong(element.getAttribute("address"));
				startingLocation = this.cityMap.getIntersectionFromId(startingLocationId);

				String[] departureTime = element.getAttribute("departureTime").split(":", 3);
				Integer hour = Integer.parseInt(departureTime[0]);
				Integer minute = Integer.parseInt(departureTime[1]);
				Integer second = Integer.parseInt(departureTime[2]);
				startingTime = LocalTime.of(hour, minute, second);

				if (startingLocation == null) {
					throw new InvalidRequestException("The request contains unknown intersections");
				}
			}
		}

		nodes = doc.getElementsByTagName("request");

		if (nodes.getLength() == 0) {
			throw new InvalidRequestException("Error while parsing XML request file, please load a correct one");
		}

		for (int i = 0; i < nodes.getLength(); ++i) {

			Node node = nodes.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) node;

				Long pickUpLocationId = Long.parseLong(element.getAttribute("pickupAddress"));
				Intersection pickUpLocation = this.cityMap.getIntersectionFromId(pickUpLocationId);
				pickUpLocations.add(pickUpLocation);

				Long deliveryLocationId = Long.parseLong(element.getAttribute("deliveryAddress"));
				Intersection deliveryLocation = this.cityMap.getIntersectionFromId(deliveryLocationId);
				deliveryLocations.add(deliveryLocation);

				if (pickUpLocation == null && deliveryLocation == null) {
					throw new InvalidRequestException("The request contains unknown intersections");
				}

				pickUpDurations.add(Integer.parseInt(element.getAttribute("pickupDuration")));
				deliveryDurations.add(Integer.parseInt(element.getAttribute("deliveryDuration")));
			}
		}

		Request r = new Request(startingLocation, startingTime, pickUpDurations, deliveryDurations, pickUpLocations,
				deliveryLocations);
		return (r);
	}

}
