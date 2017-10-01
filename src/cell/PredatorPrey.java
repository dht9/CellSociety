package cell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Game of Life implementation of Cell superclass
 * 
 * @author estellehe
 *
 */
public class PredatorPrey extends Cell {
	private ResourceBundle myResources = ResourceBundle.getBundle("resources/Text");
	
	private static final int FISH = 0;
	private static final int SHARK = 1;

	private double fishBreed;
	private double sharkBreed;
	private double sharkDie;
	private int myBreedCount = 0;
	private int myDieCount = 0;
	private boolean myIsBreed = false;
	private boolean myIsDie = false;
	private boolean myGiveBirth = false;

	/**
	 * constructor for predatorprey cell
	 * 
	 * @param row
	 * @param column
	 * @param state
	 * @param gridSize
	 * @param paraList:
	 *            {fishBreed, sharkBreed, sharkDie}
	 */
	public PredatorPrey(int row, int column, int state, int[] gridSize, Map<String, Double> paraMap, String edgeType, int neighborType) {
		super(row, column, state, gridSize, paraMap, edgeType, neighborType);

		for (String key : paraMap.keySet()) {
			if (key.equalsIgnoreCase("fishBreedTime"))
				fishBreed = paraMap.get(key);
			else if (key.equalsIgnoreCase("sharkBreedTime")) 
				sharkBreed = paraMap.get(key);
			else if (key.equalsIgnoreCase("sharkDieTime"))
				sharkDie = paraMap.get(key);
		}
	}

	@Override
	public void updateInfo(List<Cell> neighborlist, List<int[]> emptyPos) {
		super.updateInfo(neighborlist, emptyPos);
		myDieCount++;
		if (!myIsBreed) {
			myBreedCount++;
		}
		if (this.state() == FISH) {
			fishUpdate(neighborlist, emptyPos);
		} else if (this.state() == SHARK) {
			sharkUpdate(neighborlist, emptyPos);
		}
		int[] currentPos = { this.row(), this.column() };
		if (!myGiveBirth) {
			emptyPos.add(currentPos);
		}

		Iterator<int[]> emptyIter = emptyPos.iterator();
		while(emptyIter.hasNext()) {
			int[] nextEmpty = emptyIter.next();
			if (nextEmpty[0] == this.nextrow() && nextEmpty[1] == this.nextcol()) {
				emptyIter.remove();
				break;
			}
		}
	}

	@Override
	public void update(List<Cell> removeCellList, List<Cell> newCellList, List<int[]> emptyPos) {
		if (myIsDie) {
			int[] nextPos = { this.nextrow(), this.nextcol() };
			emptyPos.add(nextPos);
			removeCellList.add(this);
			if (myGiveBirth) {
				int[] currentPos = { this.row(), this.column() };
				emptyPos.add(currentPos);
			}
		}
		else if (myGiveBirth) {
			Cell baby = new PredatorPrey(this.row(), this.column(), this.state(), this.grid(), this.paraMap(), this.edgeType(), this.neighborType());
			newCellList.add(baby);
			myBreedCount = 0;
			myGiveBirth = false;
			myIsBreed = false;
		}
		super.update(removeCellList, newCellList, emptyPos);
	}

	/**
	 * updateinfo implementation for fish
	 * 
	 * @param neighborlist
	 */
	private void fishUpdate(List<Cell> neighborlist, List<int[]> emptyPos) {
		checkBreed(fishBreed);
		checkMove(neighborlist, emptyPos);
	}

	/**
	 * check if there is empty adjacent position to move into
	 * 
	 * also checks if the cell can reproduce
	 * 
	 * @param neighborlist
	 * @param breedTime
	 */
	private void checkMove(List<Cell> neighborlist, List<int[]> emptyPos) {
		List<int[]> movablePos = emptyNeighbor(neighborlist);
		Iterator<int[]> posIter = movablePos.iterator();
		outerloop: while (posIter.hasNext()) {
			int[] pos = posIter.next();
			for (int[] empty : emptyPos) {
				if (pos[0] == empty[0] && pos[1] == empty[1]) {
					continue outerloop;
				}
			}
			posIter.remove();
		}
		int posSize = movablePos.size();
		if (posSize != 0) {
			int randomIndex = (int) (Math.random() * posSize);
			int[] nextPos = movablePos.get(randomIndex);
			this.setNextRow(nextPos[0]);
			this.setNextCol(nextPos[1]);
			if (myIsBreed) {
				myGiveBirth = true;
			}
		}
	}

	/**
	 * updateinfo implementation for shark
	 * 
	 * the shark will use up a unit of energy at each update, and recover one unit
	 * for eating a fish
	 * 
	 * @param neighborlist
	 */
	private void sharkUpdate(List<Cell> neighborlist, List<int[]> emptyPos) {
		if (myDieCount >= sharkDie) {
			myIsDie = true;
		}
		checkBreed(sharkBreed);
		List<Cell> availableFish = foodList(neighborlist);
		Iterator<Cell> fishIter = availableFish.iterator();
		while (fishIter.hasNext()) {
			PredatorPrey currentFish = (PredatorPrey) fishIter.next();
			if (currentFish.myIsDie) {
				fishIter.remove();
			}
		}
		int fishSize = availableFish.size();
		if (fishSize != 0) {
			PredatorPrey food = (PredatorPrey) availableFish.get((int) (Math.random() * (fishSize - 1)));
			food.consume();
			this.setNextCol(food.column());
			this.setNextRow(food.row());
			myDieCount--;
			if (myIsBreed) {
				myGiveBirth = true;
			}
		} else {
			checkMove(neighborlist, emptyPos);
		}
	}

	/**
	 * get the available fish list around shark
	 * 
	 * @param neighborlist
	 * @return list of available fish
	 */
	private List<Cell> foodList(List<Cell> neighborlist) {
		Iterator<Cell> neighborIter = neighborlist.iterator();
		List<Cell> availableFish = new ArrayList<Cell>();
		if (neighborIter.hasNext()) {
			Cell neighbor = neighborIter.next();
			if (neighbor.state() == FISH) {
				availableFish.add(neighbor);
			}
		}
		return availableFish;
	}

	/**
	 * check if the cell can reproduce
	 * 
	 * @param breedTime
	 */
	private void checkBreed(double breedTime) {
		if (myBreedCount >= breedTime) {
			myIsBreed = true;
		}
	}

	/**
	 * change the cell state when being consumed
	 */
	public void consume() {
		myIsDie = true;
	}
}
