package cell;

import java.util.ArrayList;
import java.util.Iterator;

public class CellManager {
	private ArrayList<Cell> myCellList;
	private String myType;
	private int[] myGridSize;
	private double[] myParaList;
	
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
			cellIter.next().update(myCellList);
		}
	}
	
	/**
	 * initialize all cells based on the stateArray parsed by XML file and store them in mycelllist
	 * 
	 * @param stateArray
	 * @param type
	 * @param paraList
	 */
	public void initialize(int[][] stateArray, String type, double[] paraList) {
		int row = stateArray.length;
		int col = stateArray[0].length;
		myType = type;
		myGridSize[0] = row;
		myGridSize[1] = col;
		myParaList = paraList;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				Cell current = createCell(row, col, stateArray[i][j]);
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
	public Cell createCell(int row, int col, int state) {
		Cell current;
		switch(myType) {
			case "GameOfLife":
				current = new GameofLife(row, col, state, myGridSize, myParaList);
				break;
			default:
				current = new GameofLife(row, col, state, myGridSize, myParaList);
				break;
		}
		return current;
	}

}
