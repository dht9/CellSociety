package cell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RockPaperScissors extends Cell{
	private static final int EMPTY = 0;
	private static final int ROCK = 1;
	private static final int PAPER = 2;
	private static final int SCISSORS = 3;
	
	private int myLevel = 0;
	private boolean myIsDie = false;

	public RockPaperScissors(int row, int column, int state, int[] gridSize, Map<String, Double> paraMap,
			String edgeType, int neighborType) {
		super(row, column, state, gridSize, paraMap, edgeType, neighborType);
	}
	
	@Override
	public void updateInfo(List<Cell> neighborlist, List<int[]> emptyPos) {
		super.updateInfo(neighborlist, emptyPos);
		getUpdate(neighborlist);
		checkDie();
	}

	
	private void getUpdate(List<Cell> neighborlist) {
		
	}
	
	
	private void checkDie() {
		if (myLevel >= 9) {
			myIsDie = true;
		}
	}
}
