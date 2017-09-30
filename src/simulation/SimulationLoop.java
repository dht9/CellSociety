package simulation;

import java.util.List;
import java.util.Map;

import cell.Cell;
import cell.CellManager;
import config.XMLReader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import visualization.MakeSlider;
import visualization.RectangleCell;
import visualization.VisualizeGrid;

/**
 * This class updates the GUI to simulate a CA model with parameters defined in
 * an XML file chosen by the user.
 * 
 * @author DavidTran
 *
 */
public class SimulationLoop {

	private static final int MAX_FRAMES_PER_SECOND = 30;
	
	private int guiWidth;
	private int guiHeight;
	private Stage myStage;
	private Scene myScene;
	private Timeline animation;
	private KeyFrame frame;
	private boolean shouldRun;
	private MakeSlider myMakeSlider;
	
	private XMLReader xmlReader;
	private String simulationType;
	private String edgeType;
	private int neighborType;
	
	private Map<Integer, Color> colorMap;
	private Map<Integer, String> stateNameMap;
	private Map<String, Double> parameterMap;
	
	private int[][] stateGrid;
	private VisualizeGrid myGrid;

	private CellManager manager;

	/**
	 * Constructor, initializes and starts the simulation loop.
	 */
	public SimulationLoop() {
		frame = new KeyFrame(Duration.millis(1000 / MAX_FRAMES_PER_SECOND), e -> step());
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	public void setGUI(Stage s, Scene scene, int width, int height) {
		myStage = s;
		myScene = scene;
		guiWidth = width;
		guiHeight = height;
		shouldRun = false;
	}

	public void setMakeSlider(MakeSlider mSlider) {
		myMakeSlider = mSlider;
	}

	/**
	 * Initialize data and manager for each new simulation.
	 * 
	 * @param xmlReaderInput
	 */
	public void setNewSimulationParameters(XMLReader xmlReaderInput) {
		xmlReader = xmlReaderInput;
		colorMap = xmlReaderInput.createColorMap();
		stateNameMap = xmlReaderInput.createStateNameMap();
		parameterMap = xmlReaderInput.createParameterMap();
		stateGrid = xmlReaderInput.createStateGrid();
		edgeType = xmlReaderInput.setEdgeType();
		simulationType = xmlReaderInput.setSimulationType();
		neighborType = xmlReaderInput.setNeighborType();
		
		manager = new CellManager();
		
		//need to add neighborType in constructor?
		manager.initialize(stateGrid, edgeType, simulationType, parameterMap, neighborType);
	}

	public void setVisualizeGrid(VisualizeGrid grid) {
		myGrid = grid;
	}


	/**
	 * Primary loop for running each frame of the simulation.
	 */
	public void step() {

		if (shouldRun && xmlReader != null) {
			
			 manager.update();
			 
			 // update all rectangles to empty color
			 for (int i = 0; i < myGrid.getRowSize(); i++) {
				 for (int j = 0; j < myGrid.getColSize(); j++) {
					 myGrid.colorRectangle(i, j, colorMap.get(-1), -1);
				 }
			 }
			 
			 // update appropriate rectangles to non-empty color
			 List<Cell> cellList = manager.cellList();
			 for (Cell cell: cellList) {
				 
				 int row = cell.row();
				 int col = cell.column();
				 int state = cell.state();
				 Color color = colorMap.get(state);
				 
				 myGrid.colorRectangle(row, col, color, state);
			 }
		
		}
		animation.setRate( myMakeSlider.getValue() / MAX_FRAMES_PER_SECOND);
	}

	// start/resume the simulation
	public void play() {
		shouldRun = true;
	}

	// pause the simulation
	public void pause() {
		shouldRun = false;
	}
	
	public int getFPS() {
		return MAX_FRAMES_PER_SECOND;
	}

	public int[][] getCurrentStateGrid() {
		int [][] grid = new int[myGrid.getRowSize()][myGrid.getColSize()];
		for (int row=0; row<myGrid.getRowSize(); row++) {
			for (int col=0; col<myGrid.getRowSize(); col++) {
				RectangleCell rect = (RectangleCell) myGrid.getRectWithCellPosition(row, col);
				grid[row][col] = rect.getState();
			}
		}
		return grid;
	}

}
