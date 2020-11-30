package modele;

import java.util.Iterator;
import java.util.List;

import view.GraphicalView;

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
	
	public void setIntersectionCordinates(GraphicalView graphicalView) {
		PointFactory.initPointFactory(graphicalView, this);
		Iterator<Intersection> itIntersections = getIntersectionsIterator();
		while(itIntersections.hasNext()) {
			Intersection intersection = itIntersections.next();
			Point coordinates = PointFactory.createPoint(intersection);
			intersection.setCoordinates(coordinates);
		}
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
	public Iterator<Segment> getSegmentsIterator(){
		return segments.iterator();
	}
	
	/**
	 * @return an iterator on all Intersections in the CityMap
	 */
	public Iterator<Intersection> getIntersectionsIterator(){
		return intersections.iterator();
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
	public Intersection getIntersectionFromAddress(Long address) {
		for(Intersection intersection : intersections)
		{
			if(address.equals(intersection.getId())) 
			{
				return intersection;
			}
		}
		return null;	
	}
}
