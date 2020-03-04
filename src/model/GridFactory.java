package hexagonal2048.model;

import hexagonal2048.util.*;

public abstract class GridFactory {
	private GridFactory() {};

	public static Grid createGrid(GridType gridType, int size) {
		switch(gridType) {
			case CLASSIC: return new ClassicGrid(size);
			case HEXAGONAL: return new HexagonalGrid(size);
			default: throw new IllegalArgumentException("Invalid grid type.");
		}
	}
}