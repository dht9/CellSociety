package config;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Sub-class for XML Random Configuration for Fire Simulation.
 * 
 * @author DavidTran
 *
 */
public class XMLFireRandom extends XMLRandom {

	private double probCatch;

	/**
	 * Constructor that sets appropriate parameter value.
	 * 
	 * @param xmlInput
	 */
	public XMLFireRandom(File xmlInput) {
		super(xmlInput);

		setProbCatch();
	}

	/*
	 * Set Fire simulation specific parameters.
	 */
	private void setProbCatch() {
		probCatch = Math.random();
		parameterMap.replace("probCatch", probCatch);
		System.out.println("New Parameter Map: " + parameterMap);
	}

	

}
