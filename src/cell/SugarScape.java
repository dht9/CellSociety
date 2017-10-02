package cell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SugarScape extends Cell{
	private static final int AGENT = 0;
	private static final int PATCH = 1;
	private static final int LARGE = 1000000;

	private double maxSugar;
	private double mySugar;
	private int mySugarGrow = 0;
	private double initSugar;
	private double initSugarMin;
	private double initSugarMax;
	private double sugarMeta;
	private double sugarMetaMin;
	private double sugarMetaMax;
	private double myVision;
	private double myVisionMin;
	private double myVisionMax;
	private double sugarRate;
	private double sugarInterval;
	private List<int[]> myVisionPos;
	private boolean myIsDie = false;

	public SugarScape(int row, int column, int state, int[] gridSize, Map<String, Double> paraMap, String edgeType,
			int neighborType) {
		super(row, column, state, gridSize, paraMap, edgeType, neighborType);
		
		for (String key : paraMap.keySet()) {
			if (key.equalsIgnoreCase("initSugarMin"))
				initSugarMin = paraMap.get(key);
			else if (key.equalsIgnoreCase("initSugarMax"))
				initSugarMax = paraMap.get(key);
			else if (key.equalsIgnoreCase("sugarMetaMin")) 
				sugarMetaMin = paraMap.get(key);
			else if (key.equalsIgnoreCase("sugarMetaMax")) 
				sugarMetaMax = paraMap.get(key);
			else if (key.equalsIgnoreCase("visionMin"))
				myVisionMin = paraMap.get(key);
			else if (key.equalsIgnoreCase("visionMax"))
				myVisionMax = paraMap.get(key);
			else if (key.equalsIgnoreCase("sugarRate"))
				sugarRate = paraMap.get(key);
			else if (key.equalsIgnoreCase("sugarInterval"))
				sugarInterval = paraMap.get(key);
		}
		setPara();
		setSugarLevel();
		myVisionPos = getVisionPos((int) myVision);
	}

	@Override
	public void updateInfo(List<Cell> neighborlist, List<int[]> emptyPos) {
		super.updateInfo(neighborlist, emptyPos);
		if (this.state() == PATCH) {
			patchUpdate();
		}
		else {
			agentUpdate(neighborlist, emptyPos);
		}
	}
	
	@Override
	public void update(List<Cell> removeCellList, List<Cell> newCellList, List<int[]> emptyPos) {
		if (myIsDie) {
			int[] nextPos = { this.nextrow(), this.nextcol() };
			emptyPos.add(nextPos);
			removeCellList.add(this);
		}
		super.update(removeCellList, newCellList, emptyPos);
	}

	private void agentUpdate(List<Cell> neighborlist, List<int[]> emptyPos) {
		move(neighborlist, emptyPos);
		checkDie();
		int[] originPos = {this.row(), this.column()};
		emptyPos.add(originPos);
		Iterator<int[]> emptyIter = emptyPos.iterator();
		while(emptyIter.hasNext()) {
			int[] nextEmpty = emptyIter.next();
			if (nextEmpty[0] == this.nextrow() && nextEmpty[1] == this.nextcol()) {
				emptyIter.remove();
				break;
			}
		}
	}

	private void move(List<Cell> neighborlist, List<int[]> emptyPos) {
		double currentMax = 0;
		int[] currentPos = new int[2];
		currentPos[0] = LARGE;
		currentPos[1] = LARGE;
		SugarScape maxCell = this;
		List<int[]> movablePos = getMovable(neighborlist, emptyPos);
		for (int[] current: movablePos) {
			SugarScape currentCell = (SugarScape) getCell(current[0], current[1], neighborlist);
			if (currentCell.sugar() >= currentMax) {
				currentMax = currentCell.sugar();
				if (dist(currentCell.row(), currentCell.column()) <= dist(currentPos[0], currentPos[1])) {
					currentPos[0] = currentCell.row();
					currentPos[1] = currentCell.column();
					maxCell = currentCell;
				}
			}
		}
		this.setNextRow(currentPos[0]);
		this.setNextCol(currentPos[1]);
		this.setSugar(maxCell.sugar());
		maxCell.setSugar(0);
		this.setSugar(this.sugar() - sugarMeta);
	}
	
	private void checkDie() {
		if (mySugar <= 0) {
			myIsDie = true;
		}
	}
	private double dist(int row, int col) {
		return (row - this.row())*(row - this.row()) + (col - this.column())*(col - this.column());
	}
	private Cell getCell(int row, int col, List<Cell> neighborlist) {
		for (Cell current: neighborlist) {
			if (current.row() == row && current.column() == col) {
				return current;
			}
		}
		return null;
	}
	/**
	 * get rid of preoccupied posiiton from the empty neighbor list
	 * @param neighborlist
	 * @param emptyPos
	 * @return list of movable position
	 */
	private List<int[]> getMovable(List<Cell> neighborlist, List<int[]> emptyPos) {
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
		return movablePos;
	}
	
	private void patchUpdate() {
		mySugarGrow++;
		checkSugarGrow();
	}
	
	private void checkSugarGrow() {
		if (mySugarGrow >= sugarInterval) {
			mySugar = mySugar + sugarRate;
			mySugarGrow = 0;
		}
		if (mySugar >= maxSugar) {
			mySugar = maxSugar;
		}
	}
	
	private void setPara() {
		initSugar = initSugarMin + this.randomIndex((int) (initSugarMax-initSugarMin+1));
		sugarMeta = sugarMetaMin + this.randomIndex((int) (sugarMetaMax-sugarMetaMin+1));
		myVision = myVisionMin + this.randomIndex((int) (myVisionMax-myVisionMin+1));
	}
	
	private void setSugarLevel() {
		this.setNextRow(this.row());
		this.setNextCol(this.column());
		if (this.state() == -1) {
			this.setNextState(AGENT);
			mySugar = initSugar;
		}
		else {
			maxSugar = this.state();
			mySugar = maxSugar;
			this.setNextState(PATCH);;
		}
		this.update(new ArrayList<Cell>(), new ArrayList<Cell>(), new ArrayList<int[]>());
	}
	
	public List<int[]> visionList() {
		return myVisionPos;
	}
	
	public double sugar() {
		return mySugar;
	}
	
	public void setSugar(double sugar) {
		mySugar = sugar;
	}
	
	private List<int[]> getVisionPos(int vision) {
		return super.visionPos(vision);
	}

}
