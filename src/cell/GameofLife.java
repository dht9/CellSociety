package cell;

import java.util.ArrayList;

/**
 * Game of Life implementation of Cell superclass
 * 
 * @author estellehe
 *
 */
public class GameofLife extends Cell{
	private static final int LIVE = 1;
	private static final int DIE = 0;

	public GameofLife(int row, int column, int state, int[] gridSize, double[] paraList) {
		super(row, column, state, gridSize, paraList);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateInfo(ArrayList<Cell> neighborlist) {
		int liveCount = 0;
		for (Cell neighbor: neighborlist) {
			if (neighbor.state() == LIVE) {
				liveCount++;
			}
		}
		if (this.mystate == LIVE) {  //TODO check if enum can be used here
			switch(liveCount) {
				case 0:
				case 1:
					this.mynextState = DIE;
					break;
				case 2:
				case 3:
					break;
				default:
					this.mynextState = DIE;
					break;
			}
		}
		else {
			if (liveCount == 3) {
				this.mynextState = LIVE;
			}
		}
	}

	@Override
	public boolean isNeighbor(Cell other) {
		if (Math.abs(other.row() - this.row()) <= 1 && Math.abs(other.column() - this.column()) <= 1) {
			if (!other.equals(this)) {
				return true;
			}
		}
		return false;
	}

}
