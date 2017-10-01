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
	private int[][] gridArray;

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
		int[][] gridArray = xml.getStateGrid();
		colorGrid(myColorMap, gridArray);

		setGridLinesVisible(true);

		setAlignment(Pos.CENTER);

		numRows = gridArray.length;
		numCols = gridArray[0].length;

		// set index widths/height for grid
		for (int i = 0; i < this.getRowSize(); i++) {
			this.getRowConstraints().add(new RowConstraints(this.getCellHeight(gridArray)));

		}
		for (int i = 0; i < this.getColSize(); i++) {
			this.getColumnConstraints().add(new ColumnConstraints(this.getCellWidth(gridArray)));
		}
	}

	private void colorGrid(Map<Integer, Color> myColorMap, int[][] gridArray) {

		for (int i = 0; i < gridArray.length; i++) {
			for (int j = 0; j < gridArray[0].length; j++) {

				Color color = myColorMap.get(gridArray[i][j]);

				this.add(new RectangleCell(getCellWidth(gridArray), getCellHeight(gridArray), color, gridArray[i][j]), j, i);
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

	private double getCellWidth(int[][] gridArray) {
		return GRID_SIZE / gridArray[0].length;
	}
	
	private double getCellHeight(int[][] gridArray) {
		return GRID_SIZE / gridArray.length;
	}

	public int getRowSize() {
		return numRows;
	}

	public int getColSize() {
		return numCols;
	}

	/**
	 * Removes and adds a new rectangle with a color at a specified index in the
	 * grid.
	 * 
	 * @param row
	 * @param col
	 * @param color
	 */
	public void colorRectangle(int row, int col, Color color, int state) {
		RectangleCell rect = (RectangleCell) this.getRectWithCellPosition(row, col);
		rect.setState(state);
		rect.setFill(color);
	}

}
