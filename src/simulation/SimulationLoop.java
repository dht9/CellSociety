package simulation;

import java.util.ArrayList;

import cell.Cell;
import cell.CellManager;
import config.XMLReader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
	private boolean shouldRun;
	private XMLReader xmlReader;
	
	
	/**
	 * Constructor, give simulation loop a scene
	 */
	public SimulationLoop(Stage s, Scene scene, int width, int height) {
		myStage = s;
		myScene = scene;
		guiWidth = width;
		guiHeight = height;
		shouldRun = false;
	}
	
	public void setXMLReader(XMLReader xmlReaderInput) {
		xmlReader = xmlReaderInput;
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
	public void step() {
		
		if (shouldRun && xmlReader != null) {
			
			
			CellManager manager = new CellManager();
			manager.update();
			ArrayList<Cell> cellList = manager.cellList();
			
			for (Cell cell: cellList) {
				int row = cell.row();
				int col = cell.column();
				int state = cell.state();
				
				
			}
			
			// do stuff
			System.out.println("running");
		}
		
	}

	// start/resume the simulation
	public void play() {
		shouldRun = true;
	}
	
	// pause the simulation
	public void pause() {
		shouldRun = false;
	}

}
