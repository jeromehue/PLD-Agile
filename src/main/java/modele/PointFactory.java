package modele;

import view.GraphicalView;

/**
 * This class creates and stores all Points of the window.
 * It also provides utility functions for distance calculations.
 * 
 * @author H4414
 * 
 */
public class PointFactory {
	/**
	 * A 2D array containing all points of the window.
	 */
	private static Point points[][];
	
	/**
	 * The instance containing all informations relative to the map.
	 */
	private static CityMap cityMap;
	
	/**
	 * Used to get the dimensions of the window.
	 */
	private static GraphicalView graphicalView;

	/**
	 * Create a factory of points. The coordinates (x,y) of a point must belong to
	 * [0,width]x[0,height]. Use fly weight to recycle points.
	 * 
	 * @param graphicalView Used to retrieve the dimensions of the window.
	 * @param cityMap 		Used to retrieve the maximal and minimal 
	 * latitude and longitude of the map.
	 */
	public static void initPointFactory(GraphicalView graphicalView, CityMap cityMap) {
		PointFactory.points = new Point[graphicalView.getWidth() + 1][graphicalView.getHeight() + 1];
		PointFactory.graphicalView = graphicalView;
		PointFactory.cityMap = cityMap;
	}

	/**
	 * Method used to create instances of Points.
	 * 
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @return An instance of a Point whose coordinates are passed as parameters.
	 */
	public static Point createPoint(int x, int y) {

		if ((x > graphicalView.getWidth()) || (x < 0) || (y > graphicalView.getHeight()) || (y < 0))
			return null;
		// Happens at loading
		if (x > points.length || y > points[0].length) {
			return null;
		}
		// System.out.println(points.length + "+" + points[0].length + " : " + x + "," +
		// y);
		if (points[x][y] == null)
			points[x][y] = new Point(x, y);
		return points[x][y];
	}

	/**
	 * Method used to create instances of Points.
	 * 
	 * @param i The intersection corresponding to the Point to be created.
	 * @return An instance of a Point whose coordinates are retrieved and calculated
	 * from the latitude and longitude of the given Intersection.
	 */
	public static Point createPoint(Intersection i) {
		int x = intersectionToPixelLatitude(i);
		int y = intersectionToPixelLongitude(i);
		if ((x > graphicalView.getWidth()) || (x < 0) || (y > graphicalView.getHeight()) || (y < 0))
			return null;
		if (points[x][y] == null)
			points[x][y] = new Point(x, y);
		return points[x][y];
	}

	/**
	 * Utility method used for creating a point from an Intersection.
	 * 
	 * @param i The intersection from which information is extracted.
	 * @return The x coordinate of the window corresponding to the latitude
	 * of the given Intersection.
	 */
	private static int intersectionToPixelLatitude(Intersection i) {
		double longPourcent = (cityMap.getMaxLongitude() - i.getLongitude())
				/ (cityMap.getMaxLongitude() - cityMap.getMinLongitude());
		double latitudePixel = (1 - longPourcent) * graphicalView.getWidth();
		return (int) latitudePixel;
	}

	/**
	 * Utility method used for creating a point from an Intersection.
	 * 
	 * @param i The intersection from which information is extracted.
	 * @return The y coordinate of the window corresponding to the longitude
	 * of the given Intersection.
	 */
	private static int intersectionToPixelLongitude(Intersection i) {
		double latPourcent = (cityMap.getMaxLatitude() - i.getLatitude())
				/ (cityMap.getMaxLatitude() - cityMap.getMinLatitude());
		double longitudePixel = latPourcent * graphicalView.getHeight();
		return (int) longitudePixel;
	}
}