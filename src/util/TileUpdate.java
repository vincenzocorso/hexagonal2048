package hexagonal2048.util;

/**
 * Describes a generic update of a grid tile.
 */
public class TileUpdate {
	private final Point currentPosition;
	private final int currentValue;

	/**
	 * Create a generic update info about a grid tile.
	 * @param currentPosition the current position of the tile affected by this update.
	 */
	public TileUpdate(Point currentPosition, int currentValue) {
		this.currentPosition = currentPosition;
		this.currentValue = currentValue;
	}

	/**
	 * Get the current position of the tile affected by this update
	 * @return the current position of the tile affected by this update.
	 */
	public Point getCurrentPosition() {
		return this.currentPosition;
	}

	/**
	 * Get the current value of the tile affected by this update.
	 * @return the current position of the tile affected by this update.
	 */
	public int getCurrentValue() {
		return this.currentValue;
	}
}