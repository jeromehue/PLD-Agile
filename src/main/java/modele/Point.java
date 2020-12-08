package modele;

/**
 * This class contains the coordinates (x,y) of an Intersection on the window. 
 * 
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
	 * @param x coordinate on the x axis
	 * @param y coordinate on the y axis
	 */
	protected Point(int x, int y) { 
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the new coordinates corresponding to a translation of this point.

	 * @param deltaX the number of unit moves on the x-axis
	 * @param deltaY the number of unit moves on the y-axis
	 */
	public Point move(int deltaX, int deltaY) {
		return PointFactory.createPoint(x + deltaX, y + deltaY);
	}

	/**
	 * Computes the Euclidian distance between this Point and another
	 * 
	 * @param p a Point
	 * @return the distance between this Point and p
	 */
	public int distance(Point p) {
		return (int) (Math.sqrt((x - p.getX()) * (x - p.getX()) + (double) (y - p.getY()) * (y - p.getY())));
	}

	/**
	 * Computes the Euclidian distance between this Point and another (as coordinates)
	 * 
	 * @param x1 coordinate on the x axis
	 * @param y1 coordinate on the y axis
	 * 
	 * @return the distance between this Point and the coordinates
	 */
	public int distanceWithCoordinates(int x1, int y1) {
		return (int) (Math.sqrt((x - x1) * (x - x1) + (double) (y - y1) * (y - y1)));
	}

	/**
	 * Allows to know if this Point is in a rectangle of known coordinates.
	 * @param x1 one edge of the box
	 * @param y1 one edge of the box
	 * @param x2 one edge of the box
	 * @param y2 one edge of the box
	 * @return true if this Point is in the rectangle, else false
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

	public float distBetweenPointAndLine(int x1, int y1, int x2, int y2) {
		// A - the standalone point (x, y)
		// B - start point of the line segment (x1, y1)
		// C - end point of the line segment (x2, y2)
		// D - the crossing point between line from A to BC

		int x = this.getX();
		int y = this.getY();

		float AB = distBetween(x, y, x1, y1);
		float BC = distBetween(x1, y1, x2, y2);
		float AC = distBetween(x, y, x2, y2);

		// Heron's formula
		float s = (AB + BC + AC) / 2;
		float area = (float) Math.sqrt(s * (s - AB) * (s - BC) * (s - AC));

		// but also area == (BC * AD) / 2
		// BC * AD == 2 * area
		// AD == (2 * area) / BC
		// TODO: check if BC == 0
		float AD = (2 * area) / BC;
		return AD;
	}

	private static float distBetween(int x, int y, int x1, int y1) {
		float xx = (float) x1 - x;
		float yy = (float) y1 - y;

		return (float) Math.sqrt(xx * xx + yy * yy);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}