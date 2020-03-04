package hexagonal2048.view;

import hexagonal2048.util.Point;

public class HexagonalTile extends Tile {

	public HexagonalTile(Point centerPosition, int sideLength, int value) {
		super(sideLength, value);
		for(int i = 0; i < 6; i++) {
			int pointX = (int)(centerPosition.getX() + sideLength * Math.cos(i * Math.PI / 3));
			int pointY = (int)(centerPosition.getY() + sideLength * Math.sin(i * Math.PI / 3));
			this.addPoint(pointX, pointY);
		}
	}

	public HexagonalTile(int sideLength, int value) {
		this(Point.ORIGIN, sideLength, value);
	}

	public HexagonalTile(Point centerPosition, int sideLength) {
		this(centerPosition, sideLength, 0);
	}

	public HexagonalTile(int sideLength) {
		this(Point.ORIGIN, sideLength, 0);
	}
}