package model;

/**
 * This class is currently unused in the grand scheme of things
 * This simply holds three strings as an object
 * UserCommands can then be stored in a simple text format
 * The idea is that the Parser and View could interact with this class as well
 * Looking back, it seems that a List of Strings would have also worked for this purpose...
 * @author Sid
 *
 */

public class XMLInstruction {

	private String myInstructionName;
	private String myVariables;
	private String myUserCommands;
	
	public XMLInstruction(String instructionName, String variables, String userCommands){
		myInstructionName = instructionName;
		myVariables = variables;
		myUserCommands = userCommands;
	}
	
	public String getInstructionName(){
		return myInstructionName;
	}
	
	public String getVariables(){
		return myVariables;
	}
	
	public String getUserCommands(){
		return myUserCommands;
	}	
}