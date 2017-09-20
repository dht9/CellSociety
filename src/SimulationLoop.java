import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SimulationLoop {
	
	private int fps = 60;
	
	public void start() {
		KeyFrame frame = new KeyFrame(Duration.millis(1000 / fps), e -> step());
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	
	private void step() {
		//do stuff
	}

	public Scene setupScene(Stage s, int guiWidth, int guiHeight) {
		
		// need to call method that creates buttons and waits for XML input via button
		
		Scene scene = new Scene(new Group(), guiWidth, guiHeight);
			
		return scene;
	}

	
	

}
