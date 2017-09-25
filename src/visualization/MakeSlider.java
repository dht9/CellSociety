package visualization;

import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;

public class MakeSlider {
	private int animationSpeed;
	private Timeline animation;
	private Slider mySlider;
	
	public MakeSlider(Timeline timeline) {
		animationSpeed = 1;
		animation = timeline;
	}
	
//	public Node initialize() {
//		Pane pane = new Pane();
//		mySlider = createSlider();
//		pane.getChildren().add(mySlider);
//		return pane;
//	}
	
	public Slider createSlider(int fps) {
		mySlider = new Slider(0,fps,1);
		mySlider.setShowTickMarks(true);
		mySlider.setShowTickLabels(true);
		return mySlider;
	}
	
	public Slider getSlider() {
		return mySlider;
	}
	
	public int getSpeed() {
		return animationSpeed;
	}
	
	public void setSpeed(int speed) {
		animationSpeed = speed;
	}
	
	public Timeline getTimeline() {
		return animation;
	}
	
	public void changeSpeed(int fps) {
		double newSpeed = mySlider.getValue() / (double) fps;
		System.out.println(newSpeed);
		animation.setRate(newSpeed);
	}
}
