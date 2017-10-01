package config;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;

/**
 * This is the class that parses the input XML file.
 * 
 * @author DavidTran
 *
 */
public class XMLReader {

	private ResourceBundle myResources = ResourceBundle.getBundle("resources/Text");
	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private Document doc;
	private File xmlFile;

	private String simulationType;
	private String edgeType;
	private Map<Integer, Color> colorMap;
	private Map<Integer, String> stateNameMap;
	private Map<String, Double> parameterMap;
	private int[][] stateGrid;
	private int numRows;
	private int numCols;
	private int neighborType;

	/**
	 * Initialize DOMParser, colorMap, cellStateGrid, simulationType;
	 * 
	 * @param xmlInput
	 *            file to be chosen by user.
	 * 
	 */
	public XMLReader(File xmlInput) {

		xmlFile = xmlInput;

		initDOMParser();

		simulationType = setSimulationType();
		System.out.println("Simulation Type: " + simulationType);

		edgeType = setEdgeType();
		System.out.println("Edge Type: " + edgeType);

		neighborType = setNeighborType();
		System.out.println("Neighbor Type: " + neighborType);
		
		colorMap = createColorMap();
		for (Map.Entry<Integer, Color> e : colorMap.entrySet()) {
			System.out.println(e.getKey() + ":" + e.getValue());
		}

		stateNameMap = createStateNameMap();
		System.out.println("StateNameMap:" + stateNameMap);

		parameterMap = createParameterMap();
		System.out.println("Parameters: " + parameterMap);

		stateGrid = createStateGrid();

	}

	/**
	 * Initialize XML file parser.
	 */
	private void initDOMParser() {

		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
		} catch (IOException e) {
			e.printStackTrace();
			showError(e.getMessage());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			showError(e.getMessage());
		} catch (SAXException e) {
			e.printStackTrace();
			showError(e.getMessage());
		}

	}

	private void showError(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(myResources.getString("ErrorTitleXML"));
		alert.setContentText(message);
		alert.showAndWait();
	}

	public File setFile() {
		return xmlFile;
	}

	/**
	 * Retrieve the name of the simulation type.
	 */
	public String setSimulationType() {

		Element typeElement = (Element) doc.getElementsByTagName("simulation").item(0);
		return typeElement.getAttribute("type");
	}

	/**
	 * Retrieve the name of the edge type.
	 */
	public String setEdgeType() {

		NodeList nList = doc.getElementsByTagName("edge");
		Element element = (Element) nList.item(0);

		return element.getAttribute("type");
	}
	
	/**
	 * Retrieve the neighbor type.
	 */
	public int setNeighborType() {

		NodeList nList = doc.getElementsByTagName("neighbor");
		Element element = (Element) nList.item(0);
		
		return Integer.parseInt(element.getAttribute("type"));
	}

	/**
	 * Generate a mapping of cell state number and color.
	 */
	public Map<Integer, Color> createColorMap() {

		try {
			colorMap = new HashMap<Integer, Color>();
			NodeList nList = doc.getElementsByTagName("colormap");

			for (int i = 0; i < nList.getLength(); i++) {

				Node nNode = nList.item(i);
				Element eElement = (Element) nNode;

				Integer state = Integer.parseInt(eElement.getAttribute("stateNum"));
				Color color = Color.valueOf(eElement.getAttribute("color"));
				colorMap.put(state, color);
			}
		} catch (NumberFormatException e) {
			showError(e.getMessage() + "; the string does not contain a parsable integer for tag 'stateNum'");
		}

		// System.out.println("Colormap: " + colorMap);
		return colorMap;
	}

	/**
	 * Generate a mapping of cell state number and name
	 * 
	 * @return
	 */
	public Map<Integer, String> createStateNameMap() {
		try {
			stateNameMap = new HashMap<Integer, String>();
			NodeList nList = doc.getElementsByTagName("statemap");

			for (int i = 0; i < nList.getLength(); i++) {

				Node nNode = nList.item(i);
				Element eElement = (Element) nNode;

				Integer stateNum = Integer.parseInt(eElement.getAttribute("stateNum"));
				String stateName = eElement.getAttribute("name");
				stateNameMap.put(stateNum, stateName);
			}
		} catch (NumberFormatException e) {
			showError(e.getMessage() + "; the string does not contain a parsable integer for tag 'stateNum'");
		}

		return stateNameMap;

	}

	/**
	 * Generate a mapping of parameter name and value.
	 * 
	 * @return
	 */
	public Map<String, Double> createParameterMap() {
		try {
			parameterMap = new HashMap<String, Double>();
			NodeList nList = doc.getElementsByTagName("parametermap");

			for (int i = 0; i < nList.getLength(); i++) {

				Node nNode = nList.item(i);
				Element eElement = (Element) nNode;

				String name = eElement.getAttribute("name");
				Double value = Double.parseDouble(eElement.getAttribute("value"));
				parameterMap.put(name, value);
			}
		} catch (NumberFormatException e) {
			showError(e.getMessage() + "; the string does not contain a parsable integer for tag 'value'");
		}

		return parameterMap;
	}

	/**
	 * Generate a grid of cell states.
	 */
	public int[][] createStateGrid() {

		NodeList nList = doc.getElementsByTagName("row");

		numRows = nList.getLength();
		numCols = numRows;
		stateGrid = new int[numRows][numCols];

		// iterate through row entries
		for (int i = 0; i < numRows; i++) {

			Node currentRow = nList.item(i);

			String row = ((Element) currentRow).getAttribute("stateNum");
			List<String> colStates = Arrays.asList(row.toString().split("\\s*,\\s*"));
			System.out.println(colStates);

			// iterate through each column in for current row
			for (int j = 0; j < numCols; j++) {

				String trim = colStates.get(j).trim();
				stateGrid[i][j] = Integer.parseInt(trim);
			}
		}
		// System.out.println(Arrays.deepToString(stateGrid));

		return stateGrid;
	}

	/**
	 * Tests the XML reader for parsing.
	 */
	// public static void main(String args[]) {
	// File xml = new
	// File("/Users/DavidTran/eclipse-workspace/cellsociety_team10/src/resources/segregation.xml");
	// XMLReader reader = new XMLReader(xml);
	// }

}
