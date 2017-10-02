package visualization;

import java.util.Map;

import cell.CellManager;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangleCell extends Rectangle {

	private int myState;
	private int newState;
	private Color newColor;
	private CellManager myCellManager;
	private int myRow;
	private int myCol;

	public RectangleCell(int row, int col, double width, double height, Color color, int state, Map<Integer, Color> colorMap) {
		this.setWidth(width);
		this.setHeight(height);
		this.setFill(color);
		myRow = row;
		myCol = col;
		myState = state;

		// create Array consisted of keys from colorMap
		// wraps Array. For example, if keys are -1,0,1, the colorKeyArray is
		// [-1,0,1,-1]

		Object[] tempArray = colorMap.keySet().toArray();
		int[] colorKeyArray = new int[colorMap.size() + 1];

		for (int i = 0; i < tempArray.length; i++) {
			colorKeyArray[i] = (int) tempArray[i];
			if (i == tempArray.length - 1) {
				colorKeyArray[i + 1] = (int) tempArray[0];
			}
		}

		setNewState(colorMap, colorKeyArray);

		this.setOnMouseClicked(e -> this.changeColor(newState, newColor, colorMap, colorKeyArray));
	}

	private void setNewState(Map<Integer, Color> colorMap, int[] colorKeyArray) {
		for (int i = 0; i < colorKeyArray.length - 1; i++) {
			if (colorKeyArray[i] == myState) {
				newState = colorKeyArray[i + 1];
				newColor = colorMap.get(newState);
			}
		}
	}

	public void changeColor(int newState, Color newColor, Map<Integer, Color> colMap, int[] stateArray) {
		this.setFill(newColor);
		this.setState(newState);
		this.setNewState(colMap, stateArray);
		myCellManager.changeState(myRow, myCol, newState);
	}

	public int getState() {
		return myState;
	}

	public void setState(int state) {
		myState = state;
	}
	
	public void setManager(CellManager c) {
		myCellManager = c;
	}

}
