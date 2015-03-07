package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import view.ViewUpdater;

/**
 * Class which implements instructions that handle multiple turtles.  AskWith's implementations is incomplete.  These commands tell the parser which
 * turtles to execute a set of instructions on, and update the active turtles lists held in the executionenvironment object.
 * Use the dependencies list to create loops and update which turtles are active
 * @author Greg
 *
 */

public class MultipleTurtlesInstruction extends Instruction{
	
	public enum implementers {
		ID(0),
		TURTLES(0),
		TELL(1),
		ASK(2),
		ASKWITH(2);
		
    private int numArgs;
	implementers(int args){
    	this.numArgs=args;
	}
}
	public MultipleTurtlesInstruction(List<Instruction> dependencies,
			String instructionType, ViewUpdater updater,
			ExecutionEnvironment environment) {
		super(dependencies, instructionType, updater, environment);
	}

	@Override
	public double execute() {
		double returnVal = 0; 
		switch(myInstructionType.toUpperCase()){
		case "ID":
			return myEnvironment.getActiveTurtle();
		case "TURTLES":
			return myEnvironment.getTurtles().size();
		case "TELL":
			myEnvironment.clearActiveList();
			for(Instruction i :myDependencies.get(0).myDependencies){
				myEnvironment.addTurtle(i.execute());
				myEnvironment.addTurtleToActiveList(i.execute());
				returnVal = i.execute();
			}
			return returnVal;
		case "ASK":
			for(Instruction i :myDependencies.get(0).myDependencies){
				myEnvironment.addTurtle(i.execute());
				myEnvironment.setActiveTurtle(i.execute());
				for(Instruction j : myDependencies.get(1).myDependencies){
					returnVal = j.execute();
				}
			}
			return returnVal;
		case "ASKWITH":
			// Not implemented
			return myDependencies.get(0).execute();
		default:
			return 0;
		}
	}

	@Override
	public int getNumberOfArguments() {
		return implementers.valueOf(myInstructionType.toUpperCase()).numArgs;
	}
}