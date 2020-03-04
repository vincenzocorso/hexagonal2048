package hexagonal2048.view;

import hexagonal2048.util.Point;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

public class MessagePanel extends JPanel {
	private static final Font font = new Font("Arial", Font.BOLD, 60);

	private int x;
	private int y;
	private int width;
	private int height;
	private Color backgroundColor;
	private String text;

	public MessagePanel(Point origin, Dimension dimension, Color backgroundColor, String text) {
		this.x = origin.getX();
		this.y = origin.getY();
		this.width = (int)dimension.getWidth();
		this.height = (int)dimension.getHeight();
		this.backgroundColor = backgroundColor;
		this.text = text;
		this.setOpaque(false);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g.create();
		g2.setColor(this.backgroundColor);
		g2.fillRect(this.x, this.y, this.width, this.height);
		g2.setColor(Color.WHITE);
		g2.setFont(this.font);
		FontMetrics metrics = g2.getFontMetrics(this.font);
		Rectangle2D stringBounds = metrics.getStringBounds(this.text, g2);
		double x = this.getBounds().getCenterX() - stringBounds.getWidth() / 2;
		double y = this.getBounds().getCenterY() - stringBounds.getHeight() / 2 + metrics.getAscent();
		g2.drawString(this.text, (int)x, (int)y);
		g2.dispose();
	}
}