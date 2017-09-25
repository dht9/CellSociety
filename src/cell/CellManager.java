package cell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class CellManager {
	private static final int EMPTY = -1;
	
	private ArrayList<Cell> myCellList;
	private String mySimulationType;
	private String myEdgeType;
	private int[] myGridSize = new int[2];

	Map<String,Double> myParaMap;
	private ArrayList<int[]> myEmptyPos;

	/**
	 * constructor for cell manager, initialize mycelllist
	 */
	public CellManager() {
		myCellList = new ArrayList<Cell>();
		myEmptyPos = new ArrayList<int[]>();
		// TODO change type to enum
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
	private ArrayList<Cell> getNeighborList(Cell current) {
		ArrayList<Cell> neighborList = new ArrayList<Cell>();
		for (Cell other: myCellList) {
			if (current.isNeighbor(other)) {
				neighborList.add(other);
//				System.out.println("Cell neighbor: " + other.state());
			}
		}
//		System.out.println("neighbor");
//		System.out.println(neighborList.size());
		return neighborList;
	}

	/**
	 * update every cell created and stored in myCellList
	 */
	public void update() {
		ArrayList<Cell> newCellList = new ArrayList<Cell>();
		ArrayList<Cell> removeCellList = new ArrayList<Cell>();
		for (Cell current : myCellList) {
			current.updateInfo(getNeighborList(current), myEmptyPos);
		}
		for (Cell current: myCellList) {
			current.update(removeCellList, newCellList, myEmptyPos);
		}
		myCellList.addAll(newCellList);
		myCellList.removeAll(removeCellList);
	}


	/**
	 * initialize all cells based on the stateArray parsed by XML file and store
	 * them in mycelllist
	 * 
	 * @param stateArray
	 * @param type
	 * @param paraList
	 */
	public void initialize(int[][] stateArray, String edgeType, String simulationType, Map<String,Double> paraMap) {
		int row = stateArray.length;
		int col = stateArray[0].length;
		mySimulationType = simulationType;
		myEdgeType = edgeType;
		myGridSize = new int[2];
		myGridSize[0] = row;
		myGridSize[1] = col;
		myParaMap = paraMap;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (stateArray[i][j] != EMPTY) {
					Cell current = createCell(i, j, stateArray[i][j]);
					myCellList.add(current);
				}
				else {
					int[] empty = {i,j};
					myEmptyPos.add(empty);
					
				}
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
		switch(mySimulationType) {
			case "GameOfLife":
				current = new GameofLife(row, col, state, myGridSize, myParaMap);
				break;
			case "PredatorPrey":
				current = new PredatorPrey(row, col, state, myGridSize, myParaMap);
				break;
			case "Segregation":
				current = new Segregation(row, col, state, myGridSize, myParaMap);
				break;
			case "Fire":
				current = new Fire(row, col, state, myGridSize, myParaMap);
				break;
			default:
				current = new GameofLife(row, col, state, myGridSize, myParaMap);
				break;
		}
		return current;
	}

}
