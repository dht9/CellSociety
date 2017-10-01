package config;

import java.io.File;
import java.util.Random;

public class XMLRandom extends XMLReader {

	private InputGridSize dialog;

	public XMLRandom(File xmlInput) {
		super(xmlInput);

		askForGridSize();

		createRandomStateGrid();

		edgeType = setEdgeType();
		neighborType = setNeighborType();

	}

	private void askForGridSize() {
		dialog = new InputGridSize();
		numRows = dialog.getInputSize("rows:");
		numCols = dialog.getInputSize("columns:");
	}

	@Override
	public int setNeighborType() {
		String[] types = { "4", "8" };
		Random random = new Random();
		Integer randomElement = random.nextInt(types.length);
		neighborType = Integer.parseInt(types[randomElement]);

		System.out.println("New neighbor type: " + neighborType);
		return neighborType;
	}

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
