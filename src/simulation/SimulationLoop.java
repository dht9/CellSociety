package simulation;
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

	private int guiWidth;
	private int guiHeight;
	private int fps = 60;
	private Stage myStage;
	private Scene myScene;
	
	
	/**
	 * Constructor, give simulation loop a scene
	 */
	public SimulationLoop(Stage s, Scene scene, int width, int height) {
		myStage = s;
		myScene = scene;
		guiWidth = width;
		guiHeight = height;
	}

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

}
