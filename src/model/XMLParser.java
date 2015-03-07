package model;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {

	private HashMap<String, String> myParameters = new HashMap<String, String>();
	private Integer myGrid[][];

	public XMLParser(File XMLFile) throws InvalidParameterException {
		try {
			checkFileExtension(XMLFile);
			Document doc = initializeDoc(XMLFile);
			clean(doc.getDocumentElement().getParentNode());
			if (doc.getDocumentElement().getNodeName().equals("simulation")) {
				readSimFile(doc);
			} else {
				readStyleFile(doc);
			}
		} catch (InvalidParameterException e) {
			throw new InvalidParameterException();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads the style file document using the read Parameters method
	 * 
	 * @param doc
	 */
	private void readStyleFile(Document doc) {
		NodeList styleChildren = initializeNodeList(doc, "parameter");
		readParameters(styleChildren);
	}

	/**
	 * Reads a sim file document Puts all parameters in the parameter map
	 * Creates grid and puts it in grid array
	 * 
	 * @param doc
	 * @throws InvalidParameterException
	 */
	private void readSimFile(Document doc) throws InvalidParameterException {
		NodeList parameterChildren = initializeNodeList(doc, "parameter");
		readParameters(parameterChildren);
		NodeList gridChildren = initializeNodeList(doc, "grid");
		createGrid(gridChildren);
		readGrid(gridChildren);
	}

	/**
	 * Method to check file extension name Could be made a try/catch but didn't
	 * due to time
	 * 
	 * @param xmlFile
	 */
	private void checkFileExtension(File xmlFile) {
		String file = xmlFile.getName();
		if (!(file.endsWith("xml"))) {
			System.out.println("Invalid file format. Exiting program.");
			System.exit(0);
		}
	}

	/**
	 * Initialize grid size
	 * 
	 * @param gridChildren
	 */
	private void createGrid(NodeList gridChildren) {
		Integer gridHeight = Integer.parseInt(myParameters.get("gridHeight"));
		Integer gridWidth = Integer.parseInt(myParameters.get("gridWidth"));
		myGrid = new Integer[gridHeight][gridWidth];
	}

	/**
	 * Read in the parameters Put them in the HashMap Also used to read in the
	 * parameters in the style sheet Uses a messy if statement to throw the
	 * exception
	 */
	private void readParameters(NodeList parameterChildren)
			throws InvalidParameterException {
		for (int i = 0; i < parameterChildren.getLength(); i++) {
			Node currentParam = parameterChildren.item(i);
			myParameters.put(currentParam.getNodeName(),
					currentParam.getTextContent());
		}
		if (!myParameters.containsKey("cellShape")
				&& (!myParameters.containsKey("simName") || myParameters
						.get("simName") == null)) {
			throw new InvalidParameterException();
		}
	}

	/**
	 * Method that cleans white space and stray characters out of XML file Taken
	 * from:
	 * http://stackoverflow.com/questions/978810/how-to-strip-whitespace-only
	 * -text-nodes-from-a-dom-before-serialization/16285664#16285664
	 * 
	 * @param node
	 */
	public static void clean(Node node) {
		NodeList childNodes = node.getChildNodes();
		for (int n = childNodes.getLength() - 1; n >= 0; n--) {
			Node child = childNodes.item(n);
			short nodeType = child.getNodeType();
			if (nodeType == Node.ELEMENT_NODE) {
				clean(child);
			} else if (nodeType == Node.TEXT_NODE) {
				String trimmedNodeVal = child.getNodeValue().trim();
				if (trimmedNodeVal.length() == 0) {
					node.removeChild(child);
				} else {
					child.setNodeValue(trimmedNodeVal);
				}
			} else if (nodeType == Node.COMMENT_NODE) {
				node.removeChild(child);
			}
		}
	}

	/**
	 * Read in the Grid row by row Create the new cells by placing ints in the
	 * grid array
	 */
	private void readGrid(NodeList gridChildren)
			throws ArrayIndexOutOfBoundsException {
		try {
			for (int i = 0; i < gridChildren.getLength(); i++) {
				Node currentRow = gridChildren.item(i);
				String rowString = currentRow.getTextContent();
				String[] splitRow = rowString.split(" ");
				for (int j = 0; j < splitRow.length; j++) {
					myGrid[i][j] = Integer.parseInt(splitRow[j]);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InvalidParameterException();
		}
	}

	/**
	 * Function to do initial set-up Returns a node list of all elements of one
	 * type Ex. returns list of parameters or list of grid rows
	 */
	private NodeList initializeNodeList(Document doc, String element) {
		NodeList elementList = doc.getDocumentElement().getElementsByTagName(
				element);
		Node elementNode = elementList.item(0);
		NodeList elementChildren = elementNode.getChildNodes();
		return elementChildren;
	}

	/**
	 * Does initial set-up of document file.
	 */
	private Document initializeDoc(File XMLFile)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(XMLFile);
		doc.getDocumentElement().normalize();
		return doc;
	}

}