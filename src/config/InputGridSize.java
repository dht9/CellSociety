package config;

import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JPanel;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.util.Pair;

public class InputGridSize {

	private int numRows;
	private int numCols;
	private int input;
	private JPanel myPanel;
	private int gridWidth, gridHeight;
	private ResourceBundle myResources = ResourceBundle.getBundle("resources/Text");

	public int getInputSize(String s) {
		try {
			TextInputDialog dialog = new TextInputDialog("");

			dialog.setTitle("Input New Grid Dimensions");
			dialog.setHeaderText("Enter number of " + s);

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				input = Integer.parseInt(result.get());
			}
			else
				input = 1;
		} catch (NumberFormatException e) {
			showError(e.getMessage());
		}
		return input;
	}

	private void showError(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(myResources.getString("ErrorInputSize"));
		alert.setContentText(message);
		alert.showAndWait();
	}

	public int[] getGridSize() {
		int[] size = { gridWidth, gridHeight };
		return size;
	}
}
