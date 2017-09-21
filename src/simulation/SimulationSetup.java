package simulation;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class initializes the interface for the simulation.
 * 
 * @author DavidTran
 *
 */
public class SimulationSetup extends Application {

	private final int guiSize = 500;
	private SimulationLoop mySimulationLoop;

	
	/**
	 * Initialize stage, scene, and simulation loop.
	 */
	@Override
	public void start(Stage s) {

		Scene scene = setupScene(s, guiSize, guiSize);

		s.setTitle("Cell Society");

		s.setScene(scene);

		s.show();
		
		mySimulationLoop = new SimulationLoop(s, scene, guiSize, guiSize);

		mySimulationLoop.start();

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

		// need to add buttons
		
		// can create new class for adding the first scene

		Scene scene = new Scene(new Group(), guiWidth, guiHeight);

		return scene;
	}

	public void startSimulation(String[] args) {
		launch(args);
	}

}
