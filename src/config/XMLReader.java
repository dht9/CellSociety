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
	private int[][] cellStateGrid;

	// private Element classElement;

	public XMLReader(File xmlInput) {
		xmlFile = xmlInput;

		initDOMParser();

		createColorMap();

	}

	public void initDOMParser() {
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			System.out.println("Simulation Type: " + doc.getDocumentElement().getAttribute("type"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error with XML File");
			// add more "error" code later
		}
	}

	private void createColorMap() {
		Map<Integer, Color> colorMap = new HashMap<Integer, Color>();

		NodeList nList = doc.getElementsByTagName("color");

		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			//System.out.println(nNode.getNodeName());

			Element eElement = (Element) nNode;
			Integer state = Integer.parseInt(eElement.getAttribute("cellState"));
			Color color = Color.valueOf(eElement.getAttribute("color"));

			colorMap.put(state, color);
		}
	}

	public static void main(String args[]) {

		XMLReader reader = new XMLReader(
				new File("/Users/DavidTran/eclipse-workspace/cellsociety_team10/src/resources/gameOfLife.xml"));
	}
}
