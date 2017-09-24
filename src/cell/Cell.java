package cell;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This is the cell superclass that has abstract cell method shared by all cell subclass
 * 
 * @author estellehe
 *
 */
public abstract class Cell {
	protected int myrow;
	protected int mycol;
	protected int mystate;
	protected int[] mygrid;
	protected double[] myParaList;
	protected int mynextRow;
	protected int mynextCol;
	protected int mynextState;
	protected NeighborCell myNeighborCell;
	protected ArrayList<int[]> myAdjacent;
	
	/**
	 * constructor for cell superclass
	 * @param row
	 * @param column
	 * @param state
	 * @param gridSize is the {row, col} of current grid, used to determine whether on the edge
	 * @param paraList, the list of parameters for simulation
	 */
	public Cell(int row, int column, int state, int[] gridSize, double[] paraList) {
		myrow = row;
		mycol = column;
		mystate = state;
		mygrid = gridSize;
		myParaList = paraList;
	}
	
	/**
	 * access column
	 * @return column
	 */
	public int column() {
		return mycol;
	}
	
	/**
	 * access row
	 * @return row
	 */
	public int row() {
		return myrow;
	}
	
	/**
	 * access state
	 * @return state
	 */
	public int state() {
		return mystate;
	}
	
	/**
	 * check if the cell is at edge
	 * @return whether this cell is at edge
	 */
	public boolean isEdge() {
		return (myrow == 0 || mycol == 0 || myrow == mygrid[0]-1 || mycol == mygrid[1]-1);
	}
	
	/**
	 * update cell position and state depending on the neighborlist input
	 * 
	 * store update info
	 * @param neighborlist
	 */
	public abstract void updateInfo(ArrayList<Cell> neighborlist);
	
	/**
	 * execute the update information on the cell
	 */
	public abstract void update(Iterator<Cell> cellIter, ArrayList<Cell> newCellList);
	
	/**
	 * check if certain cell is the neighbor of this cell based on the rules
	 * 
	 * @param other
	 * @return whether other cell is a neighbor
	 */
	public boolean isNeighbor(Cell other) {
		return myNeighborCell.isNeighbor(other);
	}
	
	/**
	 * return a list of empty adjacent position in {row, col} that can be moved into
	 * 
	 * @param neighborlist
	 * @return list of empty adjacent position
	 */
	protected ArrayList<int[]> emptyNeighbor (ArrayList<Cell> neighborlist) {
		return myNeighborCell.emptyNeighbor(neighborlist);
	}
	
	/**
	 * get the adjacent position list, to accelerate speed
	 * 
	 * @return adjacent position list
	 */
	public ArrayList<int[]> adjacent() {
		return myAdjacent;
	}

}
