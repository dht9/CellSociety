package config;

import java.io.File;

/**
 * Sub-class for XML Random Configuration for Segregation simulation. 
 * 
 * @author DavidTran
 *
 */
public class XMLSegregationRandom extends XMLRandom {

	private double satisfiedThreshold; 

	/**
	 * Construction that sets appropriate simulation parameters.
	 * 
	 * @param xmlInput
	 */
	public XMLSegregationRandom(File xmlInput) {
		super(xmlInput);
		
		setSatisfiedThreshold();
	}

	/*
	 * Set fire specific parameters.
	 */
	private void setSatisfiedThreshold() {
		
		satisfiedThreshold = Math.random();
		parameterMap.replace("satisfiedThreshold", satisfiedThreshold);
		
		System.out.println("New Parameter Map: " + parameterMap);
		
	}

}
