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

		mySimulationLoop = new SimulationLoop();

		Scene scene = mySimulationLoop.setupScene(s, guiSize, guiSize);

		s.setTitle("Cell Society");

		s.setScene(scene);

		s.show();

		mySimulationLoop.start();

	}

	public void startSimulation(String[] args) {
		launch(args);
	}

}
