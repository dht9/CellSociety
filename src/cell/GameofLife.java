package cell;

import java.util.List;
import java.util.Map;

/**
 * Game of Life implementation of Cell superclass
 * 
 * @author estellehe
 *
 */
public class GameofLife extends Cell{
	private static final int LIVE = 1;
	private static final int DIE = 0;
	

	/**
	 * constructor for gameoflife cell
	 * 
	 * @param row
	 * @param column
	 * @param state
	 * @param gridSize
	 * @param paraList
	 */
	public GameofLife(int row, int column, int state, int[] gridSize, Map<String,Double> paraMap, String edgeType, int neighborType) {
		super(row, column, state, gridSize, paraMap, edgeType, neighborType);
	}

	@Override
	public void updateInfo(List<Cell> neighborlist, List<int[]> emptyPos) {
		super.updateInfo(neighborlist, emptyPos);
		int liveCount = 0;
		for (Cell neighbor: neighborlist) {
			if (neighbor.state() == LIVE) {
				liveCount++;	
			}
		}
		if (this.state() == LIVE) {  //TODO check if enum can be used here
			switch(liveCount) {
				case 0:
				case 1:
					this.setNextState(DIE);
					break;
				case 2:
				case 3:
					this.setNextState(LIVE);
					break;
				default:
					this.setNextState(DIE);
					break;
			}
		}
		else {
			if (liveCount == 3) {
				this.setNextState(LIVE);
			}
		}
	}



}
