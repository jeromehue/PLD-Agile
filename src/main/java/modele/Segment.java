package modele;

public class Segment {

	private double length;
	private String name;
	private Long origin;
	private Long destination;
	
	public Segment() {}

	public Segment(double length, String name, Long origin, Long destination) {
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

	
	
	
	
	
	

}
