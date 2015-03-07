package model;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLWriter {
	public XMLWriter(Map<String, Double> variableMap, List<XMLInstruction> userInstructions) {

		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();

			// create the userInfo root element
			Document xmlDoc = docBuilder.newDocument();
			Element rootElement = xmlDoc.createElement("userInfo");
			xmlDoc.appendChild(rootElement);

			// create userVariables child of userInfo
			Element userVariables = xmlDoc.createElement("userVariables");
			rootElement.appendChild(userVariables);

			// iterate through variable map and make all variables
			for (String variable : variableMap.keySet()) {
				Element newVariable = xmlDoc.createElement(variable);
				newVariable.appendChild(xmlDoc.createTextNode(Double.toString(variableMap
						.get(variable))));
				userVariables.appendChild(newVariable);
			}

			// make userCommands element, which all instructions will be children of
			Element userCommands = xmlDoc.createElement("userCommands");
			rootElement.appendChild(userCommands);

			// make instructions
			for(XMLInstruction currentInstruction: userInstructions){
				String instructionName = currentInstruction.getInstructionName();
				String instructionVariables = currentInstruction.getVariables();
				String instructionCommands = currentInstruction.getUserCommands();
				
				Element newInstruction = xmlDoc.createElement(instructionName);
				
				Element newInstructionVariables = xmlDoc.createElement("variables");
				newInstructionVariables.appendChild(xmlDoc.createTextNode(instructionVariables));
				
				Element newInstructionCommands = xmlDoc.createElement("commands");
				newInstructionCommands.appendChild(xmlDoc.createTextNode(instructionCommands));
				
				newInstruction.appendChild(newInstructionVariables);
				newInstruction.appendChild(newInstructionCommands);
				userCommands.appendChild(newInstruction);
			}
			

			// make xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(xmlDoc);
			StreamResult result = new StreamResult(new File("userTestFile.xml"));
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
