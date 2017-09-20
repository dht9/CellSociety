import javafx.stage.Stage;

/**
 * This class starts the simulation.
 * 
 * @author DavidTran, Estelle He, Ryan Chung
 *
 */
public class Main {
	
	public static void main(String args[]) {
		
		SimulationSetup setup = new SimulationSetup();
		
		setup.startSimulation(args);
	}

}
