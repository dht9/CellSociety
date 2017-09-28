package cell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * This is the cell superclass that has abstract cell method shared by all cell subclass
 * 
 * @author estellehe
 *
 */
public abstract class Cell {
	private static final String TORUS = "torus";
	private static final String NORMAL = "normal";
	
	private int myrow;
	private int mycol;
	private int mystate;
	private int[] mygrid;
	private Map<String,Double> myParaMap;
	private int mynextRow;
	private int mynextCol;
	private int mynextState;
	private NeighborCell myNeighborCell;
	private ArrayList<int[]> myAdjacent;
	private int myNeighborType;
	private boolean myIsTorus;
	
	/**
	 * constructor for cell superclass
	 * @param row
	 * @param column
	 * @param state
	 * @param gridSize is the {row, col} of current grid, used to determine whether on the edge
	 * @param paraList, the list of parameters for simulation
	 */
	public Cell(int row, int column, int state, int[] gridSize, Map<String,Double> paraMap, String edgeType) {
		myrow = row;
		mycol = column;
		mystate = state;
		mygrid = gridSize;
		myParaMap = paraMap;
		myNeighborCell = new NeighborCell(myNeighborType, myIsTorus, this);
		myAdjacent = myNeighborCell.adjacentPos();
		switch(edgeType) {
			case TORUS:
				myIsTorus = true;
				break;
			case NORMAL:
				myIsTorus = false;
				break;
			default:
				myIsTorus = false;
		}
	}
	
	/**
	 * access column
	 * @return column
	 */
	public int column() {
		return mycol;
	}
	
	/**
	 * access grid size {row, col}
	 * @return grid size
	 */
	public int[] grid() {
		return mygrid;
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
	 * access next state
	 * @return state
	 */
	public int nextstate() {
		return mynextState;
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
	public void updateInfo(ArrayList<Cell> neighborlist, ArrayList<int[]> emptyPos) {
		mynextState = mystate;
		mynextRow = myrow;
		mynextCol = mycol;
	}
	
	/**
	 * execute the update information on the cell
	 */
	public void update(ArrayList<Cell> removeCellList, ArrayList<Cell> newCellList, ArrayList<int[]> emptyPos) {
		myrow = mynextRow;
		mycol = mynextCol;
		mystate = mynextState;
		myAdjacent = myNeighborCell.adjacentPos();
	}
	
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
