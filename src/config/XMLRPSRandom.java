package config;

import java.io.File;

/**
 * XML Random Configurator for Rock Paper Scissors.
 * 
 * @author DavidTran
 *
 */
public class XMLRPSRandom extends XMLRandom {

	/**
	 * Constructor that reads any default parameters from XML file. Currently, there are no RPS
	 * specific parameters to randomly initialize.
	 * 
	 * @param xmlInput
	 */
	public XMLRPSRandom(File xmlInput) {
		super(xmlInput);
	}
}
