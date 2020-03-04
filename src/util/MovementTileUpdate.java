package hexagonal2048.util;

/**
 * Describes a tile that has moved in the grid.
 */
public class MovementTileUpdate extends TileUpdate{
	private final Point finalPosition;

	/**
	 * Create an object which describe a tile that has moved in the grid.
	 * @param currentPosition the current position of the tile affected by this update.
	 * @param finalPosition the final position of the tile after this update.
	 */
	public MovementTileUpdate(Point currentPosition, int currentValue, Point finalPosition) {
		super(currentPosition, currentValue);
		this.finalPosition = finalPosition;
	}

	/**
	 * Get the final position of the tile after this update.
	 * @return the final position of the tile after this update.
	 */
	public Point getFinalPosition() {
		return this.finalPosition;
	}
}