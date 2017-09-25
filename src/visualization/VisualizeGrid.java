package visualization;

import java.util.HashMap;
import java.util.Map;

import config.XMLReader;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class initializes the grid for the simulation.
 * 
 * @author RyanChung, DavidTran
 *
 */
public class VisualizeGrid extends GridPane {

	private final int GRID_SIZE = 550;
	private int numRows;
	private int numCols;

	/**
	 * Constructor for VisualizeGrid Class.
	 * 
	 * iterates through CellGrid and returns a GridPane with corresponding
	 * color(state) for each cell
	 * 
	 * @param xml
	 * 
	 * @return
	 */
	public VisualizeGrid(XMLReader xml) {

		Map<Integer, Color> myColorMap = xml.createColorMap();
		int[][] gridArray = xml.createStateGrid();
    
		colorGrid(myColorMap, gridArray);

		setGridLinesVisible(true);

		setAlignment(Pos.CENTER);

		numRows = gridArray.length;
		numCols = numRows;

		// set index widths/height for grid
		for (int i = 0; i < this.getSize(); i++) {
			this.getRowConstraints().add(new RowConstraints(this.getCellSize(gridArray)));
			this.getColumnConstraints().add(new ColumnConstraints(this.getCellSize(gridArray)));
		}
	}
	

	private void colorGrid(Map<Integer, Color> myColorMap, int[][] gridArray) {

		for (int i = 0; i < gridArray.length; i++) {
			for (int j = 0; j < gridArray.length; j++) {

				Color color = myColorMap.get(gridArray[i][j]);

				this.add(new Rectangle(getCellSize(gridArray), getCellSize(gridArray), color), j, i);
			}
		}
	}

	/**
	 * Get the rectangle with the same row/column index of a cell
	 * 
	 * @param row
	 *            index of cell
	 * @param column
	 *            index of cell
	 * @param gridPane
	 *            contains
	 * @return rectangle with the cell's coordinates
	 */

	public Node getRectWithCellPosition(int row, int column) {
		Node rect = null;
		ObservableList<Node> children = this.getChildren();
		for (Node child : children) {
			if (VisualizeGrid.getRowIndex(child) == row && VisualizeGrid.getColumnIndex(child) == column) {
				rect = child;
				break;
			}
		}
		return rect;
	}

	public double getCellSize(int [][] gridArray) {
		return GRID_SIZE / gridArray.length;
	}

	// assumes square grid
	public int getSize() {
		return numRows;
	}

}
