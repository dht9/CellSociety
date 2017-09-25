package cell;

import java.util.ArrayList;
import java.util.Map;

public class Fire extends Cell{
	private static final int BURNING = 1;
	private static final int TREE = 0;
	private static final int EMPTY = -1;
	private static final int FOURADJACENT = 4;
	
	private double myProbCatch;
	private boolean myIsEmpty = false;
	private boolean myIsProbBurn = false;

	public Fire(int row, int column, int state, int[] gridSize, Map<String,Double> paraMap) {
		super(row, column, state, gridSize, paraMap);
		for (String key : paraMap.keySet()) {
			if (key.equalsIgnoreCase("probCatch"))
				myProbCatch = paraMap.get(key);
		}
		myNeighborCell = new NeighborCell(FOURADJACENT, false, this);
		myAdjacent = myNeighborCell.adjacentPos();
	}
	
	@Override
	public void updateInfo(ArrayList<Cell> neighborlist, ArrayList<int[]> emptyPos) {
		super.updateInfo(neighborlist, emptyPos);
		if (mystate == BURNING) {
			myIsEmpty = true;
			mynextState = EMPTY;
		}
		if (mystate == TREE) {
			for (Cell neighbor: neighborlist) {
				if (neighbor.mystate == BURNING) {
					myIsProbBurn = true;
				}
			}
			if (myIsProbBurn) {
				probBurn();
			}
		}
	}
	
	@Override
	public void update(ArrayList<Cell>removeCellList, ArrayList<Cell> newCellList, ArrayList<int[]> emptyPos) {
		super.update(removeCellList, newCellList, emptyPos);
		if (myIsEmpty) {
			removeCellList.add(this);
		}
	}
	
	private void probBurn() {
		double prob = Math.random();
		if (prob <= myProbCatch) {
			mynextState = BURNING;
		}
		myIsProbBurn = false;
	}

}
