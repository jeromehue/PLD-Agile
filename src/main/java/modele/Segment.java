package modele;

public class Segment extends Shape {
	
	@Override
	public boolean contains(Point p) {
		return false;
	}

	@Override
	public void display(Visitor v) {
		v.display(this);
	}

	private double length;
	private String name;
	private Intersection origin;
	private Intersection destination;
	
	public Segment() {}

	public Segment(double length, String name, Intersection origin, Intersection destination) {
		super();
		this.length = length;
		this.name = name;
		this.origin = origin;
		this.destination = destination;
	}

	@Override
	public String toString() {
		return "Segment [length=" + length + ", name=" + name + ", origin=" + origin + ", destination=" + destination
				+ "]";
	}
	
	public double getLength() {
		return length;
	}

	public String getName() {
		return name;
	}

	public Intersection getOrigin() {
		return origin;
	}

	public Intersection getDestination() {
		return destination;
	}
}
