package config;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cell.Cell;
import javafx.scene.paint.Color;

import org.w3c.dom.Node;

/**
 * This is the class that parses the XML file.
 * 
 * @author DavidTran
 *
 */
public class XMLReader {

	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private Document doc;
	private File xmlFile;

	private String simulationType;
	private Map<Integer, Color> colorMap;
	private Cell[][] cellGrid;
	private int numRows;
	private int numCols;

	
	/**
	 * Initialize DOMParser, colorMap, cellStateGrid, simulationType; 
	 * 
	 * @param xmlInput - file to be chosen by user.
	 */
	public XMLReader(File xmlInput) {
		xmlFile = xmlInput;

		initDOMParser();

		createColorMap();
		
		createCellGrid();

	}
	
	
	/**
	 * Initialize XML file parser.
	 */
	public void initDOMParser() {
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			simulationType = doc.getDocumentElement().getAttribute("type");
			System.out.println("Simulation Type: " + simulationType);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error with XML File");
			// add more "error" code later
		}
	}

	/**
	 * Generate a mapping of cell states and color.
	 */
	private void createColorMap() {
		Map<Integer, Color> colorMap = new HashMap<Integer, Color>();

		NodeList nList = doc.getElementsByTagName("color");

		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			// System.out.println(nNode.getNodeName());

			Element eElement = (Element) nNode;
			Integer state = Integer.parseInt(eElement.getAttribute("cellState"));
			Color color = Color.valueOf(eElement.getAttribute("color"));

			colorMap.put(state, color);
		}
	}
	
	/**
	 * Generate a grid of cell states.
	 */
	public void createCellGrid() {
		
		NodeList nList = doc.getElementsByTagName("row");
		numRows = nList.getLength();
		numCols = numRows;
		
		cellGrid = new Cell[numRows][numCols];
		
		int[] gridSize = {numRows,numCols};
		
		for (int i = 0; i < numRows; i++) {
			Node row = nList.item(i);
			String rowString = ((Element) row).getAttribute("cellStates");
			List<String> colStates = Arrays.asList(rowString.toString().split(","));
			System.out.println(colStates);
			
			for (int j = 0; j < colStates.size(); j++) {
//				cellGrid[i][j] = new Cell(i, j, colStates.get(j), gridSize);
			}
			
			
		}
		
	}

	
	/**
	 * Tests the XML reader for parsing.
	 */
	public static void main(String args[]) {
		File xml = new File("/Users/DavidTran/eclipse-workspace/cellsociety_team10/src/resources/gameOfLife.xml");
		XMLReader reader = new XMLReader(xml);
	}
}
