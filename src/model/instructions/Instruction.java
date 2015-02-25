package model.instructions;

import java.util.List;

import model.turtle.TurtleCommand;


public abstract class Instruction {
 
	protected List<Instruction> myDependencies;
	protected String myInstructionType;
	
	public Instruction(List<Instruction> dependencies, String instructionType){
		myDependencies = dependencies;
		myInstructionType = instructionType;
	}
	
	public Instruction(String constantInput){}
	
    public abstract double execute();
	public abstract int getNumberOfArguments();
	
	public TurtleCommand getTurtleCommand() {
		return null;
	}
}

