package cell;

import java.util.List;
import java.util.Map;

public class Fire extends Cell{
	private static final int BURNING = 1;
	private static final int TREE = 0;
	private static final int EMPTY = -1;
	
	private double myProbCatch;
	private boolean myIsEmpty = false;
	private boolean myIsProbBurn = false;

	public Fire(int row, int column, int state, int[] gridSize, Map<String,Double> paraMap, String edgeType, int neighborType) {
		super(row, column, state, gridSize, paraMap, edgeType, neighborType);
		for (String key : paraMap.keySet()) {
			if (key.equalsIgnoreCase("probCatch"))
				myProbCatch = paraMap.get(key);
		}
	}
	
	@Override
	public void updateInfo(List<Cell> neighborlist, List<int[]> emptyPos) {
		super.updateInfo(neighborlist, emptyPos);
		if (this.state() == BURNING) {
			myIsEmpty = true;
			this.setNextState(EMPTY);
		}
		if (this.state() == TREE) {
			for (Cell neighbor: neighborlist) {
				if (neighbor.state() == BURNING) {
					myIsProbBurn = true;
				}
			}
			if (myIsProbBurn) {
				probBurn();
			}
		}
	}
	
	@Override
	public void update(List<Cell>removeCellList, List<Cell> newCellList, List<int[]> emptyPos) {
		super.update(removeCellList, newCellList, emptyPos);
		if (myIsEmpty) {
			removeCellList.add(this);
		}
	}
	
	private void probBurn() {
		double prob = Math.random();
		if (prob <= myProbCatch) {
			this.setNextState(BURNING);
		}
		myIsProbBurn = false;
	}

}
