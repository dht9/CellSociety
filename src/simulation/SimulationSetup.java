package simulation;

//import config.XMLRPSRandom;
import config.XMLReader;
import config.XMLWriter;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

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
	private static final int GUI_WIDTH = 900;
	private static final int GUI_HEIGHT = 700;

	private SimulationLoop mySimulationLoop;
	private XMLReader xmlReader;
	private XMLWriter xmlWriter;
	private GridPane startingGrid;
	private UserControlPanel ctrlPanel;

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

		Node popPanel = makePopulationPanel(s, scene);
		root.setRight(popPanel);
		root.setBottom(btnPanel);
		root.setMargin(btnPanel, new Insets(50));
		root.setCenter(startingGrid);

		return scene;
	}

	private Node makePopulationPanel(Stage s, Scene scene) {

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

		ctrlPanel = new UserControlPanel(s, scene, mySimulationLoop, xmlReader, xmlWriter, 10);
		return ctrlPanel;
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

	public void startSimulation(String[] args) {
		launch(args);
	}

}
