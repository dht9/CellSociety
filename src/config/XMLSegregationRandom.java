package config;

import java.io.File;

public class XMLSegregationRandom extends XMLRandom {

	private double satisfiedThreshold; 
	
	public XMLSegregationRandom(File xmlInput) {
		super(xmlInput);
		
		setSatisfiedThreshold();
	}

	private void setSatisfiedThreshold() {
		
		satisfiedThreshold = Math.random();
		parameterMap.replace("satisfiedThreshold", satisfiedThreshold);
		
		System.out.println("New Parameter Map: " + parameterMap);
		
	}

}
