package hexagonal2048.view;

import java.awt.*;
import javax.swing.*;

public class BoardPanel extends JPanel {
	private final JLabel title = new JLabel();
	private final JLabel value = new JLabel();

	public BoardPanel(String title) {
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(187, 173, 160));

		final Font textFont = new Font("Arial", Font.PLAIN, 20);
		this.title.setFont(textFont);
		this.value.setFont(textFont);

		this.title.setHorizontalAlignment(SwingConstants.CENTER);
		this.value.setHorizontalAlignment(SwingConstants.CENTER);
		this.title.setText(title);

		final Color textColor = new Color(255, 244, 229);
		this.title.setForeground(textColor);
		this.value.setForeground(textColor);

		this.add(this.title, BorderLayout.NORTH);
		this.add(this.value, BorderLayout.CENTER);
	}

	public void setValueText(String text) {
		this.value.setText(text);
	}
}