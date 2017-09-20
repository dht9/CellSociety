package config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javafx.scene.paint.Color;

import org.w3c.dom.Node;

public class XMLReader {

	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private Document doc;
	private File xmlFile;

	private String simulationType;
	private Map<Integer, Color> colorMap;
	private int[][] cellGrid;

	
	/**
	 * Initialize DOMParser, colorMap, cellStateGrid, simulationType; 
	 * 
	 * @param xmlInput - file to be chosen by user.
	 */
	public XMLReader(File xmlInput) {
		xmlFile = xmlInput;

		initDOMParser();

		createColorMap();

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
		
	}

	
	/**
	 * Tests the XML reader for parsing.
	 */
	public static void main(String args[]) {
		File xml = new File("/Users/DavidTran/eclipse-workspace/cellsociety_team10/src/resources/gameOfLife.xml");
		XMLReader reader = new XMLReader(xml);
	}
}
