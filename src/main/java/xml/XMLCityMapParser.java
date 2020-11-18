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

public class ReadXMLMap {

  public static void main(String argv[]) {

    try {

    List<Intersection> intersections =  new ArrayList<Intersection>(); 	
    List<Segment> segments = new ArrayList<Segment>();	
    
    // Put the correct path here
    File fXmlFile = new File("src/main/resources/smallMap.xml");
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(fXmlFile);
            
    //optional, but recommended
    //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
    doc.getDocumentElement().normalize();

    System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            
    NodeList nList = doc.getElementsByTagName("intersection");
            
    System.out.println("----------------------------");

    for (int temp = 0; temp < nList.getLength(); temp++) {

        Node nNode = nList.item(temp);
                
        //System.out.println("\nCurrent Element :" + nNode.getNodeName());
                
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

            Element eElement = (Element) nNode;
            
            //System.out.println("intersection id = " + eElement.getAttribute("id"));
            Intersection inter = new Intersection(  Long.parseLong(  eElement.getAttribute("id")),
            										Double.parseDouble((eElement.getAttribute("latitude"))), 
            										Double.parseDouble(eElement.getAttribute("longitude")));
            System.out.println(inter);
            intersections.add(inter);
 
        }
    }
    
   

    nList = doc.getElementsByTagName("segment");

    for (int temp = 0; temp < nList.getLength(); temp++) {

        Node nNode = nList.item(temp);
                
        //System.out.println("\nCurrent Element :" + nNode.getNodeName());
                
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

            Element eElement = (Element) nNode;
            
            //System.out.println("length = " + eElement.getAttribute("length"));
            Segment seg = new Segment( Double.parseDouble(  eElement.getAttribute("length")),
											(eElement.getAttribute("name")), 
											Long.parseLong(eElement.getAttribute("destination")),
											Long.parseLong(eElement.getAttribute("origin")));
			System.out.println(seg);
			segments.add(seg);

        }
    }

    
    } catch (Exception e) {
    e.printStackTrace();
    }
  }

}
