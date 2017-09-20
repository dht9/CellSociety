package cell;

/**
 * This is the cell superclass that has abstract cell method shared by all cell subclass
 * 
 * @author estellehe
 *
 */
public abstract class Cell {
	private int myrow;
	private int mycol;
	private String mystate;
	private int[] mygrid;
	private int mynextRow;
	private int mynextCol;
	private int mynextState;
	
	/**
	 * constructor for cell superclass
	 * @param row
	 * @param column
	 * @param state
	 * @param gridSize is the {row, col} of current grid, used to determine whether on the edge
	 */
	public Cell(int row, int column, String state, int[] gridSize) {
		myrow = row;
		mycol = column;
		mystate = state;
		mygrid = gridSize;
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
	public String state() {
		return mystate;
	}
	
	/**
	 * check if the cell is at edge
	 * @return
	 */
	public boolean isEdge() {
		return (myrow == 1 || mycol == 1 || myrow == mygrid[0] || mycol == mygrid[1]);
	}
	

}
