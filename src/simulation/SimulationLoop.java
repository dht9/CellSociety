package simulation;
import java.io.File;
import java.util.ResourceBundle;

import config.XMLReader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
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
	private ResourceBundle myResources = ResourceBundle.getBundle("resources/Text");;
	
	
	/**
	 * Constructor, give simulation loop a scene
	 */
	public SimulationLoop(Stage s, Scene scene, int width, int height, XMLReader xmlReader) {
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
	
	/**
	 * Method inspired by BrowserView.java
	 * 
	 * @param scene
	 * @return
	 */
	public Node makeButtonPanel(Stage s) {
	
		HBox btnPanel = new HBox();
		
		Button chooseXMLButton = makeButton("ChooseXMLCommand", event -> openXML(s));
		
		Button submitXMLButton = makeButton("SubmitXMLCommand", event -> submitXML());
		
		Button startButton = makeButton("StartCommand", event -> play());
		
		Button pauseButton = makeButton("PauseCommand", event -> pause());
		
		Button stepButton = makeButton("StepCommand", event -> stepForward());
		
		btnPanel.getChildren().addAll(chooseXMLButton, submitXMLButton, startButton, pauseButton, stepButton);

		return btnPanel;
		
	}
	/**
	 * This method inspired by Browserview.java by Robert Duvall
	 * 
	 * @param property text displayed on button
	 * @param handler actions if button pressed
	 * @return
	 */
	private Button makeButton (String property, EventHandler<ActionEvent> handler) {
		Button btn = new Button();
		String label = myResources.getString(property);
		btn.setText(label);
		btn.setOnAction(handler);
		return btn;
	}
	
	/**
	 * Button Event Handler Methods
	 */
	private void openXML(Stage s) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(s);
		
		if (file != null) {
			XMLReader xmlReader = new XMLReader(file);
		}
	}
	
	private void submitXML() {
		
	}
	
	private void play() {
		
	}
	
	private void pause() {
		
	}
	
	private void stepForward() {
		
	}
	


}
