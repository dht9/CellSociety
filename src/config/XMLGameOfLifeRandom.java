package config;

import java.io.File;

/**
 * Sub-class for XML Random Configuration for Game of Life.
 * 
 * @author DavidTran
 *
 */
public class XMLGameOfLifeRandom extends XMLRandom {

	/**
	 * Constructor that sets appropriate Game of Life parameters. Currently, there are no RPS
	 * specific parameters to randomly initialize.
	 * 
	 * @param xmlInput
	 */
	public XMLGameOfLifeRandom(File xmlInput) {
		super(xmlInput);
	}
}
