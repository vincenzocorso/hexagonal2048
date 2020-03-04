package hexagonal2048.view;

public abstract class TileFactory {
	private TileFactory() {}

	public static Tile createTile(GridPanel grid, int sideLength, int value) {
		if(grid instanceof ClassicGridPanel)
			return new ClassicTile(sideLength, value);
		else if(grid instanceof HexagonalGridPanel)
			return new HexagonalTile(sideLength, value);
		else
			throw new IllegalArgumentException("Can't create tile for this grid");
	}
}