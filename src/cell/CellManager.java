package cell;

import java.util.ArrayList;
import java.util.Iterator;

public class CellManager {
	private ArrayList<Cell> myCellList;
	private String myType;
	
	/**
	 * constructor for cell manager, initialize mycelllist
	 */
	public CellManager(String type) {
		myCellList = new ArrayList<Cell>();
		myType = type;
		//TODO change type to enum
	}
	
	/**
	 * get the neighbor cell list for input cell
	 * 
	 * @param current
	 * @return
	 */
	private ArrayList<Cell> getNeighbor(Cell current) {
		ArrayList<Cell> neighborList = new ArrayList<Cell>();
		Iterator<Cell> cellIter = myCellList.iterator();
		if (cellIter.hasNext()) {
			Cell next = cellIter.next();
			if (current.isNeighbor(next)) {
				neighborList.add(next);
			}
		}
		return neighborList;
	}
	
	public 

}
