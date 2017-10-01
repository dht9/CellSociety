package config;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class XMLFireRandom extends XMLRandom {

	private double probCatch;

	public XMLFireRandom(File xmlInput) {
		super(xmlInput);

		setProbCatch();
	}

	private void setProbCatch() {
		probCatch = Math.random();
		parameterMap.replace("probCatch", probCatch);
		System.out.println("New Parameter Map: " + parameterMap);
	}

	

}
