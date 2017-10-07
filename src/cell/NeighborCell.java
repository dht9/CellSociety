package cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * handle all the neighbor cells related issue
 * 
 * @author estellehe
 *
 */
public class NeighborCell {
	private static final int FOURADJACENT = 4;
	private static final int EIGHTADJACENT = 8;
	private static final int THREEADJACENT = 3;
	private static final int FOURVISION = 44;
	private static final double FOURRADIUS = 1;
	private static final double EIGHTRADIUS = Math.sqrt(2);
	private static final double THREERADIUS = 1;

	private int myNeighborType;
	private boolean myIsTorus;
	private Cell myCell;
	private double myRadius;

	/**
	 * constructor for neighborcell class
	 * 
	 * @param neighborType
	 * @param isTorus
	 * @param current
	 */
	public NeighborCell(int neighborType, boolean isTorus, Cell current) {
		myNeighborType = neighborType;
		myIsTorus = isTorus;
		myCell = current;
		setRadius();
	}

	/**
	 * assign neighboring radius based on neighbor type
	 */
	private void setRadius() {
		switch(myNeighborType) {
			case FOURADJACENT:
				myRadius = FOURRADIUS;
				break;
			case EIGHTADJACENT:
				myRadius = EIGHTRADIUS;
				break;
			case THREEADJACENT:
				myRadius = THREERADIUS;
				break;
			default:
				myRadius = FOURRADIUS;
				break;
		}
	}
	
	/**
	 * check if a cell is a neighbor cell
	 * 
	 * @param other
	 * @return whether a cell is the neighbor cell
	 */
	public boolean isNeighbor(Cell other) {
		List<int[]> adjacentList = new ArrayList<int[]>();
		if (myNeighborType == FOURVISION) {
			SugarScape current = (SugarScape) myCell;
			adjacentList = current.visionList();
		}
		else {
			adjacentList = myCell.adjacent();
		}
		for (int[] adjacent: adjacentList) {
			if (other.row() == adjacent[0] && other.column() == adjacent[1]) {
				return true;
			}
		}
		return false;
	}

	/**
	 * return list of adjacent empty position
	 * 
	 * @param neighborList
	 * @return list of empty position
	 */
	public List<int[]> emptyNeighbor(List<Cell> neighborList) {
		List<int[]> emptyList = myCell.adjacent();
		Iterator<Cell> neighborIter = neighborList.iterator();
		while (neighborIter.hasNext()) {
			Cell current = neighborIter.next();
			Iterator<int[]> emptyIter = emptyList.iterator();
			while (emptyIter.hasNext()) {
				int[] empty = emptyIter.next();
				if (empty[0] == current.row()&& empty[1] == current.column()) {
					emptyIter.remove();
				}
			}
		}
		return emptyList;
	}

	/**
	 * only valid in neighbor type 4 for SugarScape
	 * @return
	 */
	public List<int[]> visionPos(int vision) {
		List<int[]> visionList = new ArrayList<int[]>();
		for (int i = vision; Math.abs(i) <= vision; i--) {
			for (int j = vision; Math.abs(j) <= vision; j--) {
				int[] pos = getPos(i, j);
				visionList.add(pos);
			}
		}
		return visionList;
	}
	
	/**
	 * get a list of adjacent position
	 * @return list of adjacent position
	 */
	public List<int[]> adjacentPos() {
		List<int[]> adjacentList = new ArrayList<int[]>();
		double sqR = myRadius*myRadius;
		for (int i = (int) Math.floor(myRadius); i*i <= sqR; i--) {
			for (int j = (int) Math.floor(Math.sqrt(sqR-(i*i))); j*j <= sqR-(i*i); j--) {
				if (i != 0 || j != 0) {
					int[] pos = getPos(i, j);
					adjacentList.add(pos);
				}
			}
		}
		return adjacentList;
	}

	/**
	 * helper method for getting position and checking torus
	 * @param i
	 * @param j
	 * @return adjacent position
	 */
	private int[] getPos(int i, int j) {
		int[] pos = new int[2];
		pos[0] = myCell.row() + i;
		if (myIsTorus && (pos[0] < 0 || pos[0] > myCell.grid()[0]-1)) {
			pos[0] = myCell.grid()[0] - Math.abs(pos[0]);
		}
		pos[1] = myCell.column() + j;
		if (myIsTorus && (pos[1] < 0 || pos[1] > myCell.grid()[1]-1)) {
			pos[1] = myCell.grid()[1] - Math.abs(pos[1]);
		}
		return pos;
	}


	/**
	 * helper method for getting random index
	 * @param size of list
	 * @return random index
	 */
	public int randomIndex(int size) {
		return (int) (Math.random() * size);
	}
	
	/**
	 * access the neighborType
	 * @return myNeighborType
	 */
	public int getNeighborType() {
		return myNeighborType;
	}

}
