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

	public GameofLife(int row, int column, int state, int[] gridSize) {
		super(row, column, state, gridSize);
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
		if (this.mystate == LIVE) {
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

}
