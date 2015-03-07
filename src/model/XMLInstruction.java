package model;

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