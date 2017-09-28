package cell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Segregation extends Cell{
	private static final int EIGHTADJACENT = 8;
	
	private double myThreshold;

	public Segregation(int row, int column, int state, int[] gridSize, Map<String,Double> paraMap) {
		super(row, column, state, gridSize, paraMap);
		
		for (String key: paraMap.keySet()) {
		    if (key.equalsIgnoreCase("satisfiedThreshold"))
		    	myThreshold = paraMap.get(key);
		    	
		}
		myNeighborCell = new NeighborCell(EIGHTADJACENT, false, this);
		myAdjacent = myNeighborCell.adjacentPos();
	}
	
	@Override
	public void updateInfo(ArrayList<Cell> neighborlist, ArrayList<int[]> emptyPos) {
		super.updateInfo(neighborlist, emptyPos);
		double sameCount = 0;
		for (Cell neighbor: neighborlist) {
			if (mystate == neighbor.mystate) {
				sameCount += 1;
			}
		}
		double percent = sameCount/neighborlist.size();
		if (neighborlist.size() == 0) {
			percent = 0;
		}
		if (percent < myThreshold) {
			int[] nextPos = randomMove(emptyPos);
			mynextRow = nextPos[0];
			mynextCol = nextPos[1];
			int[] currentPos = {myrow, mycol};
			emptyPos.add(currentPos);
			Iterator<int[]> emptyIter = emptyPos.iterator();
			while(emptyIter.hasNext()) {
				int[] nextEmpty = emptyIter.next();
				if (nextEmpty[0] == mynextRow && nextEmpty[1] == mynextCol) {
					emptyIter.remove();
				}
			}
		}
	}
	
	@Override
	public void update(ArrayList<Cell>removeCellList, ArrayList<Cell> newCellList, ArrayList<int[]> emptyPos) {
		super.update(removeCellList, newCellList, emptyPos);
	}
	
	private int[] randomMove(ArrayList<int[]> emptyPos) {
		int randomIndex = (int) (Math.random()*(emptyPos.size()));
		int[] randomPos = emptyPos.get(randomIndex);
		return randomPos;
	}

}
