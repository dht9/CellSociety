import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class updates the GUI to simulate a CA model with parameters defined in
 * an XML file chosen by the user.
 * 
 * @author DavidTran
 *
 */
public class SimulationLoop {

	private int fps = 60;

	/**
	 * Initializes and starts the simulation loop.
	 */
	public void start() {
		KeyFrame frame = new KeyFrame(Duration.millis(1000 / fps), e -> step());
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	/**
	 * Primary loop for running each frame of the simulation.
	 */
	private void step() {
		// do stuff
	}

	/**
	 * Initializes the scene based off user input of XML.
	 * 
	 * @param s stage for the gui
	 * @param guiWidth
	 * @param guiHeight
	 * @return scene to be displayed on the stage
	 */
	public Scene setupScene(Stage s, int guiWidth, int guiHeight) {

		// need to call method that creates buttons and waits for XML input via button

		Scene scene = new Scene(new Group(), guiWidth, guiHeight);

		return scene;
	}

}
