package modele;

/**
 * This class contains the coordinates (x,y) of a pixel on the window.
 * Only the part of the window where the map is displayed is concerned. 
 * A Point can be added to an Intersection if their locations coincide. 
 * @author H4414
 *
 */
public class Point {
	/**
	 * Coordinate on the x axis.
	 */
	private int x;
	
	/**
	 * Coordinate on the y axis.
	 */
	private int y;

	/**
	 * Constructor to initialize a Point of coordinates (x,y)
	 * Because of the Design Pattern FlyWeight, the instances of Point
	 * are created by a PointFactory.
	 * 
	 * @param x Coordinate on the x axis.
	 * @param y Coordinate on the y axis.
	 */
	protected Point(int x, int y) { 
		this.x = x;
		this.y = y;
	}

	/**
	 * @return The new coordinates corresponding to a translation of this point.

	 * @param deltaX The number of unit moves on the x axis.
	 * @param deltaY The number of unit moves on the y axis.
	 */
	public Point move(int deltaX, int deltaY) {
		return PointFactory.createPoint(x + deltaX, y + deltaY);
	}

	/**
	 * Computes the Euclidian distance between this Point and another.
	 * 
	 * @param p A Point.
	 * @return The distance between this Point and p.
	 */
	public int distance(Point p) {
		return (int) (Math.sqrt((x - p.getX()) * (x - p.getX()) + (double) (y - p.getY()) * (y - p.getY())));
	}

	/**
	 * Computes the Euclidian distance between this Point and another (as coordinates).
	 * 
	 * @param x1 Coordinate on the x axis.
	 * @param y1 Coordinate on the y axis.
	 * 
	 * @return The distance between this Point and the coordinates.
	 */
	public int distanceWithCoordinates(int x1, int y1) {
		return (int) (Math.sqrt((x - x1) * (x - x1) + (double) (y - y1) * (y - y1)));
	}

	/**
	 * Allows to know if this Point is in a rectangle of known coordinates.
	 * 
	 * @param x1 One edge of the box.
	 * @param y1 One edge of the box.
	 * @param x2 One edge of the box.
	 * @param y2 One edge of the box.
	 * @return True if this Point is in the rectangle, else false.
	 */
	public boolean inBox(int x1, int y1, int x2, int y2) {
		int x = this.getX();
		int y = this.getY();
		int maxX;
		int maxY;
		int minX;
		int minY;
		if (x2 > x1) {
			maxX = x2;
			minX = x1;
		} else {
			maxX = x1;
			minX = x2;
		}
		if (y2 > y1) {
			maxY = y2;
			minY = y1;
		} else {
			maxY = y1;
			minY = y2;
		}
		return ( (x <= maxX && x >= minX) && (y <= maxY && y >= minY) );
	}

	/**
	 * Computes the minimal distance between this Point (named A)
	 * and a segment [BC] whose associated two Points are passed as parameters
	 * (named B and C).
	 * The orthogonal projection of this point on the segment is named D. 
	 * 
	 * @param x1 X coordinate of the first point of the segment.
	 * @param y1 Y coordinate of the first point of the segment.
	 * @param x2 X coordinate of the second point of the segment.
	 * @param y2 Y coordinate of the second point of the segment.
	 * @return The minimal distance between this and the segment.
	 */
	public float distBetweenPointAndLine(int x1, int y1, int x2, int y2) {

		int x = this.getX();
		int y = this.getY();

		float AB = distBetween(x, y, x1, y1);
		float BC = distBetween(x1, y1, x2, y2);
		float AC = distBetween(x, y, x2, y2);
		
		if(x1 == x2 && y1 == y2) {
			return AB;
		}

		// Heron's formula
		float s = (AB + BC + AC) / 2;
		float area = (float) Math.sqrt(s * (s - AB) * (s - BC) * (s - AC));

		float AD = (2 * area) / BC;
		return AD;
	}
	
	/**
	 * Calculates the Euclidian distance between two points whose coordinates 
	 * are passed as parameters. Only used for the <code>distBetweenPointAndLine
	 * </code> function.
	 * 
	 * @param x1 X coordinate of the first point of the segment.
	 * @param y1 Y coordinate of the first point of the segment.
	 * @param x2 X coordinate of the second point of the segment.
	 * @param y2 Y coordinate of the second point of the segment.
	 * @return The distance between the two Points.
	 */
	private static float distBetween(int x1, int y1, int x2, int y2) {
		float deltaX = (float) x1 - x2;
		float deltaY = (float) y1 - y2;

		return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	}

	/**
	 * Default getter.
	 * 
	 * @return The x coordinate of this Point.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Default getter.
	 * 
	 * @return The y coordinate of this Point.
	 */
	public int getY() {
		return y;
	}
}