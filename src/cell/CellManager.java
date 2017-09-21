package cell;

import java.util.ArrayList;

public class CellManager {
	private ArrayList<Cell> myCellList;
	private String myType;
	
	/**
	 * constructor for cell manager, initialize mycelllist
	 */
	public CellManager(String type) {
		myCellList = new ArrayList<Cell>();
		myType = type;
	}
	
	private ArrayList<Cell> getNeighbor(Cell current) {
		for (Cell each: myCellList) {
			
		}
		return myCellList;
	}

}
