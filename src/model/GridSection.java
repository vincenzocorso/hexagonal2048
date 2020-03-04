package hexagonal2048.model;

import java.util.*;

public abstract class GridSection {
	private GridSection() {}

	public static List<List<Tile>> getVerticalSection(Tile[][] tiles) {
		List<List<Tile>> verticalSection = new ArrayList<List<Tile>>();
		for(int column = 0; column < tiles.length; column++) {
			verticalSection.add(new ArrayList<Tile>());
			for(int row = 0; row < tiles.length; row++)
				if(tiles[row][column] != null)
					verticalSection.get(column).add(tiles[row][column]);
		}
		return verticalSection;
	}

	public static List<List<Tile>> getHorizontalSection(Tile[][] tiles) {
		List<List<Tile>> horizontalSection = new ArrayList<List<Tile>>();
		for(int row = 0; row < tiles.length; row++) {
			horizontalSection.add(new ArrayList<Tile>());
			for(int column = 0; column < tiles.length; column++)
				if(tiles[row][column] != null)
					horizontalSection.get(row).add(tiles[row][column]);
		}
		return horizontalSection;
	}

	public static List<List<Tile>> getMainDiagonalsSection(Tile[][] tiles) {
		List<List<Tile>> mainDiagonalSection = new ArrayList<List<Tile>>();
		for(int line = tiles.length - 1; line >= -tiles.length + 1; line--) {
			int startColumn = (line >= 0) ? 0 : -line;
			int endColumn = (line >= 0) ? tiles.length - 1 - line : tiles.length - 1;
			mainDiagonalSection.add(new ArrayList<Tile>());
			for(int column = startColumn; column <= endColumn; column++)
				if(tiles[column + line][column] != null)
					mainDiagonalSection.get(mainDiagonalSection.size() - 1).add(tiles[column + line][column]);
		}
		return mainDiagonalSection;
	}

	public static List<List<Tile>> getAntiDiagonalsSection(Tile[][] tiles) {
		List<List<Tile>> antiDiagonalSection = new ArrayList<List<Tile>>();
		for(int line = 0; line < 2 * tiles.length - 1; line++) {
			int startColumn = (line < tiles.length) ? 0 : (line - tiles.length + 1);
			int endColumn = (line < tiles.length) ? line : tiles.length - 1;
			antiDiagonalSection.add(new ArrayList<Tile>());
			for(int column = startColumn; column <= endColumn; column++)
				if(tiles[line - column][column] != null)
					antiDiagonalSection.get(antiDiagonalSection.size() - 1).add(tiles[line - column][column]);
		}
		return antiDiagonalSection;
	}
}