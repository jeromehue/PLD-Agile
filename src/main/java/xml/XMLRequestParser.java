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

	public XMLRequestParser(String filename) {
		super(filename);
	}
	
	public Request parse() /* throws ... */ {
	
		try {
			
			
		
			ArrayList<Long> 		pickUpLocations   	= new ArrayList<>();
			ArrayList<Long> 		deliveryLocations 	= new ArrayList<>();
		    Long 					startingLocation	= (long) 0;
		    String 					startingTime 		= new String();
		    ArrayList<Integer> 	pickUpDurations   		= new ArrayList<>();
		    ArrayList<Integer>	deliveryDurations 		= new ArrayList<>();
		    
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    Document doc = dBuilder.parse(this.file);
		            
		    // Optional, but recommended
		    // Read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		    doc.getDocumentElement().normalize();
		
		    //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		            
		    NodeList nodes = doc.getElementsByTagName("depot");
		            
		    //System.out.println("----------------------------");
		
		    for (int i = 0; i < nodes.getLength(); ++i) {
		
		        Node node = nodes.item(i);
		                
		        if (node.getNodeType() == Node.ELEMENT_NODE) {
		
		            Element element = (Element) node;
		            
		            startingLocation  = Long.parseLong(element.getAttribute("address"));
		            startingTime = element.getAttribute("departureTime");
		        }
		    }
		    
		    nodes = doc.getElementsByTagName("request"); 
		    
		    for (int i=0; i < nodes.getLength(); ++i){
		    	
		    	Node node = nodes.item(i);
		    	
		        if (node.getNodeType() == Node.ELEMENT_NODE) { 
		        	
		        	Element element = (Element) node;
		        	
		        	
		            pickUpLocations.add(Long.parseLong(element.getAttribute("pickupAddress")));
		            deliveryLocations.add(Long.parseLong(element.getAttribute("deliveryAddress")));
		            pickUpDurations.add(Integer.parseInt(element.getAttribute("pickupDuration")));
		            deliveryDurations.add(Integer.parseInt(element.getAttribute("deliveryDuration")));
		            
		        }
		    	
	            
		    	
		    }
		
		    Request r = new Request(startingLocation, startingTime, pickUpDurations,
					 deliveryDurations,  pickUpLocations,  deliveryLocations);
		    return (r);
		    
		} catch (Exception e) {
			System.out.println("Erreur lors de la récupération du fichier XML de requête : ");
			e.printStackTrace();
			return (null);
		}
		//return (new Request());
		
		
		
	}

}
