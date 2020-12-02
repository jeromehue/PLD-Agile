package xml;

// From https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/ 

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

public class XMLRequestParser extends XMLParser {

	private CityMap cityMap;
	
	public XMLRequestParser(String filename, CityMap cityMap) {
		super(filename);
		this.cityMap = cityMap;
	}
	
	public Request parse() throws InvalidRequestException {
		try {
			ArrayList<Intersection> pickUpLocations   	= new ArrayList<>();
			ArrayList<Intersection> deliveryLocations 	= new ArrayList<>();
		    Intersection			startingLocation	= null;
		    LocalTime 				startingTime 		= null;
		    ArrayList<Integer> 		pickUpDurations   	= new ArrayList<>();
		    ArrayList<Integer>		deliveryDurations 	= new ArrayList<>();
		    
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    
		    // XML specification allows the use of entities that can be internal or external (file system / network access ...).
		    // Allowing access to external entities in XML parsing could lead to vulnerabilities like confidential file disclosures or SSRFs.
		    dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // Compliant
		    dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); // compliant
		    
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    Document doc = dBuilder.parse(this.file);
		            
		    // Optional, but recommended
		    // http://stackoverflow.com/questions/13786607/
		    doc.getDocumentElement().normalize();
				            
		    NodeList nodes = doc.getElementsByTagName("depot");
		
		    for (int i = 0; i < nodes.getLength(); ++i) {
		
		        Node node = nodes.item(i);
		                
		        if (node.getNodeType() == Node.ELEMENT_NODE) {
		
		            Element element = (Element) node;
		            Long startingLocationId = Long.parseLong(element.getAttribute("address"));
		            startingLocation = this.cityMap.getIntersectionFromAddress(startingLocationId);
		            
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
		    
		    for (int i=0; i < nodes.getLength(); ++i){
		    	
		    	Node node = nodes.item(i);
		    	
		        if (node.getNodeType() == Node.ELEMENT_NODE) { 
		        	
		        	Element element = (Element) node;
		        	
		        	Long pickUpLocationId = Long.parseLong(element.getAttribute("pickupAddress"));
		        	Intersection pickUpLocation = this.cityMap.getIntersectionFromAddress(pickUpLocationId);
		            pickUpLocations.add(pickUpLocation);
		            
		            Long deliveryLocationId = Long.parseLong(element.getAttribute("deliveryAddress"));
		        	Intersection deliveryLocation = this.cityMap.getIntersectionFromAddress(deliveryLocationId);
		            deliveryLocations.add(deliveryLocation);
		            
		            if (pickUpLocation == null && deliveryLocation == null) {
		            	throw new InvalidRequestException("The request contains unknown intersections");
		            }
		            
		            pickUpDurations.add(Integer.parseInt(element.getAttribute("pickupDuration")));
		            deliveryDurations.add(Integer.parseInt(element.getAttribute("deliveryDuration")));
		        }		    	
		    }
		
		    Request r = new Request(startingLocation, startingTime, pickUpDurations, deliveryDurations,  pickUpLocations,  deliveryLocations);
		    return (r);
		    
		} catch (InvalidRequestException e) {
			System.out.println("Erreur lors de la récupération du fichier XML de requête : ");
			e.printStackTrace();
			throw e;
			//return (null);
		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
			return (null);
		} catch (SAXException e) {
			
			e.printStackTrace();
			return (null);
		} catch (IOException e) {
			
			e.printStackTrace();
			return (null);
		}		
	}

}
