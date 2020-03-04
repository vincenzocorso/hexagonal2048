import hexagonal2048.model.*;
import hexagonal2048.view.*;
import hexagonal2048.controller.*;
import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Model model = new GameManager();
			View view = new ViewManager("Hexagonal2048");
			Controller controller = new Controller(model, view);
		});
	}
}