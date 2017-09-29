package cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CellManager {
	private static final int EMPTY = -1;
	
	private List<Cell> myCellList;
	private String mySimulationType;
	private String myEdgeType;
	private int[] myGridSize = new int[2];
	private Map<String,Double> myParaMap;
	private List<int[]> myEmptyPos;

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
	public List<Cell> cellList() {
		return myCellList;
	}

	/**
	 * get the neighbor cell list for input cell
	 * 
	 * @param current
	 * @return arraylist of neighbor cell
	 */
	private List<Cell> getNeighborList(Cell current) {
		List<Cell> neighborList = new ArrayList<Cell>();
		for (Cell other: myCellList) {
			if (current.isNeighbor(other)) {
				neighborList.add(other);
			}
		}
		return neighborList;
	}

	/**
	 * update every cell created and stored in myCellList
	 */
	public void update() {
		List<Cell> newCellList = new ArrayList<Cell>();
		List<Cell> removeCellList = new ArrayList<Cell>();
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
				current = new GameofLife(row, col, state, myGridSize, myParaMap, myEdgeType);
				break;
			case "PredatorPrey":
				current = new PredatorPrey(row, col, state, myGridSize, myParaMap, myEdgeType);
				break;
			case "Segregation":
				current = new Segregation(row, col, state, myGridSize, myParaMap, myEdgeType);
				break;
			case "Fire":
				current = new Fire(row, col, state, myGridSize, myParaMap, myEdgeType);
				break;
			default:
				current = new GameofLife(row, col, state, myGridSize, myParaMap, myEdgeType);
				break;
		}
		return current;
	}

}
