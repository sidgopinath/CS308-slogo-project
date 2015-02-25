package model.instructions;

import java.util.List;

import view.SLogoView;
import model.turtle.TurtleCommand;


public abstract class Instruction {
 
	protected List<Instruction> myDependencies;
	protected String myInstructionType;
	protected SLogoView myView;
	
	public Instruction(List<Instruction> dependencies, String instructionType, SLogoView view){
		myDependencies = dependencies;
		myInstructionType = instructionType;
		myView = view;
	}
	
	public Instruction(String constantInput){}
	
    public abstract double execute();
	public abstract int getNumberOfArguments();
	
	public TurtleCommand getTurtleCommand() {
		return null;
	}
}

