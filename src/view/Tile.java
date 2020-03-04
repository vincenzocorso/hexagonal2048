package hexagonal2048.view;

import hexagonal2048.util.Point;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Tile extends Polygon {
	protected static final Color textColor1 = new Color(119, 110, 101);
	protected static final Color textColor2 = new Color(249, 246, 242);
	protected static final Color defaultBorderColor = new Color(187, 173, 160);
	protected static final Color[] defaultBackgroundColors = new Color[]{
		new Color(205, 193, 181), new Color(238, 228, 218), new Color(237, 224, 200),
		new Color(242, 177, 121), new Color(245, 149, 99), new Color(246, 124, 95),
		new Color(246, 94, 59), new Color(237, 207, 114), new Color(237, 200, 80),
		new Color(237, 200, 80), new Color(237, 197, 63), new Color(237, 194, 46),
		new Color(87, 87, 87)
	};
	protected Font textFont;
	protected float strokeThickness;
	
	protected final int sideLength;
	protected int value;

	public Tile(int sideLength, int value) {
		this.sideLength = sideLength;
		this.setupAspectRatio();
		this.setupValue(value);
	}

	private void setupAspectRatio() {
		this.textFont = new Font("Arial", Font.BOLD, (int)((this.sideLength * 30) / 50));
		this.strokeThickness = (float)((this.sideLength * 30.0) / 50);
	}

	public void setupValue(int value) {
		this.value = value;
	}

	public Point getDistanceFromTile(Tile target) {
		Rectangle targetBounds = target.getBounds();
		Rectangle sourceBounds = this.getBounds();
		return new Point((int)(targetBounds.getCenterX() - sourceBounds.getCenterX()), (int)(targetBounds.getCenterY() - sourceBounds.getCenterY()));
	}

	public void draw(Graphics2D g2d) {
		Graphics2D previousGraphics = (Graphics2D)g2d.create();

		this.drawBorder(g2d);
		this.fill(g2d);
		this.drawString(g2d);

		g2d = previousGraphics;
	}

	private void drawBorder(Graphics2D g2d) {
		g2d.setColor(this.defaultBorderColor);
		g2d.setStroke(new BasicStroke(this.strokeThickness));
		g2d.drawPolygon(this);
	}

	private void fill(Graphics2D g2d) {
		int index;
		if(this.value <= 0)
			index = 0;
		else if(this.value > 4096)
			index = 12;
		else
			index = (int)(Math.log(this.value) / Math.log(2));

		g2d.setColor(defaultBackgroundColors[index]);
		g2d.fillPolygon(this);
	}

	private void drawString(Graphics2D g2d) {
		g2d.setColor((this.value <= 4) ? this.textColor1 : this.textColor2);
		g2d.setFont(this.textFont);
		FontMetrics metrics = g2d.getFontMetrics(this.textFont);
		String text = (this.value == 0) ? "" : "" + this.value;
		Rectangle2D stringBounds = metrics.getStringBounds(text, g2d);
		double x = this.getBounds().getCenterX() - stringBounds.getWidth() / 2;
		double y = this.getBounds().getCenterY() - stringBounds.getHeight() / 2 + metrics.getAscent();
		g2d.drawString(text, (int)x, (int)y);
	}
}