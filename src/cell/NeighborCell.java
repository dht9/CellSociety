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
		List<int[]> adjacentList = myCell.adjacent();
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
//		Iterator<Cell> iter = neighborList.iterator();
//		while (iter.hasNext()) {
//			Cell current = iter.next();
//			int[] currentPos = { current.myrow, current.mycol };
//			emptyList.remove(currentPos);
//		}
		for (Cell current: neighborList) {
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

//	/**
//	 * get list of adjacent position
//	 * 
//	 * Currently only valid for eight/!torus/!move, four/torus
//	 * 
//	 * @return a list of adjacent positions in {row, col}
//	 */
//	public List<int[]> adjacentPos() {
//		List<int[]> adjacentList = getAdjacentPos(myRadius);
////		if (myNeighborType == FOURADJACENT) {
////			 adjacentList = getFourAdjacentPositions();
////		} else { // if (myNeighborType == EIGHTADJACENT)
////			 adjacentList = getEightAdjacentPositions();
////		}
//		return adjacentList;
//	}
	
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

//	/**
//	 * implementation of adjacentPos for four adjacent
//	 * 
//	 * @return list of adjacent position
//	 */
//	private List<int[]> getFourAdjacentPositions() {
//		
//		List<int[]> adjacentList = new ArrayList<int[]>();
//		int[] right = { myCell.row(), myCell.column() + 1 };
//		int[] left = { myCell.row(), myCell.column() - 1 };
//		int[] up = { myCell.row() - 1, myCell.column() };
//		int[] down = { myCell.row()+ 1, myCell.column() };
//		if (myIsTorus && myCell.isEdge()) {
//			if (myCell.row() == 0) {
//				up[0] = myCell.grid()[0]-1;
//			}
//			if (myCell.row() == myCell.grid()[0]-1) {
//				down[0] = 0;
//			}
//			if (myCell.column() == 0) {
//				left[1] = myCell.grid()[1]-1;
//			}
//			if (myCell.column() == myCell.grid()[1]-1) {
//				right[1] = 0;
//			}
//		}
//		adjacentList.addAll(new ArrayList<int[]>(Arrays.asList(right, left, up, down)));
//		return adjacentList;
//	}

//	/**
//	 * implementation of adjacentPos for eight adjacent
//	 * 
//	 * do not include torus option right now
//	 * 
//	 * @return list of adjacent position
//	 */
//	public List<int[]> getEightAdjacentPositions() {
//		List<int[]> adjacentPositionsList = new ArrayList<int[]>();
//		
//
//		for (int i = -1; i <= 1; i++) {
//			for (int j = -1; j <= 1; j++) {
//				if (i != 0 || j != 0) {
//					int[] pos = new int[2];
//					pos[0] = myCell.row() + i;
//					pos[1] = myCell.column() + j;
//					adjacentPositionsList.add(pos);
//				}
//			}
//		}
//		return adjacentPositionsList;
//	}

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
