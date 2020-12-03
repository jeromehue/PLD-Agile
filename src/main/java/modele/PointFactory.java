package modele;

import view.GraphicalView;

public class PointFactory {
	private static Point points[][];
	private static CityMap cityMap;
	private static GraphicalView graphicalView;

	/**
	 * Create a factory of points. The coordinates (x,y) of a point must belong to [0,width]x[0,height].
	 * Use fly weight to recycle points
	 * @param width the maximal value for x
	 * @param height the maximal value for y
	 */
	public static void initPointFactory(GraphicalView graphicalView, CityMap cityMap){
		//System.out.println(graphicalView.getWidth() + " : " +graphicalView.getHeight());
		PointFactory.points = new Point[graphicalView.getWidth()+1][graphicalView.getHeight()+1];
		PointFactory.graphicalView = graphicalView;
		PointFactory.cityMap = cityMap;
	}
	
	/** 
	 * @param x
	 * @param y
	 * @return an instance p of Point such that p.x = x and p.y = y
	 */
	public static Point createPoint(int x, int y){
		
		if ((x > graphicalView.getWidth()) || (x < 0) || (y > graphicalView.getHeight()) || (y < 0))
			return null;
		// Happens at loading
		if(x > points.length || y > points[0].length) {
			return null;
		}
		//System.out.println(points.length + "+" + points[0].length + " : " + x + "," + y);
		if (points[x][y] == null)
			points[x][y] = new Point(x,y);
		return points[x][y];
	}
	
	public static Point createPoint(Intersection i){
		int x = intersectionToPixelLattitude(i);
		int y = intersectionToPixelLongitude(i);
		if ((x > graphicalView.getWidth()) || (x < 0) || (y > graphicalView.getHeight()) || (y < 0))
			return null;
		if (points[x][y] == null)
			points[x][y] = new Point(x,y);
		return points[x][y];
	}
	
	public static int intersectionToPixelLattitude(Intersection i) {
		double longPourcent = ( cityMap.getMaxLongitude() - i.getLongitude()) / ( cityMap.getMaxLongitude() - cityMap.getMinLongitude());
		double latitudePixel =( 1 - longPourcent ) * graphicalView.getWidth();
		return (int)latitudePixel;
	}
	
	public static int intersectionToPixelLongitude(Intersection i) {
		double latPourcent = ( cityMap.getMaxLatitude() - i.getLatitude()) / (cityMap.getMaxLatitude() - cityMap.getMinLatitude());
		double longitudePixel =  latPourcent  * graphicalView.getHeight();
		return (int)longitudePixel;
	}
	
	
}