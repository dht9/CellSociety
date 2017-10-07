package cell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CellManager {

	private ResourceBundle myResources = ResourceBundle.getBundle("resources/Text");

	private static final int EMPTY = -1;
	private static final int AGENT = 0;
	private static final int AGENTMAP = -1;
	private static final int BACKGROUND = 1;

	private List<Cell> myCellList;
	private List<Cell> myAgentList;
	private List<Cell> myBackgroundList;

	private String mySimulationType;
	private String myEdgeType;
	private int myNeighborType;
	private int[] myGridSize = new int[2];
	private Map<String, Double> myParaMap;
	private int myAgentNum = 10;

	private List<int[]> myEmptyPos;

	/**
	 * constructor for cell manager, initialize mycelllist
	 */
	public CellManager() {
		myCellList = new ArrayList<Cell>();
		myAgentList = new ArrayList<Cell>();
		myBackgroundList = new ArrayList<Cell>();
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
		for (Cell other : myCellList) {
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
		if (mySimulationType == "SugarScape") {
			myCellList = new ArrayList<Cell>();
			myCellList.addAll(myAgentList);
			myCellList.addAll(myBackgroundList);
		}
		for (Cell current : myCellList) {
			current.updateInfo(getNeighborList(current), myEmptyPos);
		}
		for (Cell current : myCellList) {
			current.update(removeCellList, newCellList, myEmptyPos);
		}
		myCellList.addAll(newCellList);
		myCellList.removeAll(removeCellList);
		updateAgentBackground();
	}

	private void updateAgentBackground() {
		if (mySimulationType == "SugarScape") {
			myAgentList = new ArrayList<Cell>();
			myBackgroundList = new ArrayList<Cell>();
			for (Cell current : myCellList) {
				if (current.state() == AGENT) {
					myAgentList.add(current);
				} else {
					myBackgroundList.add(current);
				}
			}
		}
	}

	/**
	 * initialize all cells based on the stateArray parsed by XML file and store
	 * them in mycelllist
	 * 
	 * @param stateArray
	 * @param type
	 * @param paraList
	 */
	public void initialize(int[][] stateArray, String edgeType, String simulationType, Map<String, Double> paraMap,
			int neighborType) {
		int row = stateArray.length;
		int col = stateArray[0].length;
		mySimulationType = simulationType;
		myEdgeType = edgeType;
		myGridSize = new int[2];
		myGridSize[0] = row;
		myGridSize[1] = col;
		myParaMap = paraMap;
		// myAgentNum = agentNum;
		myNeighborType = neighborType;
		addToList(stateArray, row, col);
	}

	private void addToList(int[][] stateArray, int row, int col) {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (stateArray[i][j] != EMPTY) {
					Cell current = createCell(i, j, stateArray[i][j]);
					if (mySimulationType == "SugarScape") {
						myBackgroundList.add(current);
						int[] empty = { i, j };
						myEmptyPos.add(empty);
					} else {
						myCellList.add(current);
					}
				} else {
					int[] empty = { i, j };
					myEmptyPos.add(empty);
				}
			}
		}
		if (mySimulationType == "SugarScape") {
			addRandomAgent();
		}
	}

	private void addRandomAgent() {
		for (int i = 0; i < myAgentNum; i++) {
			int randomIndex = (int) (Math.random() * (myBackgroundList.size() - i));
			Cell match = myBackgroundList.get(randomIndex);
			Cell current = createCell(match.row(), match.column(), AGENTMAP);
			myAgentList.add(current);
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
	private Cell createCell(int row, int col, int state) {
		Cell current;
		switch (mySimulationType) {
		case "GameOfLife":
			current = new GameofLife(row, col, state, myGridSize, myParaMap, myEdgeType, myNeighborType);
			break;
		case "PredatorPrey":
			current = new PredatorPrey(row, col, state, myGridSize, myParaMap, myEdgeType, myNeighborType);
			break;
		case "Segregation":
			current = new Segregation(row, col, state, myGridSize, myParaMap, myEdgeType, myNeighborType);
			break;
		case "Fire":
			current = new Fire(row, col, state, myGridSize, myParaMap, myEdgeType, myNeighborType);
			break;
		case "RPS":
			current = new RockPaperScissors(row, col, state, myGridSize, myParaMap, myEdgeType, myNeighborType);
			break;
		case "SugarScape":
			current = new SugarScape(row, col, state, myGridSize, myParaMap, myEdgeType, myNeighborType);
			break;
		default:
			current = new GameofLife(row, col, state, myGridSize, myParaMap, myEdgeType, myNeighborType);
			// showError();
			break;
		}
		return current;
	}

	/*
	 * Display error message. Does not work yet.
	 */
	private void showError() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText(myResources.getString("ErrorSimulationType"));
		alert.showAndWait();
	}

	/**
	 * for mouse click to change state
	 * 
	 * @param row
	 * @param col
	 * @param state
	 */
	public void changeState(int row, int col, int state) {
		Iterator<Cell> cellIter = myCellList.iterator();
		while (cellIter.hasNext()) {
			Cell next = cellIter.next();
			if (next.row() == row && next.column() == col) {
				next.setNextState(state);
				;
			}
		}
		List<Cell> newCellList = new ArrayList<Cell>();
		List<Cell> removeCellList = new ArrayList<Cell>();
		for (Cell current : myCellList) {
			current.update(removeCellList, newCellList, myEmptyPos);
		}
		myCellList.addAll(newCellList);
		myCellList.removeAll(removeCellList);
	}

}
