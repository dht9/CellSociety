package config;

import java.io.File;

/**
 * Sub-class for XML Random Configuration for Predator-Prey Simulation.
 * 
 * @author DavidTran
 *
 */
public class XMLPredatorPreyRandom extends XMLRandom{

	private int fishBreedTime, fishDieTime, sharkBreedTime, sharkDieTime;
	private static final int MAX_BREED_TIME = 5;
	
	/**
	 * Constructor that sets appropriate simulation parameters.
	 * 
	 * @param xmlInput
	 */
	public XMLPredatorPreyRandom(File xmlInput) {
		super(xmlInput);
		
		setFishBreedTime();
	}

	/**
	 * Sets Predator-Prey simulation specific parameters.
	 */
	private void setFishBreedTime() {
		
		fishBreedTime = (int) ((Math.random()+1)*MAX_BREED_TIME);
		sharkBreedTime = (int) ((Math.random()+1)*MAX_BREED_TIME);
		
		// ensures fish/sharks always breed slower than they die
		fishDieTime = (int) ((Math.random()+1)*(fishBreedTime));
		sharkDieTime = (int) ((Math.random()+1)*(sharkBreedTime));
		
		parameterMap.replace("fishDieTime", (double) fishDieTime);
		parameterMap.replace("sharkDieTime", (double) sharkDieTime);
		parameterMap.replace("fishBreedTime", (double) fishBreedTime);
		parameterMap.replace("sharkBreedTime", (double) sharkBreedTime);

		System.out.println("New Parameter Map: " + parameterMap);
		
	}

}
