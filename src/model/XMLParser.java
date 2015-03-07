package model;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {

	private Map<String, Double> myVariableMap = new HashMap<String, Double>();
	private List<XMLInstruction> myUserInstructions = new ArrayList<XMLInstruction>(); 

	public XMLParser(File XMLFile) {
		try {
			Document doc = initializeDoc(XMLFile);
			clean(doc.getDocumentElement().getParentNode());
			readFile(doc);
		} catch (InvalidParameterException e) {
			throw new InvalidParameterException();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readFile(Document doc) {
		NodeList userVariableChildren = initializeNodeList(doc, "userVariables");
		readVariables(userVariableChildren);
		NodeList userCommandChildren = initializeNodeList(doc, "userCommands");
		readInstructions(userCommandChildren);
	}

	private void readVariables(NodeList variableChildren) {
		for (int i = 0; i < variableChildren.getLength(); i++) {
			Node currentParam = variableChildren.item(i);
			myVariableMap.put(currentParam.getNodeName(),
					Double.parseDouble(currentParam.getTextContent()));
		}
	}
	
	private void readInstructions(NodeList instructionChildren) {
		for (int i = 0; i < instructionChildren.getLength(); i++) {
			Node currentInstruction = instructionChildren.item(i);
			NodeList instructionDetails = currentInstruction.getChildNodes();
			Node instructionVariables = instructionDetails.item(0);
			Node instructionCommands = instructionDetails.item(1);
			XMLInstruction newXMLInstruction = new XMLInstruction(
					currentInstruction.getNodeName(),
					instructionVariables.getTextContent(),
					instructionCommands.getTextContent());
			myUserInstructions.add(newXMLInstruction);
		}
	}
	
	/**
	 * Method that cleans white space and stray characters out of XML file Taken
	 * from:
	 * http://stackoverflow.com/questions/978810/how-to-strip-whitespace-only
	 * -text-nodes-from-a-dom-before-serialization/16285664#16285664
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