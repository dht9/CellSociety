package visualization;

import javafx.scene.control.Slider;

public class MakeSlider {
	private Slider mySlider;
	
	public MakeSlider(double maxFPS) {

		mySlider = new Slider(1,maxFPS,1);
		mySlider.setShowTickMarks(true);
		mySlider.setShowTickLabels(true);
		mySlider.setMajorTickUnit(10);
//		mySlider.setMinorTickCount(1);
		
	}
	
	public Slider getSlider() {
		return mySlider;
	}

	public double getValue() {
		return mySlider.getValue();
	}
}
