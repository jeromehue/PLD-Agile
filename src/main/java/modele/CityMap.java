package modele;

import java.util.ArrayList;

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
	
	
}