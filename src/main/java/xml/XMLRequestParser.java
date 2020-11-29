package xml;

// From https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/ 

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
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
		    String 					startingTime 		= new String();
		    ArrayList<Integer> 		pickUpDurations   	= new ArrayList<>();
		    ArrayList<Integer>		deliveryDurations 	= new ArrayList<>();
		    
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
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
		            startingTime = element.getAttribute("departureTime");

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
		    
		} catch (Exception e) {
			System.out.println("Erreur lors de la récupération du fichier XML de requête : ");
			e.printStackTrace();
			return (null);
		}		
	}

}
