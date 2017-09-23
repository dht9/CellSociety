package simulation;

import java.util.ArrayList;

import cell.Cell;
import cell.CellManager;
import config.XMLReader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import visualization.VisualizeGrid;

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
	private VisualizeGrid myGrid;
	
	
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
	
	public void setVisualizeGrid(VisualizeGrid grid) {
		myGrid = grid;
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
			System.out.println("Cell List: " + cellList);
			
			for (Cell cell: cellList) {
				
				int row = cell.row();
				int col = cell.column();
				int state = cell.state();
				Color color = xmlReader.createColorMap().get(state);
				
				colorRectangle(row, col, color);
				
			}
			
			// do stuff
			System.out.println("running");
		}
		
	}

	/**
	 * Removes and adds a new rectangle at a specified index in the grid.
	 * 
	 * @param row
	 * @param col
	 * @param color
	 */
	private void colorRectangle(int row, int col, Color color) {
		
		Node rect = myGrid.getRectWithCellPosition(row,col);
		
		myGrid.getChildren().remove(rect);

        final Rectangle newRect = new Rectangle(0, 0, myGrid.getCellSize(), myGrid.getCellSize()); 
        
        newRect.setFill(color);
        
        //GridPane uses opposite indexes
        myGrid.add(newRect, col, row); 
		
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
