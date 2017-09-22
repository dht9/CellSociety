package cell;

import java.lang.reflect.Array;
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
	 * 
	 * @return arraylist of of all current cells
	 */
	public ArrayList<Cell> cellList() {
		return myCellList;
	}
	
	/**
	 * get the neighbor cell list for input cell
	 * 
	 * @param current
	 * @return arraylist of neighbor cell
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
	
	/**
	 * update every cell created and stored in myCellList
	 */
	public void update() {
		Iterator<Cell> cellIter = myCellList.iterator();
		if (cellIter.hasNext()) {
			Cell next = cellIter.next();
			next.updateInfo(getNeighbor(next));
		}
		cellIter = myCellList.iterator();
		if(cellIter.hasNext()) {
			cellIter.next().update();
		}
	}
	
	public void initialize(int[][] statusArray) {
		int row = statusArray.length;
		int col = statusArray[0].length;
		int[] gridSize = {row, col};
		for (int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				Cell current = new Cell(row, col, statusArray[row][col], gridSize);
			}
		}
	}
	
	private Cell createCell(String type) {
		switch(type) 
	}

}
