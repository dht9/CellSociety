package visualization;

import java.util.HashMap;
import java.util.Map;

import config.XMLReader;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class initializes the grid for the simulation.
 * 
 * @author RyanChung, DavidTran
 *
 */
public class VisualizeGrid extends GridPane {

	private XMLReader myXML;
	private final int CELL_SIZE = 55;
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
	public VisualizeGrid(XMLReader xml){

		Map<Integer, Color> myColorMap = xml.createColorMap();
		int[][] gridArray = xml.createCellGrid();

		colorGrid(this, myColorMap, gridArray);
		
		setGridLinesVisible(true);

		setAlignment(Pos.CENTER);
		
		numRows = gridArray.length;
		numCols = numRows;
	}

	private void colorGrid(GridPane myGrid, Map<Integer, Color> myColorMap, int[][] gridArray) {
		for (int i = 0; i < gridArray.length; i++) {
			for (int j = 0; j < gridArray.length; j++) {

				Color color = myColorMap.get(gridArray[i][j]);

				myGrid.add(new Rectangle(CELL_SIZE, CELL_SIZE, color), j, i);
			}
		}
	}
	
	/** Get the rectangle with the same row/column index of a cell
	 * 
	 * @param row index of cell
	 * @param column index of cell
	 * @param gridPane contains 
	 * @return rectangle with the cell's coordinates
	 */
	public Node getRectWithCellPosition (int row, int column) {
		
        ObservableList<Node> children = this.getChildren();
        for (Node child : children) {
            if(GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == column) {
                return child;
            }
        }
        return null;
    }
	
	public int getCellSize() {
		return CELL_SIZE;
	}
	
	public int getGridSize() {
		return numRows;
	}

}
