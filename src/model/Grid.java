package hexagonal2048.model;

import hexagonal2048.util.*;
import java.util.*;

public abstract class Grid implements Cloneable {
	protected final int size;
	protected Tile[][] tiles;

	public Grid(int size) {
		this.size = size;
		this.initGrid();
		this.insertRandomTile();
		this.insertRandomTile();
	}

	/**
	 * Initialise the grid with empty tiles.
	 * If a position of the array is non used by the grid, it must be set to null.
	 */
	protected abstract void initGrid();

	private TileUpdate insertRandomTile() {
		int x, y, value;
		do {
			x = (int)(Math.random() * this.tiles.length);
			y = (int)(Math.random() * this.tiles.length);
			value = (Math.random() < 0.5) ? 2 : 4;
		}
		while(this.tiles[y][x] == null || this.tiles[y][x].getValue() != 0);
		this.tiles[y][x] = new Tile(new Point(x, y), value);
		return new TileUpdate(this.tiles[y][x].getPosition(), value);
	}

	public List<TileUpdate> moveTiles(Direction direction) {
		List<TileUpdate> updates = new ArrayList<>();

		List<List<Tile>> section = this.getGridSection(direction);
		section.forEach(s -> updates.addAll(this.moveSection(s)));

		if(!updates.isEmpty())
			updates.add(this.insertRandomTile());

		return updates;
	}

	private List<TileUpdate> moveSection(List<Tile> section) {
		List<TileUpdate> updates = new ArrayList<>();
		boolean[] tilesMergedPosition = new boolean[section.size()];
		for(int currentPosition = 1; currentPosition < section.size(); currentPosition++) {
			if(section.get(currentPosition).getValue() != 0) {
				int furthestPosition = Grid.getFurthestPosition(section, currentPosition, tilesMergedPosition);
				if(furthestPosition != currentPosition) {
					TileUpdate update = this.moveTileInSection(section, currentPosition, furthestPosition);
					updates.add(update);
				}
			}
		}
		return updates;
	}

	private static int getFurthestPosition(List<Tile> section, int startPosition, boolean[] hasTileMerged) {
		int furthestPosition = startPosition;
		while(furthestPosition - 1 >= 0 && section.get(furthestPosition - 1).getValue() == 0)
			furthestPosition--;

		if(furthestPosition - 1 >= 0 && section.get(furthestPosition - 1).getValue() == section.get(startPosition).getValue()) {
			if(!hasTileMerged[furthestPosition - 1]) {
				hasTileMerged[furthestPosition - 1] = true;
				furthestPosition--;
			}
		}

		return furthestPosition;
	}

	private TileUpdate moveTileInSection(List<Tile> section, int startPosition, int finalPosition) {
		Tile startTile = section.get(startPosition);
		Tile finalTile = section.get(finalPosition);
		boolean hasMerged = (startPosition != finalPosition && startTile.getValue() == finalTile.getValue()) ? true : false;

		Tile newStartTile = new Tile(startTile.getPosition());
		Tile newFinalTile = new Tile(finalTile.getPosition(), finalTile.getValue() + startTile.getValue());
		section.set(startPosition, newStartTile);
		section.set(finalPosition, newFinalTile);
		this.tiles[startTile.getPosition().getY()][startTile.getPosition().getX()] = newStartTile;
		this.tiles[finalTile.getPosition().getY()][finalTile.getPosition().getX()] = newFinalTile;

		if(hasMerged)
			return new MergingTileUpdate(startTile.getPosition(), startTile.getValue(), finalTile.getPosition(), newFinalTile.getValue());
		else
			return new MovementTileUpdate(startTile.getPosition(), startTile.getValue(), finalTile.getPosition());
	}

	protected abstract List<List<Tile>> getGridSection(Direction direction);

	public List<TileUpdate> getTiles() {
		List<TileUpdate> tilesInfo = new ArrayList<>();
		for(int y = 0; y < this.tiles.length; y++)
			for(int x = 0; x < this.tiles.length; x++)
				if(this.tiles[y][x] != null)
					tilesInfo.add(new TileUpdate(this.tiles[y][x].getPosition(), this.tiles[y][x].getValue()));
		return tilesInfo;
	}

	public boolean hasWinTile() {
		return this.hasTile(2048);
	}

	public boolean canMove() {
		return this.hasEmptyTile() || this.hasTwoAdjacentEqualTiles();
	}

	private boolean hasEmptyTile() {
		return this.hasTile(0);
	}

	private boolean hasTile(int value) {
		for(int y = 0; y < this.tiles.length; y++)
			for(int x = 0; x < this.tiles.length; x++)
				if(this.tiles[y][x] != null && this.tiles[y][x].getValue() == value)
					return true;
		return false;
	}

	protected abstract boolean hasTwoAdjacentEqualTiles();

	@Override
	public Object clone() throws CloneNotSupportedException {
		Grid gridCopy = (Grid)super.clone();
		gridCopy.tiles = new Tile[this.tiles.length][this.tiles.length];
		for(int y = 0; y < gridCopy.tiles.length; y++)
			for(int x = 0; x < gridCopy.tiles.length; x++)
				gridCopy.tiles[y][x] = this.tiles[y][x];
		return gridCopy;
	}
}