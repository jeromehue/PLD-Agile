package modele;

import java.util.ArrayList;
import java.util.Iterator;

public class CityMap {

	private ArrayList<Intersection> intersections;
	private ArrayList<Segment> segments;
	
	public CityMap() {}
	
	public void addIntersection(Intersection inter) {
		intersections.add(inter);
	}
	
	public void addSegment(Segment seg) {
		segments.add(seg);
	}

	public ArrayList<Intersection> getIntersections() {
		return intersections;
	}

	public ArrayList<Segment> getSegments() {
		return segments;
	}
	
	/**
	 * @return an iterator on all Segments in the CityMap
	 */
	public Iterator<Segment> getShapeIterator(){
		return segments.iterator();
	}
	
	
}
