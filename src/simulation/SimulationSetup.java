package simulation;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
		
		addButtons(scene);
		
//		((Group)scene.getRoot()).getChildren().addAll();

		return scene;
	}
	
	public void addButtons(Scene scene) {
	
		HBox hbox = new HBox();
		
		Button chooseXML = new Button("Choose XML File");
		
		Button submit = new Button("Submit");
		
		Button start = new Button("Start");
		
		Button stop = new Button("Pause");
		
		Button step = new Button("Step");
		
		hbox.getChildren().addAll(chooseXML, submit, start, stop, step);

		((Group) scene.getRoot()).getChildren().addAll(hbox);
		
	}

	public void startSimulation(String[] args) {
		launch(args);
	}

}
