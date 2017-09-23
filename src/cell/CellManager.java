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
	public CellManager() {
		myCellList = new ArrayList<Cell>();
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
	
	/**
	 * initialize all cells based on the stateArray parsed by XML file and store them in mycelllist
	 * 
	 * @param stateArray
	 * @param type
	 */
	public void initialize(int[][] stateArray, String simulationType) {
		int row = stateArray.length;
		int col = stateArray[0].length;
		myType = simulationType;
		int[] gridSize = {row, col};
		for (int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				Cell current = createCell(i, j, stateArray[i][j], gridSize, simulationType);
				myCellList.add(current);
			}
		}
	}
	
	/**
	 * create cell based on the designated type
	 * 
	 * @param row
	 * @param col
	 * @param state
	 * @param gridSize
	 * @param type
	 * @return cell
	 */
	private Cell createCell(int row, int col, int state, int[] gridSize, String simulationType) {
		Cell current;
		switch(simulationType) {
			case "GameOfLife":
				current = new GameofLife(row, col, state, gridSize);
				break;
			default:
				current = new GameofLife(row, col, state, gridSize);
				break;
		}
		return current;
	}

}
