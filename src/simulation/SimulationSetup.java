package simulation;

import java.io.File;
import java.util.ResourceBundle;

import config.XMLReader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This class initializes the interface for the simulation.
 * 
 * @author DavidTran
 *
 */
public class SimulationSetup extends Application {

	private ResourceBundle myResources = ResourceBundle.getBundle("resources/Text");
	private final int guiSize = 500;
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

		mySimulationLoop = new SimulationLoop(s, scene, guiSize, guiSize, xmlReader);

		mySimulationLoop.start();

	}

	/**
	 * Initializes the scene based off user input of XML.
	 * 
	 * @param s
	 *            stage for the gui
	 * @param guiWidth
	 * @param guiHeight
	 * @return scene to be displayed on the stage
	 */
	public Scene setupScene(Stage s, int guiWidth, int guiHeight) {

		BorderPane root = new BorderPane();

		root.setBottom(makeButtonPanel(s));

		Scene scene = new Scene(root, guiWidth, guiHeight);

		return scene;
	}

	/**
	 * Method inspired by BrowserView.java
	 * 
	 * @param scene
	 * @return
	 */
	private Node makeButtonPanel(Stage s) {

		HBox btnPanel = new HBox();

		chooseXMLButton = makeButton("ChooseXMLCommand", event -> openXML(s));

		startButton = makeButton("StartCommand", event -> play());

		pauseButton = makeButton("PauseCommand", event -> pause());

		stepButton = makeButton("StepCommand", event -> step());
		

		btnPanel.getChildren().addAll(chooseXMLButton, startButton, pauseButton, stepButton);

		return btnPanel;

	}

	/**
	 * This method inspired by Browserview.java by Robert Duvall
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
	 * 
	 * Button Event Handler Methods
	 * 
	 */
	private void openXML(Stage s) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.setInitialDirectory(
	            new File(System.getProperty("/cellsociety_team10/src/resources")));
		File file = fileChooser.showOpenDialog(s);

		if (file != null) {
			xmlReader = new XMLReader(file);
		}
	}

	private void play() {

	}

	private void pause() {

	}

	private void step() {

	}

	public void startSimulation(String[] args) {
		launch(args);
	}

}
