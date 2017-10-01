package config;

import java.io.File;

public class XMLPredatorPreyRandom extends XMLRandom{

	private int fishBreedTime, fishDieTime, sharkBreedTime, sharkDieTime;
	private static final int MAX_BREED_TIME = 5;
	
	
	public XMLPredatorPreyRandom(File xmlInput) {
		super(xmlInput);
		
		setFishBreedTime();
	}

	private void setFishBreedTime() {
		
		fishBreedTime = (int) ((Math.random()+1)*MAX_BREED_TIME);
		sharkBreedTime = (int) ((Math.random()+1)*MAX_BREED_TIME);
		
		// ensures fish/sharks always breed slower than they die
		fishDieTime = (int) (Math.random()*(fishBreedTime));
		sharkDieTime = (int) (Math.random()*(sharkBreedTime));
		
		parameterMap.replace("fishDieTime", (double) fishDieTime);
		parameterMap.replace("sharkDieTime", (double) sharkDieTime);
		parameterMap.replace("fishBreedTime", (double) fishBreedTime);
		parameterMap.replace("sharkBreedTime", (double) sharkBreedTime);

		System.out.println("New Parameter Map: " + parameterMap);
		
	}

}
