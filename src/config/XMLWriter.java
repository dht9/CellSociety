package config;

import java.io.File;
import java.nio.file.Paths;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;

/**
 * Class to write current simulation configurations to an XML file.
 * 
 * @author DavidTran
 *
 */
public class XMLWriter {

	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private Document doc;
	TransformerFactory transformerFactory;
	Transformer transformer;
	DOMSource source;
	StreamResult console;
	StreamResult file;

	private String simulationType;
	private String edgeType;
	private int neighborType;
	private Map<Integer, Color> colorMap;
	private Map<Integer, String> stateNameMap;
	private Map<String, Double> parameterMap;
	private int[][] stateGrid;
	private int numRows;
	private int numCols;

	private ResourceBundle myResources = ResourceBundle.getBundle("resources/Text");

	/**
	 * Constructor to initialize document factory.
	 * 
	 * @param simType
	 * @param edgeType
	 * @param cMap
	 * @param sMap
	 * @param pMap
	 * @param sGrid
	 */
	public XMLWriter() {
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.newDocument();

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showError(e.getMessage());
		}
	}

	/*
	 * Initialize data to be written.
	 */
	public void setNewSimulationParameters(XMLReader xmlReaderInput) {

		colorMap = xmlReaderInput.createColorMap();
		stateNameMap = xmlReaderInput.createStateNameMap();
		parameterMap = xmlReaderInput.createParameterMap();
		stateGrid = xmlReaderInput.createStateGrid();
		edgeType = xmlReaderInput.setEdgeType();
		simulationType = xmlReaderInput.setSimulationType();
		neighborType = xmlReaderInput.setNeighborType();

	}
	
	public void setNewStateGrid(int[][] newStateGrid) {
		stateGrid = newStateGrid;
	}

	/*
	 * Write data to XML doc.
	 */
	public void writeToXML() {
		try {
			// set root
			Element rootElement = doc.createElement("simulation");
			rootElement.setAttribute("type", simulationType);
			doc.appendChild(rootElement);

			// set colors
			Element colorMapElement = doc.createElement("colormap");
			for (int key : colorMap.keySet()) {
				Element child = doc.createElement("color");
				child.setAttribute("stateNum", Integer.toString(key));
				child.setAttribute("color", colorMap.get(key).toString());

				colorMapElement.appendChild(child);
			}
			rootElement.appendChild(colorMapElement);

			// set stateName
			Element stateMapElement = doc.createElement("statemap");
			for (int key : stateNameMap.keySet()) {
				Element child = doc.createElement("state");
				child.setAttribute("stateNum", Integer.toString(key));
				child.setAttribute("name", stateNameMap.get(key));
				stateMapElement.appendChild(child);
			}
			rootElement.appendChild(stateMapElement);

			// set parameters
			Element parameterMapElement = doc.createElement("parametermap");
			for (String key : parameterMap.keySet()) {
				Element child = doc.createElement("parameter");
				child.setAttribute("name", key);
				child.setAttribute("value", Double.toString(parameterMap.get(key)));
				parameterMapElement.appendChild(child);
			}
			rootElement.appendChild(parameterMapElement);

			// set edge type
			Element edgeElement = doc.createElement("edges");
			Element edgeChild = doc.createElement("edge");
			edgeChild.setAttribute("type", edgeType);
			edgeElement.appendChild(edgeChild);
			rootElement.appendChild(edgeElement);

			// set neighbor type
			Element neighborElement = doc.createElement("neighbors");
			Element neighborChild = doc.createElement("neighbor");
			neighborChild.setAttribute("type", Integer.toString(neighborType));
			neighborElement.appendChild(neighborChild);
			rootElement.appendChild(neighborElement);

			// set grid
			Element gridElement = doc.createElement("grid");
			for (int row = 0; row < stateGrid.length; row++) {
				Element rowElement = doc.createElement("row");

				StringBuilder sb = new StringBuilder();
				for (int col = 0; col < stateGrid[row].length; col++) {
					sb.append(stateGrid[row][col]);
					if (col != stateGrid[row].length - 1)
						sb.append(",");
				}
				rowElement.setAttribute("stateNum", sb.toString());
				gridElement.appendChild(rowElement);

			}
			rootElement.appendChild(gridElement);
			
		} catch (DOMException e) {
			e.printStackTrace();
			showError(e.getMessage());
		}

	}

	/*
	 * Output file into data folder.
	 */
	public void outputXML(String filePath) {

		try {
			transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");
			source = new DOMSource(doc);
			
			console = new StreamResult(System.out);
			transformer.transform(source, console);
			
			file = new StreamResult(new File(filePath));
			transformer.transform(source, file);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showError(e.getMessage());
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void showError(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(myResources.getString("ErrorTitleXML"));
		alert.setContentText(message);
		alert.showAndWait();
	}

}
