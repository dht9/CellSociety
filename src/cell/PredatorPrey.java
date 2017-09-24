package cell;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Game of Life implementation of Cell superclass
 * 
 * @author estellehe
 *
 */
public class PredatorPrey extends Cell{
	private static final int FISH = 1;
	private static final int SHARK = 2;
	private static final int FOURADJACENT = 4;
	
	private double fishBreed;
	private double sharkBreed;
	private double sharkDie;
	private int myBreedCount = 0;
	private int myDieCount = 0;
	private boolean myIsBreed = false;
	private boolean myIsDie = false;
	private double[] myparaList;

	/**
	 * constructor for predatorprey cell
	 * @param row
	 * @param column
	 * @param state
	 * @param gridSize
	 * @param paraList: {fishBreed, sharkBreed, sharkDie}
	 */
	public PredatorPrey(int row, int column, int state, int[] gridSize, double[] paraList) {
		super(row, column, state, gridSize, paraList);
		myparaList = paraList;
		fishBreed = paraList[0];
		sharkBreed = paraList[1];
		sharkDie = paraList[2];
		myNeighborCell = new NeighborCell(FOURADJACENT, true, this);
	}

	@Override
	public void updateInfo(ArrayList<Cell> neighborlist) {
		myDieCount++;
		myBreedCount++;
		if (mystate == FISH) {
			fishUpdate(neighborlist);
		}
		else if(mystate == SHARK) {
			sharkUpdate(neighborlist);
		}
	}
	
	@Override
	public void update(ArrayList<Cell> cellList) {
		if (myIsDie) {
			cellList.remove(this);
		}
		if (myIsBreed) {
			Cell baby = new PredatorPrey(myrow, mycol, mystate, mygrid, myparaList);
			cellList.add(baby);
			myBreedCount = 0;
		}
		super.update(cellList);
	}
	
	/**
	 * updateinfo implementation for fish 
	 * 
	 * @param neighborlist
	 */
	private void fishUpdate(ArrayList<Cell> neighborlist) {
		checkMove(neighborlist, fishBreed);
	}

	/**
	 * check if there is empty adjacent position to move into 
	 * 
	 * also checks if the cell can reproduce
	 * 
	 * @param neighborlist
	 * @param breedTime
	 */
	private void checkMove(ArrayList<Cell> neighborlist, double breedTime) {
		ArrayList<int[]> movablePos = emptyNeighbor(neighborlist);
		int posSize = movablePos.size();
		if (posSize != 0) {
			int[] nextPos = movablePos.get((int) (Math.random()*(posSize-1)));
			this.mynextRow = nextPos[0];
			this.mynextCol = nextPos[1];
			checkBreed(breedTime);
		}
	}
	
	/**
	 * updateinfo implementation for shark
	 * 
	 * the shark will use up a unit of energy at each update, and recover one unit for eating a fish
	 * 
	 * @param neighborlist
	 */
	private void sharkUpdate(ArrayList<Cell> neighborlist) {
		myDieCount++;
		ArrayList<Cell> availableFish = foodList(neighborlist);
		int fishSize = availableFish.size();
		if (fishSize != 0) {
			PredatorPrey food = (PredatorPrey) availableFish.get((int) (Math.random()*(fishSize-1)));
			food.consume();
			this.mynextCol = food.column();
			this.mynextRow = food.row();
			myDieCount--;
			checkBreed(sharkBreed);
		}
		else {
			checkMove(neighborlist, sharkBreed);
		}
	}

	/**
	 * get the available fish list around shark
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
		if (myBreedCount == breedTime) {
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
