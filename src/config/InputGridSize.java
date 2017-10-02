package config;

import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

/**
 * Class for getting user input for new grid dimension.
 * 
 * @author DavidTran
 *
 */
public class InputGridSize {

	private int input;
	private int gridWidth, gridHeight;
	private ResourceBundle myResources = ResourceBundle.getBundle("resources/Text");

	/**
	 * Opens a dialog for user to input a grid dimension.
	 * 
	 * @param dimension
	 *            - user prompt (can be "row" or "column")
	 * @return
	 */
	public int getInputSize(String dimension) {
		try {
			TextInputDialog dialog = new TextInputDialog("");

			dialog.setTitle("Input New Grid Dimensions");
			dialog.setHeaderText("Enter number of " + dimension);

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				input = Integer.parseInt(result.get());
			} else
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
}
