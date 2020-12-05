package xml;

// From https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/ 

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;



import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import modele.*;

public class XMLCityMapParser extends XMLParser {
	
	private static final Logger logger = LoggerFactory.getLogger(XMLCityMapParser.class);
	
	public XMLCityMapParser(String filename) {
		super(filename);
	}
	
	public CityMap parse() /* throws ... */ {

		List<Intersection> intersections =  new ArrayList<>(); 	
	    List<Segment> segments = new ArrayList<>();
	    
		try {

		    HashMap<Long, Intersection> mapIntersections = new HashMap<>();
		    
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    
		    // XML specification allows the use of entities that can be internal or external (file system / network access ...).
		    // Allowing access to external entities in XML parsing could lead to vulnerabilities like confidential file disclosures or SSRFs.
		    dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // Compliant
		    dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); // compliant
		    
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    Document doc = dBuilder.parse(this.file);

		    // Optional, but recommended
		    // Read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		    doc.getDocumentElement().normalize();
		
		    NodeList nodes = doc.getElementsByTagName("intersection");
		
		    for (int i = 0; i < nodes.getLength(); ++i) {
		    	
		        Node node = nodes.item(i);
		        
		        if (node.getNodeType() == Node.ELEMENT_NODE) {
		
		            Element element = (Element) node;
		            
		            Intersection intersection = new Intersection(
		            	Long.parseLong(element.getAttribute("id")),
		            	Double.parseDouble((element.getAttribute("latitude"))), 
		            	Double.parseDouble(element.getAttribute("longitude")),
		            	new ArrayList<Segment>()
		            );
		            
		            mapIntersections.put(intersection.getId(), intersection);
		            intersections.add(intersection);
		        }
		    }
		    
		    nodes = doc.getElementsByTagName("segment");
		
		    for (int i = 0; i < nodes.getLength(); ++i) {
		        
		    	Node node = nodes.item(i);
		        
		        if (node.getNodeType() == Node.ELEMENT_NODE) {
		        	
		            Element element = (Element) node;
		            
		            Intersection origin = mapIntersections.get(Long.parseLong(element.getAttribute("origin")));
		            Intersection destination = mapIntersections.get(Long.parseLong(element.getAttribute("destination")));
		            
		            Segment segment = new Segment(
		            	Double.parseDouble(element.getAttribute("length")),
						element.getAttribute("name"),
						origin,
						destination
		            );
		            
		            origin.addOutboundSegment(segment);
					segments.add(segment);
		        }
		    }
			
		} catch (Exception e) {
			logger.error("Erreur in XMLCityMapParser");
			e.printStackTrace();
		}

		return new CityMap(intersections, segments);
	}

}
