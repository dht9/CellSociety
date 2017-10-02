package cell;

import java.util.List;
import java.util.Map;

/**
 * This is the cell superclass that has abstract cell method shared by all cell subclass
 * 
 * @author estellehe
 *
 */
public abstract class Cell {
	private static final String TORUS = "toroidal";
	private static final String NORMAL = "straight";
	
	private int myrow;
	private int mycol;
	private int mystate;
	private int[] mygrid;
	private Map<String,Double> myParaMap;
	private int mynextRow;
	private int mynextCol;
	private int mynextState;
	private NeighborCell myNeighborCell;
	private List<int[]> myAdjacent;
	private int myNeighborType;
	private boolean myIsTorus;
	private String myEdgeType;
	
	/**
	 * constructor for cell superclass
	 * @param row
	 * @param column
	 * @param state
	 * @param gridSize is the {row, col} of current grid, used to determine whether on the edge
	 * @param paraList, the list of parameters for simulation
	 */
	public Cell(int row, int column, int state, int[] gridSize, Map<String,Double> paraMap, String edgeType, int neighborType) {
		myrow = row;
		mycol = column;
		mystate = state;
		mygrid = gridSize;
		myEdgeType = edgeType;
		myParaMap = paraMap;
		myNeighborType = neighborType;
		switch(myEdgeType) {
		case TORUS:
			myIsTorus = true;
			break;
		case NORMAL:
			myIsTorus = false;
			break;
		default:
			myIsTorus = false;
	}
		myNeighborCell = new NeighborCell(myNeighborType, myIsTorus, this);
		myAdjacent = myNeighborCell.adjacentPos();
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
	 * access neighborType
	 * @return neighborType
	 */
	public int neighborType() {
		return myNeighborType;
	}
	
	/**
	 * access paraMap
	 * @return paraMap
	 */
	public Map<String, Double> paraMap() {
		return myParaMap;
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
	 * @return next state
	 */
	public int nextstate() {
		return mynextState;
	}
	
	/**
	 * access next row
	 * @return next row
	 */
	public int nextrow() {
		return mynextRow;
	}
	
	/**
	 * set next col
	 * @param col
	 */
	protected void setNextCol(int col) {
		mynextCol = col;
	}
	
	/**
	 * set next state
	 * @param state
	 */
	protected void setNextState(int state) {
		mynextState = state;
	}
	
	/**
	 * set next row
	 * @param row
	 */
	protected void setNextRow(int row) {
		mynextRow = row;
	}
	
	/**
	 * access next column
	 * @return next column
	 */
	public int nextcol() {
		return mynextCol;
	}
	
	/**
	 * access edge type
	 * @return edge type
	 */
	public String edgeType() {
		return myEdgeType;
	}
	
//	/**
//	 * check if the cell is at edge
//	 * @return whether this cell is at edge
//	 */
//	public boolean isVerEdge() {
//		return (myrow == 0 || myrow == mygrid[0]-1);
//	}
//	
//	public boolean isSideEdge() {
//		return (mycol == 0 || mycol == mygrid[1]-1);
//	}
	
	/**
	 * update cell position and state depending on the neighborlist input
	 * 
	 * store update info
	 * @param neighborlist
	 */
	public void updateInfo(List<Cell> neighborlist, List<int[]> emptyPos) {
		mynextState = mystate;
		mynextRow = myrow;
		mynextCol = mycol;
	}
	
	/**
	 * execute the update information on the cell
	 */
	public void update(List<Cell> removeCellList, List<Cell> newCellList, List<int[]> emptyPos) {
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
	protected List<int[]> emptyNeighbor (List<Cell> neighborlist) {
		return myNeighborCell.emptyNeighbor(neighborlist);
	}
	
	/**
	 * get the adjacent position list, to accelerate speed
	 * 
	 * @return adjacent position list
	 */
	public List<int[]> adjacent() {
		return myAdjacent;
	}
	
	/**
	 * get random index
	 * @param size
	 * @return random index
	 */
	public int randomIndex(int size) {
		return myNeighborCell.randomIndex(size);
	}

}
