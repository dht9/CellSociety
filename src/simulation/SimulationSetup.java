package simulation;

import java.io.File;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import config.XMLFireRandom;
import config.XMLGameOfLifeRandom;
import config.XMLPredatorPreyRandom;
//import config.XMLRPSRandom;
import config.XMLReader;
import config.XMLSegregationRandom;
import config.XMLWriter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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

	private static final int STARTING_ROWS = 10;
	private static final int STARTING_COLS = 10;
	private static final int STARTING_CELL_SIZE = 55;
	private static final int GUI_WIDTH = 800;
	private static final int GUI_HEIGHT = 700;

	private ResourceBundle myResources = ResourceBundle.getBundle("resources/Text");

	private SimulationLoop mySimulationLoop;
	private XMLReader xmlReader;
	private XMLWriter xmlWriter;
	private MakeSlider makeSlider;
	private GridPane startingGrid;

	private Button chooseXMLButton;
	private Button startButton;
	private Button pauseButton;
	private Button stepButton;
	private Button resetButton;
	private Button saveButton;
	private Button randomizeButton;
	private Button gridOutlineButton;
	
	private VBox vbox;

	/**
	 * Initialize stage, scene, and simulation loop.
	 */
	@Override
	public void start(Stage s) {

		mySimulationLoop = new SimulationLoop();

		Scene scene = setupScene(s, GUI_WIDTH, GUI_HEIGHT);
		s.setTitle("Cell Society");
		s.setScene(scene);
		s.show();

		mySimulationLoop.setGUI(s, scene, GUI_WIDTH, GUI_HEIGHT);
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
		startingGrid = makeEmptyGrid();
		Node btnPanel = makeButtonPanel(s, scene, startingGrid);
		
		
		Node popPanel = makePopulationPanel(s,scene);
		root.setRight(popPanel);
		root.setBottom(btnPanel);
		root.setMargin(btnPanel, new Insets(50));
		root.setCenter(startingGrid);

		return scene;
	}

	
	private Node makePopulationPanel (Stage s, Scene scene) {
		
		vbox = new VBox(8);
		mySimulationLoop.setVBox(vbox);
		
		return vbox;
		
	}
	/**
	 * Method inspired by makeNavigationPanel() in BrowserView.java by Robert Duvall
	 * 
	 * @param s
	 * @param scene
	 * @return
	 */
	private Node makeButtonPanel(Stage s, Scene scene, GridPane grid) {

		chooseXMLButton = makeButton("ChooseXMLCommand", event -> openXML(s, scene));
		startButton = makeButton("PlayCommand", event -> play());
		pauseButton = makeButton("PauseCommand", event -> pause());
		stepButton = makeButton("StepCommand", event -> step());
		resetButton = makeButton("ResetCommand", event -> reset(scene));
		saveButton = makeButton("SaveCommand", event -> save());
		randomizeButton = makeButton("RandomizeCommand", event -> randomize(scene));
//		gridOutlineButton = makeButton("gridOutlineCommand", event -> pause());

		makeSlider = new MakeSlider(mySimulationLoop.getFPS());
		Slider slider = makeSlider.getSlider();
		mySimulationLoop.setMakeSlider(makeSlider);

		HBox btnPanel = new HBox(10);
		btnPanel.getChildren().addAll(chooseXMLButton, startButton, pauseButton, stepButton, resetButton, saveButton,
				randomizeButton, slider);

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
		String currentPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/data";
		fileChooser.setInitialDirectory(new File(currentPath));
		FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("(*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extentionFilter);
		File file = fileChooser.showOpenDialog(s);

		if (file != null) {
			
			String fileName = file.getName().toLowerCase();
			
			if (fileName.indexOf("random") != -1) {
				if (fileName.indexOf("fire") != -1)
					xmlReader = new XMLFireRandom(file);
				else if (fileName.indexOf("gameoflife") != -1)
					xmlReader = new XMLGameOfLifeRandom(file);
				else if (fileName.indexOf("segregation") != -1)
					xmlReader = new XMLSegregationRandom(file);
				else if (fileName.indexOf("predatorprey") != -1)
					xmlReader = new XMLPredatorPreyRandom(file);
//				else if (fileName.indexOf("rps") != -1)
//					xmlReader = new XMLRPSRandom(file);
			} else
				xmlReader = new XMLReader(file);
			
			newGrid(scene);
			mySimulationLoop.setNewSimulationParameters(xmlReader);

			
		}

	}
	
	private void outline(GridPane grid) {
		if (grid.isGridLinesVisible()) {
			grid.setGridLinesVisible(false);
		}
		else {
			grid.setGridLinesVisible(true);
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
			stopAndSetup(scene);
		}
	}

	// save simulation configs to new XML file
	private void save() {
		if (xmlReader != null) {

			String filePath;
			TextInputDialog dialog = new TextInputDialog("");
			dialog.setTitle("Save Simulation Configurations Dialog");
			dialog.setHeaderText("File will be located in 'data' folder");
			dialog.setContentText("Name of file (no extension):");

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				filePath = Paths.get(".").toAbsolutePath().normalize().toString() + "/data/" + result.get() + ".xml";
				xmlWriter = new XMLWriter();
				xmlWriter.setNewSimulationParameters(xmlReader);
				xmlWriter.setNewStateGrid(mySimulationLoop.getCurrentStateGrid());

				xmlWriter.writeToXML();
				xmlWriter.outputXML(filePath);
			}
		}
	}

	// randomize initial states
	private void randomize(Scene scene) {
		if (xmlReader != null) {
			xmlReader.createRandomStateGrid();
			stopAndSetup(scene);
		}
	}

	// stop simulation and reset grid
	private void stopAndSetup(Scene scene) {
		mySimulationLoop.pause();
		mySimulationLoop.setNewSimulationParameters(xmlReader);
		newGrid(scene);
	}

	public void startSimulation(String[] args) {
		launch(args);
	}

}
