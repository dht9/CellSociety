package config;

import java.io.File;
import java.util.Random;

/**
 * Super class for XML Random Configurations.
 * 
 * @author DavidTran
 *
 */
public abstract class XMLRandom extends XMLReader {

	private InputGridSize dialog;

	/**
	 * Constructor that asks for cell grid dimensions from user and sets random
	 * parameters applicable to all simulations.
	 * 
	 * @param xmlInput - file where default/required parameters are read from
	 */
	public XMLRandom(File xmlInput) {
		super(xmlInput);

		askForGridSize();

		createRandomStateGrid();

		edgeType = setEdgeType();
		neighborType = setNeighborType();

	}

	/**
	 * Gets new rows/column size from user.
	 */
	private void askForGridSize() {
		dialog = new InputGridSize();
		numRows = dialog.getInputSize("rows:");
		numCols = dialog.getInputSize("columns:");
	}

	/**
	 * Set random neighbor detection type.
	 */
	@Override
	public int setNeighborType() {
		String[] types = { "4", "8" };
		Random random = new Random();
		Integer randomElement = random.nextInt(types.length);
		neighborType = Integer.parseInt(types[randomElement]);

		System.out.println("New neighbor type: " + neighborType);
		return neighborType;
	}

	/**
	 * Set random edge detection type.
	 */
	@Override
	public String setEdgeType() {
		String[] types = { "straight", "toroidal" };
		Random random = new Random();
		Integer randomElement = random.nextInt(types.length);
		edgeType = types[randomElement];

		System.out.println("New edge type: " + edgeType);

		return edgeType;
	}

}
