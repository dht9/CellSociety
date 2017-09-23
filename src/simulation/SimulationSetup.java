package simulation;

import java.io.File;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import cell.CellManager;
import javafx.scene.paint.Color;

import config.XMLReader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import visualization.VisualizeGrid;

/**
 * This class initializes the interface for the simulation.
 * 
 * @author DavidTran, RyanChung
 *
 */
public class SimulationSetup extends Application {

	private ResourceBundle myResources = ResourceBundle.getBundle("resources/Text");
	private final int guiSize = 700;
	private SimulationLoop mySimulationLoop;
	private XMLReader xmlReader;

	private Button chooseXMLButton;
	private Button startButton;
	private Button pauseButton;
	private Button stepButton;

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

		HBox btnPanel = new HBox(75);

		chooseXMLButton = makeButton("ChooseXMLCommand", event -> openXML(s, scene));
		startButton = makeButton("PlayCommand", event -> play());
		pauseButton = makeButton("PauseCommand", event -> pause());
		stepButton = makeButton("StepCommand", event -> step());

		btnPanel.getChildren().addAll(chooseXMLButton, startButton, pauseButton, stepButton);

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
		String currentPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/src/resources";
		fileChooser.setInitialDirectory(new File(currentPath));
		FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("(*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extentionFilter);
		File file = fileChooser.showOpenDialog(s);

		if (file != null) {

			xmlReader = new XMLReader(file);

			mySimulationLoop.setXMLReader(xmlReader);

			newGrid(scene);

			// initialize myCellList
			CellManager manager = new CellManager();
			manager.initialize(xmlReader.createStateGrid(), xmlReader.setSimulationType());
		}

	}

	/**
	 * Makes new grid.
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

	public void startSimulation(String[] args) {
		launch(args);
	}

}
