package model.instructions;

import java.util.List;

import view.SLogoView;
import model.ExecutionEnvironment;
import model.turtle.TurtleCommand;


public abstract class Instruction {
 
	protected List<Instruction> myDependencies;
	protected String myInstructionType;
	protected SLogoView myView;
	protected ExecutionEnvironment myEnvironment;
	public Instruction(List<Instruction> dependencies, String instructionType, SLogoView view, ExecutionEnvironment environment){
		myDependencies = dependencies;
		myInstructionType = instructionType;
		myView = view;
		myEnvironment=environment;
	}
	
	public Instruction(String constantInput){}
	
    public abstract double execute();
	public abstract int getNumberOfArguments();
	
	public TurtleCommand getTurtleCommand() {
		return null;
	}
}

