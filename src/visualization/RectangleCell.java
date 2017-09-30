package visualization;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangleCell extends Rectangle {

	private int myState;
	
	public RectangleCell(double width, double height, Color color, int state) {
		this.setWidth(width);
		this.setHeight(height);
		this.setFill(color);
		myState = state;
	}
	
	public int getState() {
		return myState;
	}
	
	public void setState(int state) {
		myState = state;
	}

	
	
}
