package simulation;

import java.io.File;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import config.XMLReader;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import visualization.MakeSlider;
import visualization.VisualizeGrid;

/**
 * This class initializes the interface for the simulation.
 * 
 * @author DavidTran, RyanChung
 *
 */
public class SimulationSetup extends Application {

	private final int STARTING_ROWS = 10;
	private final int STARTING_COLS = 10;
	private final int STARTING_CELL_SIZE = 55;

	private ResourceBundle myResources = ResourceBundle.getBundle("resources/Text");
	private final int guiSize = 700;
	private SimulationLoop mySimulationLoop;
	private XMLReader xmlReader;

	private Button chooseXMLButton;
	private Button startButton;
	private Button pauseButton;
	private Button stepButton;
	private Button resetButton;
	private MakeSlider makeSlider;
	private GridPane startingGrid;

	/**
	 * Initialize stage, scene, and simulation loop.
	 */
	@Override
	public void start(Stage s) {

		mySimulationLoop = new SimulationLoop();

		Scene scene = setupScene(s, guiSize, guiSize);

		s.setTitle("Cell Society");

		s.setScene(scene);

		s.show();

		mySimulationLoop.setGUI(s, scene, guiSize, guiSize);
	}

	/**
	 * Initializes the scene with buttons.
	 * 
	 * @param s
	 *            stage for the gui
	 * @param guiWidth
	 * @param guiHeight
	 * @return scene to be displayed on the stage
	 */
	@SuppressWarnings("static-access")
	public Scene setupScene(Stage s, int guiWidth, int guiHeight) {

		BorderPane root = new BorderPane();

		Scene scene = new Scene(root, guiWidth, guiHeight);

		Node btnPanel = makeButtonPanel(s, scene);

		root.setBottom(btnPanel);

		root.setMargin(btnPanel, new Insets(50));

		root.setCenter(makeEmptyGrid());

		return scene;
	}

	/**
	 * Method inspired by makeNavigationPanel() in BrowserView.java by Robert Duvall
	 * 
	 * @param s
	 * @param scene
	 * @return
	 */
	private Node makeButtonPanel(Stage s, Scene scene) {

		HBox btnPanel = new HBox(10);

		chooseXMLButton = makeButton("ChooseXMLCommand", event -> openXML(s, scene));
		startButton = makeButton("PlayCommand", event -> play());
		pauseButton = makeButton("PauseCommand", event -> pause());
		stepButton = makeButton("StepCommand", event -> step());
		resetButton = makeButton("ResetCommand", event -> reset(scene));

		makeSlider = new MakeSlider(mySimulationLoop.getTimeline());
		Slider slider = makeSlider.createSlider(mySimulationLoop.getFPS());
		mySimulationLoop.setMakeSlider(makeSlider);

		btnPanel.getChildren().addAll(chooseXMLButton, startButton, pauseButton, stepButton, resetButton, slider);

		return btnPanel;

	}

	/**
	 * Method inspired by makeButton() in Browserview.java by Robert Duvall
	 * 
	 * @param property
	 *            text displayed on button
	 * @param handler
	 *            actions if button pressed
	 * @return
	 */
	private Button makeButton(String property, EventHandler<ActionEvent> handler) {

		Button btn = new Button();
		String label = myResources.getString(property);
		btn.setText(label);
		btn.setOnAction(handler);

		return btn;
	}

	public MakeSlider getMakeSlider() {
		return makeSlider;
	}

	/**
	 * Gives user a window to select XML file and creates instance of XMLReader if
	 * file is valid. When valid file is loaded, creates a new grid and visualizes
	 * it on scene
	 * 
	 * @param s
	 *            Stage for file chooser window
	 * @param scene
	 * 
	 */
	private void openXML(Stage s, Scene scene) {

		mySimulationLoop.pause();

		FileChooser fileChooser = new FileChooser();
		String currentPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/src/resources";
		fileChooser.setInitialDirectory(new File(currentPath));
		FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("(*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extentionFilter);
		File file = fileChooser.showOpenDialog(s);

		if (file != null) {

			xmlReader = new XMLReader(file);

			// initializes cell manager
			mySimulationLoop.setNewSimulationParameters(xmlReader);

			newGrid(scene);
		}

	}

	/**
	 * Makes a starting empty grid.
	 * 
	 */
	private GridPane makeEmptyGrid() {

		startingGrid = new GridPane();

		for (int i = 0; i < STARTING_ROWS; i++) {
			for (int j = 0; j < STARTING_COLS; j++) {
				startingGrid.add(new Rectangle(STARTING_CELL_SIZE, STARTING_CELL_SIZE, Color.WHITE), j, i);
			}
		}
		startingGrid.setGridLinesVisible(true);
		startingGrid.setAlignment(Pos.CENTER);

		return startingGrid;
	}

	/**
	 * Makes new simulation grid.
	 * 
	 * @param scene
	 * 
	 */
	private void newGrid(Scene scene) {

		VisualizeGrid newGrid = new VisualizeGrid(xmlReader);
		BorderPane root = (BorderPane) scene.getRoot();
		root.setCenter(newGrid);

		mySimulationLoop.setVisualizeGrid(newGrid);
	}

	// enable looping through step() in SimulationLoop
	private void play() {
		mySimulationLoop.play();
	}

	// disable looping through step()
	private void pause() {
		mySimulationLoop.pause();
	}

	// enable only 1 loop through step() then disable
	private void step() {
		mySimulationLoop.play();
		mySimulationLoop.step();
		mySimulationLoop.pause();
	}

	// reset grid to initial states
	private void reset(Scene scene) {
		if (xmlReader != null) {
			mySimulationLoop.pause();
			mySimulationLoop.setNewSimulationParameters(xmlReader);
			newGrid(scene);
		}
	}

	public void startSimulation(String[] args) {
		launch(args);
	}

}
