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
	private static final int UPGRADE = -1;
	private static final int DOWNGRADE = 1;
	
	private int myLevel = 0;
	private boolean myIsDie = false;

	/**
	 * constructor for RockPaperScissors Cell
	 * @param row
	 * @param column
	 * @param state
	 * @param gridSize
	 * @param paraMap
	 * @param edgeType
	 * @param neighborType
	 */
	public RockPaperScissors(int row, int column, int state, int[] gridSize, Map<String, Double> paraMap,
			String edgeType, int neighborType) {
		super(row, column, state, gridSize, paraMap, edgeType, neighborType);
	}
	
	@Override
	public void updateInfo(List<Cell> neighborlist, List<int[]> emptyPos) {
		super.updateInfo(neighborlist, emptyPos);
		getUpdate(neighborlist);
		checkDie();
		checkSuper();
	}

	@Override
	public void update(List<Cell> removeCellList, List<Cell> newCellList, List<int[]> emptyPos) {
		if (myIsDie) {
			this.setNextState(EMPTY);
			myLevel = 0;
			myIsDie = false;
		}
		super.update(removeCellList, newCellList, emptyPos);
	}

	/**
	 * get update info for each cell
	 * @param neighborlist
	 */
	private void getUpdate(List<Cell> neighborlist) {
		RockPaperScissors neighbor = (RockPaperScissors) neighborlist.get(this.randomIndex(neighborlist.size()));
		if (this.state() == EMPTY) {
			if (neighbor.state() != EMPTY && neighbor.level() < 9) {
				this.setNextState(neighbor.state());
				myLevel = neighbor.level() + 1;
			}
		}
		else if (neighbor.state() != EMPTY){
			colorFeed(neighbor);
		}
	}
	
	/**
	 * get update info when color cells eat other color cell
	 * @param other
	 */
	private void colorFeed(RockPaperScissors other) {
		if (Math.abs(this.state() - other.state()) == 1) {
			if (this.state() > other.state()) {
				this.setLevel(UPGRADE);
				other.setLevel(DOWNGRADE);
			}
			else {
				this.setLevel(DOWNGRADE);
				other.setLevel(UPGRADE);
			}
		}
		else if (Math.abs(this.state() - other.state()) >= 2) {
			if (this.state() > other.state()) {
				this.setLevel(DOWNGRADE);
				other.setLevel(UPGRADE);
			}
			else {
				this.setLevel(UPGRADE);
				other.setLevel(DOWNGRADE);
			}
		}
	}
	
	/**
	 * set level
	 * @param value
	 */
	public void setLevel(int value) {
		myLevel = myLevel + value;
	}
	
	/**
	 * access level
	 * @return level
	 */
	public int level() {
		return myLevel;
	}
	
	/**
	 * check if a cell dies
	 */
	private void checkDie() {
		if (myLevel > 9) {
			myIsDie = true;
		}
	}
	
	/**
	 * reset level to 0 when upgraded too much
	 */
	private void checkSuper() {
		if (myLevel < 0) {
			myLevel = 0;
		}
	}
}
