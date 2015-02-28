package model.instructions;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.ExecutionEnvironment;
import model.turtle.TurtleCommand;
import view.SLogoView;

public abstract class Instruction implements Observer{
 
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
	
	public Instruction(String constantInput, ExecutionEnvironment environment){
		myInstructionType=constantInput;
		myEnvironment = environment;
	}
	
    public abstract double execute();
	public abstract int getNumberOfArguments();
	
	public TurtleCommand getTurtleCommand() {
		return null;
	}
	
	public String getName(){
		return this.myInstructionType;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		myEnvironment = (ExecutionEnvironment) o;
	}
}