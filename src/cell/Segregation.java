package cell;


import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Segregation extends Cell{
	
	private double myThreshold;

	public Segregation(int row, int column, int state, int[] gridSize, Map<String,Double> paraMap, String edgeType) {
		super(row, column, state, gridSize, paraMap, edgeType);
		
		for (String key: paraMap.keySet()) {
		    if (key.equalsIgnoreCase("satisfiedThreshold"))
		    	myThreshold = paraMap.get(key);
		    	
		}
	}
	
	@Override
	public void updateInfo(List<Cell> neighborlist, List<int[]> emptyPos) {
		super.updateInfo(neighborlist, emptyPos);
		double sameCount = 0;
		for (Cell neighbor: neighborlist) {
			if (this.state() == neighbor.state()) {
				sameCount += 1;
			}
		}
		double percent = sameCount/neighborlist.size();
		if (neighborlist.size() == 0) {
			percent = 0;
		}
		if (percent < myThreshold) {
			int[] nextPos = randomMove(emptyPos);
			this.setNextRow(nextPos[0]);
			this.setNextCol(nextPos[1]);
			int[] currentPos = {this.row(), this.column()};
			emptyPos.add(currentPos);
			Iterator<int[]> emptyIter = emptyPos.iterator();
			while(emptyIter.hasNext()) {
				int[] nextEmpty = emptyIter.next();
				if (nextEmpty[0] == this.nextrow() && nextEmpty[1] == this.nextcol()) {
					emptyIter.remove();
				}
			}
		}
	}
	
	@Override
	public void update(List<Cell>removeCellList, List<Cell> newCellList, List<int[]> emptyPos) {
		super.update(removeCellList, newCellList, emptyPos);
	}
	
	private int[] randomMove(List<int[]> emptyPos) {
		int randomIndex = (int) (Math.random()*(emptyPos.size()));
		int[] randomPos = emptyPos.get(randomIndex);
		return randomPos;
	}

}
