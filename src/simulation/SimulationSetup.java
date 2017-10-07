package simulation;

import config.XMLReader;
import config.XMLWriter;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * This class initializes the interface for the CA simulation.
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
	private GridPane startingGrid;
	private UserControlPanel ctrlPanel;
	private VBox popPanel;

	/**
	 * Initialize the stage, scene, GUI, and simulation loop.
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
	 * Initializes the scene with a control panel, grid, population panel.
	 * 
	 * @param stage
	 * @param width
	 * @param height
	 * @return scene
	 */
	public Scene setupScene(Stage stage, int width, int height) {

		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, width, height);
		ctrlPanel = new UserControlPanel(stage, scene, mySimulationLoop, 10);
		startingGrid = makeEmptyGrid();
		popPanel = makePopulationPanel();

		root.setBottom(ctrlPanel);
		BorderPane.setMargin(ctrlPanel, new Insets(50));
		root.setCenter(startingGrid);
		root.setRight(popPanel);

		return scene;
	}

	/**
	 * Returns a VBox panel for cell population display.
	 * 
	 * @return vbox
	 */
	private VBox makePopulationPanel() {

		VBox vbox = new VBox(8);
		mySimulationLoop.setVBox(vbox);
		return vbox;
	}

	/**
	 * Makes the starting empty, white grid.
	 * 
	 * @return startingGrid
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
	 * Starts the simulation setup.
	 */
	public void startSetup(String[] args) {
		launch(args);
	}

}
