package model.instructions;

import java.util.List;


public class TurtleRequestInstruction extends Instruction {
	// TODO call view for these fields
	List<Instruction> myDependencies;
	String instructionType;
	
	public TurtleRequestInstruction(List<Instruction> dependencies, String inType) {
		myDependencies = dependencies;
		instructionType = inType;
		}

	@Override
	public double execute() {
		switch(instructionType.toUpperCase()){
		case "XCOR":
			return 0.0;
		case "YCOR":
			return 0.0;
		case "HEADING":
			return 0.0;
		case "PENDOWN":
			return 0.0;
		case "SHOWING":
			return 0.0;
		default: 
			return 0.0;
		}
	}

	@Override
	public int getNumberOfArguments() {
		return 0;
	}

}
