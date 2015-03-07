package model.instructions;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.ExecutionEnvironment;
import model.TurtleCommand;
import view.ViewUpdater;

/**
 * This class is the superclass for all Instructions implemented in this program.  All instructions contains a dependency list, which is
 * the children nodes of this node in the expression tree.  The .execute method is called when a command is actually run, and produces the output of that instruction.
 * Every instruction is always required to maintain the number of its required arguments, so that the tree can properly be constructed.  The update method is used to keep
 * all copies of the execution environment consistent across the instructions, so variables ad user commands can be easily implemented.  Actual implementations contain an enum describing the instructions the class contains.
 * @author Greg McKeon
 **/
public abstract class Instruction implements Observer{
 
	protected List<Instruction> myDependencies;
	protected String myInstructionType;
	protected ViewUpdater myViewUpdater;
	protected ExecutionEnvironment myEnvironment;
	
	public Instruction(List<Instruction> dependencies, String instructionType, ViewUpdater updater, ExecutionEnvironment environment){
		myDependencies = dependencies;
		myInstructionType = instructionType;
		myViewUpdater = updater;
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