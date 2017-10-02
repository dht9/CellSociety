package simulation;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;

import config.XMLFireRandom;
import config.XMLGameOfLifeRandom;
import config.XMLPredatorPreyRandom;
import config.XMLReader;
import config.XMLSegregationRandom;
import config.XMLWriter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import visualization.MakeSlider;
import visualization.VisualizeGrid;

/**
 * Class that creates user controls (buttons, sliders)
 * 
 * @author DavidTran
 *
 */
public class UserControlPanel extends HBox {

	private ResourceBundle myResources = ResourceBundle.getBundle("resources/Text");

	private Button chooseXMLButton;
	private Button startButton;
	private Button pauseButton;
	private Button stepButton;
	private Button resetButton;
	private Button saveButton;
	private Button randomizeButton;
	private Button gridOutlineButton;

	private SimulationLoop mySimulationLoop;
	private MakeSlider makeSlider;
	private XMLReader xmlReader;
	private XMLWriter xmlWriter;
	private Scene scene;
	private Stage s;
	private VisualizeGrid myGrid;

	/**
	 * Constructor that creates buttons that can control the simulation.
	 * 
	 * @param st
	 * @param sc
	 * @param sLoop
	 * @param xReader
	 * @param xWriter
	 * @param spacing
	 */
	public UserControlPanel(Stage st, Scene sc, SimulationLoop sLoop, XMLReader xReader, XMLWriter xWriter, int spacing) {

		s = st;
		scene = sc;
		mySimulationLoop = sLoop;
		xmlReader = xReader;
		xmlWriter = xWriter;
		
		this.setSpacing(spacing);
		
		createButtons();

	}

	/**
	 * Add buttons to pane.
	 */
	private void createButtons() {

		chooseXMLButton = makeButton("ChooseXMLCommand", event -> openXML(s, scene));
		startButton = makeButton("PlayCommand", event -> play());
		pauseButton = makeButton("PauseCommand", event -> pause());
		stepButton = makeButton("StepCommand", event -> step());
		resetButton = makeButton("ResetCommand", event -> reset(scene));
		saveButton = makeButton("SaveCommand", event -> save());
		randomizeButton = makeButton("RandomizeCommand", event -> randomize(scene));
		gridOutlineButton = makeButton("GridOutlineCommand", event -> toggleGridLines());

		makeSlider = new MakeSlider(mySimulationLoop.getFPS());
		Slider slider = makeSlider.getSlider();
		mySimulationLoop.setMakeSlider(makeSlider);

		this.getChildren().addAll(chooseXMLButton, startButton, pauseButton, stepButton, resetButton, saveButton,
				randomizeButton, gridOutlineButton, slider);
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
				// else if (fileName.indexOf("rps") != -1)
				// xmlReader = new XMLRPSRandom(file);
			} else
				xmlReader = new XMLReader(file);

			newGrid(scene);
			mySimulationLoop.setNewSimulationParameters(xmlReader);

		}

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
		mySimulationLoop.pause();
		
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
		newGrid(scene);
		mySimulationLoop.setNewSimulationParameters(xmlReader);

	}

	// allow user to toggle grid lines
	private void toggleGridLines() {
		if (myGrid != null) {
			myGrid.changeOutline();
		}
	}

	/**
	 * Makes new simulation grid.
	 * 
	 * @param scene
	 * 
	 */
	private void newGrid(Scene scene) {

		myGrid = new VisualizeGrid(xmlReader);
		BorderPane root = (BorderPane) scene.getRoot();
		root.setCenter(myGrid);

		mySimulationLoop.setVisualizeGrid(myGrid);
	}

}
