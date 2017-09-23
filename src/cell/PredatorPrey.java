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
	private double fishDie;
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
	 * @param paraList
	 */
	public PredatorPrey(int row, int column, int state, int[] gridSize, double[] paraList) {
		super(row, column, state, gridSize, paraList);
		myparaList = paraList;
		fishBreed = paraList[0];
		sharkBreed = paraList[1];
		fishDie = paraList[2];
		sharkDie = paraList[3];
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
		}
		super.update(cellList);
	}
	
	/**
	 * updateinfo implementation for fish 
	 * 
	 * @param neighborlist
	 */
	private void fishUpdate(ArrayList<Cell> neighborlist) {
		if (myDieCount == fishDie) {
			myIsDie = true;
		}
		ArrayList<int[]> movablePos = emptyNeighbor(neighborlist);
		int posSize = movablePos.size();
		if (posSize != 0) {
			int[] nextPos = movablePos.get((int) (Math.random()*(posSize-1)));
			mynextRow = nextPos[0];
			mynextCol = nextPos[1];
			if (myBreedCount == fishBreed) {
				myIsBreed = true;
				myBreedCount = 0;
			}
		}
	}
	
	private void sharkUpdate(ArrayList<Cell> neighborlist) {
		Iterator<Cell> neighborIter = neighborlist.iterator();
		ArrayList<Cell> availableFish = new ArrayList<Cell>();
		ArrayList<int[]> movablePos = emptyNeighbor(neighborlist);
		if (neighborIter.hasNext()) {
			Cell neighbor = neighborIter.next();
			if (neighbor.state() == FISH) {
				availableFish.add(neighbor);
			}
		}
		int fishSize = availableFish.size();
		int posSize = movablePos.size();
		if (fishSize != 0) {
			PredatorPrey food = (PredatorPrey) availableFish.get((int) (Math.random()*(fishSize-1)));
			food.consume();
			this.mynextCol = food.column();
			this.mynextRow = food.row();
		}
		else if(posSize != 0) {
			int[] nextPos = movablePos.get((int) (Math.random()*(posSize-1)));
			mynextRow = nextPos[0];
			mynextCol = nextPos[1];
		}
	}
	
	public void consume() {
		myIsDie = true;
	}
}
