package modele;

import java.util.ArrayList;
import java.util.Iterator;

public class CityMap {

	private ArrayList<Intersection> intersections;
	private ArrayList<Segment> segments;
	private double minLatitude;
	private double minLongitude;
	private double maxLatitude;
	private double maxLongitude;
	
	public CityMap() {
		findMinMax();
	}
	
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
	
	public double getMinLatitude() {
		return minLatitude;
	}

	public double getMinLongitude() {
		return minLongitude;
	}

	public double getMaxLatitude() {
		return maxLatitude;
	}


	public double getMaxLongitude() {
		return maxLongitude;
	}

	private void findMinMax()
	{
		 minLatitude = intersections.get(0).getLatitude();
		 minLongitude = intersections.get(0).getLongitude();
		 maxLatitude = intersections.get(0).getLatitude();
		 maxLongitude = intersections.get(0).getLongitude();
		
		for(Intersection i: intersections)
		{
			if(minLatitude > i.getLatitude())
			{
				minLatitude = i.getLatitude();
			}
			if(minLongitude > i.getLongitude())
			{
				minLongitude = i.getLongitude();
			}
			if(maxLatitude < i.getLatitude())
			{
				maxLatitude = i.getLatitude();
			}
			if(maxLongitude < i.getLongitude())
			{
				maxLongitude = i.getLongitude();
			}
		}
	}
	
}
