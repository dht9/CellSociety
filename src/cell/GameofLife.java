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
	private static final int EIGHTADJACENT = 8;

	public GameofLife(int row, int column, int state, int[] gridSize, double[] paraList) {
		super(row, column, state, gridSize, paraList);
		myNeighborCell = new NeighborCell(EIGHTADJACENT, false, this);
	}

	@Override
	public void updateInfo(ArrayList<Cell> neighborlist) {
		mynextState = mystate;
		mynextRow = myrow;
		mynextCol = mycol;
		int liveCount = 0;
		for (Cell neighbor: neighborlist) {
			if (neighbor.state() == LIVE) {
				liveCount++;
			}
		}
		this.mynextState = this.mystate;
		if (this.mystate == LIVE) {  //TODO check if enum can be used here
			switch(liveCount) {
				case 0:
				case 1:
					this.mynextState = DIE;
					break;
				case 2:
				case 3:
					this.mynextState = LIVE;
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
		return myNeighborCell.isNeighbor(other);
	}

}
