package hexagonal2048.model;

import hexagonal2048.util.*;
import java.util.*;

public class ClassicGrid extends Grid {
	public ClassicGrid(int size) {
		super(size);
	}

	@Override
	protected void initGrid() {
		this.tiles = new Tile[this.size][this.size];
		for(int y = 0; y < this.size; y++)
			for(int x = 0; x < this.size; x++)
				this.tiles[y][x] = new Tile(new Point(x, y));
	}

	@Override
	protected List<List<Tile>> getGridSection(Direction direction) {
		List<List<Tile>> section;

		switch(direction) {
			case UP: case DOWN: section = GridSection.getVerticalSection(this.tiles); break;
			case LEFT: case RIGHT: section = GridSection.getHorizontalSection(this.tiles); break;
			default: throw new IllegalMoveException();
		}

		if(direction == Direction.DOWN || direction == Direction.RIGHT)
			for(int i = 0; i < section.size(); i++)
				Collections.reverse(section.get(i));
		
		return section;
	}

	@Override
	protected boolean hasTwoAdjacentEqualTiles() {
		Direction[] directionsToCheck = new Direction[]{Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

		for(int y = 0; y < this.tiles.length; y++)
			for(int x = 0; x < this.tiles.length; x++)
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
			case UP: adjacentX = x; adjacentY = y - 1; break;
			case DOWN: adjacentX = x; adjacentY = y + 1; break;
			case LEFT: adjacentX = x - 1; adjacentY = y; break;
			case RIGHT: adjacentX = x + 1; adjacentY = y; break;
			default: throw new IllegalArgumentException();
		}

		if(!this.isWithinBounds(position) || !this.isWithinBounds(new Point(adjacentX, adjacentY)))
			return false;
		
		return this.tiles[y][x].getValue() != 0 && this.tiles[y][x].getValue() == this.tiles[adjacentY][adjacentX].getValue();
	}

	private boolean isWithinBounds(Point position) {
		int x = position.getX();
		int y = position.getY();
		return (x >= 0 && x < this.tiles.length && y >= 0 && y < this.tiles.length);
	}
}