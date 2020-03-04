package hexagonal2048.view;

import hexagonal2048.util.Point;
import java.awt.*;
import javax.swing.*;

public class LogoPanel extends JPanel {
	public LogoPanel() {
		this.setLayout(new BorderLayout());
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(145, 145);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Point center = new Point((int)this.getBounds().getCenterX(), (int)this.getBounds().getCenterY());
		Tile logo = new HexagonalTile(center, 47, 2048);
		logo.draw((Graphics2D)g);
	}
}