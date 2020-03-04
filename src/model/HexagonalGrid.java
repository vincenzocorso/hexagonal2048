package hexagonal2048.model;

import hexagonal2048.util.*;
import java.util.*;

public class HexagonalGrid extends Grid {
	public HexagonalGrid(int size) {
		super(size);
	}

	@Override
	protected void initGrid() {
		int gridSize = this.size * 2 - 1;
		this.tiles = new Tile[gridSize][gridSize];
		for(int y = 0; y < gridSize; y++)
			for(int x = 0; x < gridSize; x++)
				if(y > x - this.size && y < x + this.size)
					this.tiles[y][x] = new Tile(new Point(x, y));
	}

	@Override
	protected List<List<Tile>> getGridSection(Direction direction) {
		List<List<Tile>> section;

		switch(direction) {
			case UP: case DOWN: section = GridSection.getVerticalSection(this.tiles); break;
			case UPPER_LEFT: case LOWER_RIGHT: section = GridSection.getMainDiagonalsSection(this.tiles); break;
			case LOWER_LEFT: case UPPER_RIGHT: section = GridSection.getHorizontalSection(this.tiles); break;
			default: throw new IllegalMoveException();
		}

		if(direction == Direction.DOWN || direction == Direction.LOWER_RIGHT || direction == Direction.UPPER_RIGHT)
			for(int i = 0; i < section.size(); i++)
				Collections.reverse(section.get(i));
		
		return section;
	}

	@Override
	protected boolean hasTwoAdjacentEqualTiles() {
		Direction[] directionsToCheck = new Direction[]{
			Direction.UPPER_LEFT, Direction.UP, Direction.UPPER_RIGHT,
			Direction.LOWER_LEFT, Direction.DOWN, Direction.LOWER_RIGHT
		};

		for(int y = 0; y < this.tiles.length; y++)
			for(int x = 0; x < this.tiles.length; x++)
				if(this.tiles[y][x] != null)
					for(Direction direction : directionsToCheck)
						if(this.hasAdjacentEqualTile(new Point(x, y), direction))
							return true;
		return false;
	}

	private boolean hasAdjacentEqualTile(Point position, Direction direction) {
		int x = position.getX();
		int y = position.getY();
		int adjacentX, adjacentY;

		switch(direction) {
			case UPPER_LEFT: adjacentX = x - 1; adjacentY = y - 1; break;
			case UP: adjacentX = x; adjacentY = y - 1; break;
			case UPPER_RIGHT: adjacentX = x + 1; adjacentY = y; break;
			case LOWER_LEFT: adjacentX = x - 1; adjacentY = y; break;
			case DOWN: adjacentX = x; adjacentY = y + 1; break;
			case LOWER_RIGHT: adjacentX = x + 1; adjacentY = y + 1; break;
			default: throw new IllegalArgumentException();
		}

		if(!this.isWithinBounds(position) || !this.isWithinBounds(new Point(adjacentX, adjacentY)))
			return false;
		
		return this.tiles[y][x].getValue() != 0 && this.tiles[y][x].getValue() == this.tiles[adjacentY][adjacentX].getValue();
	}

	private boolean isWithinBounds(Point position) {
		int x = position.getX();
		int y = position.getY();
		if (x < 0 || x >= this.tiles.length || y < 0 || y >= this.tiles.length)
			return false;
		return (y > x - this.size && y < x + this.size);
	}
}