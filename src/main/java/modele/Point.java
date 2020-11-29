package modele;

public class Point {
	private int x;
	private int y;
	
	/**
	 * Create a point of coordinates (x,y)
	 * @param x
	 * @param y
	 */
	protected Point(int x, int y){ // Design Pattern FlyWeight: instances of Point are created by PointFactory
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the point corresponding to the deplacement of this according to (deltaX,deltaY)
	 * @param deltaX the number of unit moves on the x-axis 
	 * @param deltaY the number of unit moves on the y-axis 
	 */
	public Point move(int deltaX, int deltaY) {
		return PointFactory.createPoint(x+deltaX, y+deltaY);
	}
		
	/**
	 * @param p a point
	 * @return the distance between this and p
	 */
	public int distance(Point p){
		return (int)(Math.sqrt((x-p.getX())*(x-p.getX()) + (y-p.getY())*(y-p.getY())));
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}