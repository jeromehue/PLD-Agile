package modele;

import java.util.ArrayList;
import java.util.List;

public class Intersection{

    private Long id;
    private double latitude;
    private double longitude;
    private ArrayList<Segment> outboundSegments;
    private Point coordinates;

    public Intersection(Long Id, double latitude, double longitude, ArrayList<Segment> segments) {

        this.id = Id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.coordinates = null;
        if (segments != null) {
        	this.outboundSegments = new ArrayList<Segment>(segments);
        }
        else
        {
        	this.outboundSegments = new ArrayList<Segment>();
        }
    }

    public Point getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Point coordinates) {
		this.coordinates = coordinates;
	}

	public List<Intersection> getNeighbors() {
    	ArrayList<Intersection> neighbors = new ArrayList<Intersection>();
    	for (Segment seg : outboundSegments) {
    		neighbors.add(seg.getDestination());
    	}
    	return neighbors;
    }

    public void addOutboundSegment(Segment segment) {
    	this.outboundSegments.add(segment);
    }
    
    public void removeOutboundSegment(Segment segment) {
    	this.outboundSegments.remove(segment);
    }
    
	@Override
	public String toString() {
		return "Intersection [Id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public ArrayList<Segment> getOutboundSegments() {
		return outboundSegments;
	}

	public Long getId() {
		return id;
	}    
}
