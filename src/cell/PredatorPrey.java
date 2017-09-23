package cell;

import java.util.ArrayList;

/**
 * Game of Life implementation of Cell superclass
 * 
 * @author estellehe
 *
 */
public class PredatorPrey extends Cell{
	private double fishBreed;
	private double sharkBreed;
	private double fishDie;
	private double sharkDie;
	private int breedCount = 0;
	private int dieCount = 0;

	public PredatorPrey(int row, int column, int state, int[] gridSize, double[] paraList) {
		super(row, column, state, gridSize, paraList);
		fishBreed = paraList[0];
		sharkBreed = paraList[1];
		fishDie = paraList[2];
		sharkDie = paraList[3];
	}

	@Override
	public void updateInfo(ArrayList<Cell> neighborlist) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public boolean isNeighbor(Cell other) {
		if (other.equals(this)) {			//check not self
			return false;
		}
		if (Math.abs(other.row() - this.row()) <= 1 && other.column() == this.column()) {			//check up down
			return true;
		}
		else if (Math.abs(other.column() - this.column()) <= 1 && other.row() == this.row()) {	//check left right
			return true;
		}
		else if (this.isEdge()) {			//check torus
			if (other.row()+this.row() == this.mygrid[0]-1 && other.column() == this.column() && 
					Math.abs(other.row()-this.row()) == this.mygrid[0]) {
				return true;
			}
			else if (other.column()+this.column() == this.mygrid[1]-1 && other.row() == this.row() && 
					Math.abs(other.column()-this.column()) == this.mygrid[1]) {
				return true;
			}
		}
		return false;			//to make code readable, I did not collapse all if statement into one return value 
	}

}
