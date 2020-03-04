package hexagonal2048.util;

/**
 * Describes a point in a two dimensional space.
 */
public class Point {
	public static final Point ORIGIN = new Point(0, 0);

	private final int x;
	private final int y;

	/**
	 * Create and initialise a point at the specified (x, y) location.
	 * @param x the x coordinate of the point.
	 * @param y the y coordinate of the point.
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Create and initialise a point in the origin.
	 */
	public Point() {
		this(0, 0);
	}

	/**
	 * Return the x coordinate of this point.
	 * @return the x coordinate of this point.
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Return the y coordinate of this point.
	 * @return the y coordinate of this point.
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Return the distance of this point from point p.
	 * @param point the point from which to calculate the distance.
	 * @return the distance between this and point p.
	 */
	public double getDistanceFromPoint(Point p) {
		int distanceX = p.x - this.x;
		int distanceY = p.y - this.y;
		return Math.sqrt(distanceX * distanceX - distanceY * distanceY);
	}

	/**
	 * Return the distance of this point from the origin.
	 * @return the distance from the origin. 
	 */
	public double getDistanceFromOrigin() {
		return this.getDistanceFromPoint(Point.ORIGIN);
	}

	/**
	 * Create a new point at (x + dx, y + dy) where (x, y) are the coordinates of this point.
	 * @param dx the x coordinate increment.
	 * @param dy the y coordinate increment.
	 * @return a new point at (x + dx, y + dy) location.
	 */
	public Point getTranslatedPoint(int dx, int dy) {
		return new Point(this.x + dx, this.y + dy);
	}

	/**
	 * Return a copy of this point.
	 * @return a copy of this point.
	 */
	public Point getLocation() {
		return this.getTranslatedPoint(0, 0);
	}

	/**
	 * Return a string representation of this point.
	 * @return a string representation of this point.
	 */
	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
}