package hexagonal2048.view;

import hexagonal2048.util.Point;
import java.awt.*;

public class ClassicTile extends Tile {

	public ClassicTile(Point centerPosition, int sideLength, int value) {
		super(sideLength, value);
		int halfSide = (int)(sideLength / 2);
		this.addPoint(centerPosition.getX() + halfSide, centerPosition.getY() - halfSide);
		this.addPoint(centerPosition.getX() - halfSide, centerPosition.getY() - halfSide);
		this.addPoint(centerPosition.getX() - halfSide, centerPosition.getY() + halfSide);
		this.addPoint(centerPosition.getX() + halfSide, centerPosition.getY() + halfSide);
		this.setupAspectRatio();
	}

	public ClassicTile(int sideLength, int value) {
		this(Point.ORIGIN, sideLength, value);
	}

	public ClassicTile(Point centerPosition, int sideLength) {
		this(centerPosition, sideLength, 0);
	}

	public ClassicTile(int sideLength) {
		this(Point.ORIGIN, sideLength, 0);
	}

	private void setupAspectRatio() {
		this.textFont = new Font("Arial", Font.BOLD, (int)((this.sideLength * 40) / 100));
		this.strokeThickness = (float)((this.sideLength * 30) / 100);
	}
}