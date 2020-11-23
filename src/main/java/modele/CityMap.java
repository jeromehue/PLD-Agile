package modele;

import java.util.Iterator;
import java.util.List;

public class CityMap {

	private List<Intersection> intersections;
	private List<Segment> segments;
	private double minLatitude;
	private double minLongitude;
	private double maxLatitude;
	private double maxLongitude;
	
	public CityMap(List<Intersection> intersections, List<Segment> segments) {
		this.intersections = intersections;
		this.segments = segments;
		findMinMax();
	}
	
	public void addIntersection(Intersection inter) {
		intersections.add(inter);
	}
	
	public void addSegment(Segment seg) {
		segments.add(seg);
	}

	public List<Intersection> getIntersections() {
		return intersections;
	}

	public List<Segment> getSegments() {
		return segments;
	}
	
	/**
	 * @return an iterator on all Segments in the CityMap
	 */
	public Iterator<Segment> getSegementsIterator(){
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
