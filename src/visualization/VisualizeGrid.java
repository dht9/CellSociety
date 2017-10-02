package visualization;

import java.util.HashMap;
import java.util.Map;

import cell.CellManager;
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
	private boolean showOutline = false;
	private Map<Integer, Color> myColorMap;
	private CellManager myCellManager;

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

		myColorMap = xml.createColorMap();
		int[][] gridArray = xml.getStateGrid();
		colorGrid(myColorMap, gridArray);

		setGridLinesVisible(showOutline);

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
//				if (myCellManager == null) {
//					System.out.println("SHIT");
//				}
				this.add(new RectangleCell(i, j, getCellWidth(gridArray), getCellHeight(gridArray), color,
						gridArray[i][j], myColorMap, myCellManager), j, i);
			}
		}
	}

	public VisualizeGrid() {
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

	public void setCellManager(CellManager c) {
		myCellManager = c;
		System.out.println(myCellManager.toString());
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
	
	public CellManager getManager() {
		return myCellManager;
	}

	public void changeOutline() {
		if (showOutline) {
			showOutline = false;
		} else {
			showOutline = true;
		}
	}

}
