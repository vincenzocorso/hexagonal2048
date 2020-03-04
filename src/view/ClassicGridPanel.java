package hexagonal2048.view;

import hexagonal2048.util.Point;
import java.awt.*;
import javax.swing.*;

public class ClassicGridPanel extends GridPanel {
	public ClassicGridPanel(int size, int tileSideLength) {
		super(size, tileSideLength);
		this.gridTiles = new Tile[size][size];
		this.gameTiles = new Tile[size][size];
		this.animatingTiles = new boolean[size][size];
		this.initGrid();
	}

	private void initGrid() {
		int spacing = 15;
		int side = this.tileSideLength + spacing;

		Point startPoint = Point.ORIGIN;
		for(int y = 0; y < this.gridTiles.length; y++) {
			Point currentPoint = startPoint.getLocation();
			for(int x = 0; x < this.gridTiles.length; x++) {
				this.gridTiles[y][x] = new ClassicTile(currentPoint, this.tileSideLength);
				currentPoint = currentPoint.getTranslatedPoint(side, 0);
			}
			startPoint = startPoint.getTranslatedPoint(0, side);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		int minX = this.getGridMinX();
		int maxX = this.getGridMaxX();
		int minY = this.getGridMinY();
		int maxY = this.getGridMaxY();
		return new Dimension(maxX + Math.abs(minX) + 50, maxY + Math.abs(minY) + 50);
	}

	private int getGridMinX() {
		Tile leftmostTile = this.gridTiles[0][0];
		return (leftmostTile == null) ? 0 : (int)leftmostTile.getBounds().getMinX();
	}

	private int getGridMaxX() {
		Tile rightmostTile = this.gridTiles[this.size - 1][this.size - 1];
		return (rightmostTile == null) ? 0 : (int)rightmostTile.getBounds().getMaxX();
	}

	private int getGridMinY() {
		Tile leftmostTile = this.gridTiles[0][0];
		return (leftmostTile == null) ? 0 : (int)leftmostTile.getBounds().getMinY();
	}

	private int getGridMaxY() {
		Tile rightmostTile = this.gridTiles[this.size - 1][this.size - 1];
		return (rightmostTile == null) ? 0: (int)rightmostTile.getBounds().getMaxY();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;

		this.setupGridAtCenter();
		for(int y = 0; y < this.gridTiles.length; y++)
			for(int x = 0; x < this.gridTiles.length; x++)
				this.gridTiles[y][x].draw(g2d);

		this.alignTilesToGrid();
		for(int y = 0; y < this.gameTiles.length; y++)
			for(int x = 0; x < this.gameTiles.length; x++)
				if(this.gameTiles[y][x] != null)
					this.gameTiles[y][x].draw(g2d);
	}

	private void setupGridAtCenter() {
		Point distanceVector = getGridDistanceFromCenter();
		for(int y = 0; y < this.gridTiles.length; y++)
			for(int x = 0; x < this.gridTiles.length; x++)
				this.gridTiles[y][x].translate(distanceVector.getX(), distanceVector.getY());
	}

	private Point getGridDistanceFromCenter() {
		Point gridCenter = this.getGridCenter();
		double dx = this.getWidth() / 2 - gridCenter.getX();
		double dy = this.getHeight() / 2 - gridCenter.getY();
		return new Point((int)dx, (int)dy);
	}

	private Point getGridCenter() {
		Rectangle upperLeftTile = this.gridTiles[0][0].getBounds();
		Rectangle lowerRightTile = this.gridTiles[this.size-1][this.size-1].getBounds();
		int centerX = (int)(upperLeftTile.getCenterX() + (lowerRightTile.getCenterX() - upperLeftTile.getCenterX()) / 2);
		int centerY = (int)(upperLeftTile.getCenterY() + (lowerRightTile.getCenterY() - upperLeftTile.getCenterY()) / 2);
		return new Point(centerX, centerY);
	}

	private void alignTilesToGrid() {
		for(int y = 0; y < this.gameTiles.length; y++) {
			for(int x = 0; x < this.gameTiles.length; x++) {
				if(this.gameTiles[y][x] != null && !this.animatingTiles[y][x]) {
					Point distanceVector = this.gameTiles[y][x].getDistanceFromTile(this.gridTiles[y][x]);
					this.gameTiles[y][x].translate(distanceVector.getX(), distanceVector.getY());
				}
			}
		}
	}
}