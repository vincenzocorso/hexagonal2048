package hexagonal2048.util;

/**
 * Describes a tile that has moved in the grid and that has merged with another tile.
 */
public class  MergingTileUpdate extends MovementTileUpdate {
	private final int newValue;

	/**
	 * Create an object which describe a tile that has moved in the grid and that has merged with another tile.
	 * @param currentPosition the current position of the tile affected by this update.
	 * @param finalPosition the final position of the tile after this update.
	 * @param newValue the new value of the tile after this update.
	 */
	public MergingTileUpdate(Point currentPosition, int currentValue, Point finalPosition, int newValue) {
		super(currentPosition, currentValue, finalPosition);
		this.newValue = newValue;
	}

	/**
	 * Get the new value of the tile after this update.
	 * @return the new value of the tile after this update.
	 */
	public int getNewValue() {
		return this.newValue;
	}
}