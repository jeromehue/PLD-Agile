package modele;

public class Point {
	private int x;
	private int y;

	/**
	 * Create a point of coordinates (x,y)
	 * 
	 * @param x
	 * @param y
	 */
	protected Point(int x, int y) { // Design Pattern FlyWeight: instances of Point are created by PointFactory
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the point corresponding to the deplacement of this according to
	 *         (deltaX,deltaY)
	 * @param deltaX the number of unit moves on the x-axis
	 * @param deltaY the number of unit moves on the y-axis
	 */
	public Point move(int deltaX, int deltaY) {
		return PointFactory.createPoint(x + deltaX, y + deltaY);
	}

	/**
	 * @param p a point
	 * @return the distance between this and p
	 */
	public int distance(Point p) {
		return (int) (Math.sqrt((x - p.getX()) * (x - p.getX()) + (double) (y - p.getY()) * (y - p.getY())));
	}

	public int distanceWithCoordinates(int x1, int y1) {
		return (int) (Math.sqrt((x - x1) * (x - x1) + (double) (y - y1) * (y - y1)));
	}

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
		return (x <= maxX && x >= minX && y <= maxY && y >= minY);
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