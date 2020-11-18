package xml;

// From https://mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/ 

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import modele.*;

public class XMLRequestParser extends XMLParser {

	public XMLRequestParser (String filename) {
		super(filename);
	}
	
	public Request parse () /* throws ... */ {

		try {
		
		    List<Intersection> intersections =  new ArrayList<Intersection>(); 	
		    List<Segment> segments = new ArrayList<Segment>();	
		    
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    Document doc = dBuilder.parse(this.file);
		            
		    // Optional, but recommended
		    // Read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		    doc.getDocumentElement().normalize();
		
		    System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		            
		    NodeList nodes = doc.getElementsByTagName("intersection");
		            
		    System.out.println("----------------------------");
		
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
		            
		            intersections.add(intersection);
		        }
		    }
		
		    nodes = doc.getElementsByTagName("segment");
		
		    for (int i = 0; i < nodes.getLength(); ++i) {
		
		        Node node = nodes.item(i);
		                
		        if (node.getNodeType() == Node.ELEMENT_NODE) {
		
		            Element element = (Element) node;
		            
		            Segment segment = new Segment(
		            	Double.parseDouble(element.getAttribute("length")),
						(element.getAttribute("name")), 
						Long.parseLong(element.getAttribute("destination")),
						Long.parseLong(element.getAttribute("origin"))
					);

					segments.add(segment);
		
		        }
		    }

    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
