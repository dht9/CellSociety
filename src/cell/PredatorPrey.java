package cell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Game of Life implementation of Cell superclass
 * 
 * @author estellehe
 *
 */
public class PredatorPrey extends Cell {
	private static final int FISH = 0;
	private static final int SHARK = 1;
	private static final int FOURADJACENT = 4;

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
	public PredatorPrey(int row, int column, int state, int[] gridSize, Map<String, Double> paraMap) {
		super(row, column, state, gridSize, paraMap);

		for (String key : paraMap.keySet()) {
			if (key.equalsIgnoreCase("fishBreedTime"))
				fishBreed = paraMap.get(key);
			else if (key.equalsIgnoreCase("sharkBreedTime"))
				sharkBreed = paraMap.get(key);
			else if (key.equalsIgnoreCase("sharkDieTime"))
				sharkDie = paraMap.get(key);
		}
		myNeighborCell = new NeighborCell(FOURADJACENT, true, this);
		myAdjacent = myNeighborCell.adjacentPos();
	}

	@Override
	public void updateInfo(ArrayList<Cell> neighborlist, ArrayList<int[]> emptyPos) {
		super.updateInfo(neighborlist, emptyPos);
		myDieCount++;
		if (!myIsBreed) {
			myBreedCount++;
		}
		if (mystate == FISH) {
			fishUpdate(neighborlist, emptyPos);
		} else if (mystate == SHARK) {
			sharkUpdate(neighborlist, emptyPos);
		}
		int[] currentPos = { myrow, mycol };
		if (!myGiveBirth) {
			emptyPos.add(currentPos);
		}

		Iterator<int[]> emptyIter = emptyPos.iterator();
		while(emptyIter.hasNext()) {
			int[] nextEmpty = emptyIter.next();
			if (nextEmpty[0] == mynextRow && nextEmpty[1] == mynextCol) {
				emptyIter.remove();
				break;
			}
		}
	}

	@Override
	public void update(ArrayList<Cell> removeCellList, ArrayList<Cell> newCellList, ArrayList<int[]> emptyPos) {
		if (myIsDie) {
			int[] nextPos = { mynextRow, mynextCol };
			emptyPos.add(nextPos);
			removeCellList.add(this);
			if (myGiveBirth) {
				int[] currentPos = { myrow, mycol };
				emptyPos.add(currentPos);
			}
		}
		else if (myGiveBirth) {
			Cell baby = new PredatorPrey(myrow, mycol, mystate, mygrid, myParaMap);
			newCellList.add(baby);
			myBreedCount = 0;
			myGiveBirth = false;
			myIsBreed = false;
		}
		super.update(removeCellList, newCellList, emptyPos);
		myAdjacent = myNeighborCell.adjacentPos();
	}

	/**
	 * updateinfo implementation for fish
	 * 
	 * @param neighborlist
	 */
	private void fishUpdate(ArrayList<Cell> neighborlist, ArrayList<int[]> emptyPos) {
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
	private void checkMove(ArrayList<Cell> neighborlist, ArrayList<int[]> emptyPos) {
		ArrayList<int[]> movablePos = emptyNeighbor(neighborlist);
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
			this.mynextRow = nextPos[0];
			this.mynextCol = nextPos[1];
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
	private void sharkUpdate(ArrayList<Cell> neighborlist, ArrayList<int[]> emptyPos) {
		if (myDieCount >= sharkDie) {
			myIsDie = true;
		}
		checkBreed(sharkBreed);
		ArrayList<Cell> availableFish = foodList(neighborlist);
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
			this.mynextCol = food.column();
			this.mynextRow = food.row();
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
	private ArrayList<Cell> foodList(ArrayList<Cell> neighborlist) {
		Iterator<Cell> neighborIter = neighborlist.iterator();
		ArrayList<Cell> availableFish = new ArrayList<Cell>();
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
