package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import model.Polar;
import model.instructions.BooleanInstruction.implementers;
import view.SLogoView;
import view.ViewUpdater;

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
		switch(myInstructionType.toUpperCase()){
		case "ID":
			return myViewUpdater.getCurrentTurtleID();
		case "TURTLES":
			return myViewUpdater.getNumTurtles();
		case "TELL":
			return myDependencies.get(0).execute();
		case "ASK":
			return myDependencies.get(0).execute();
		case "ASKWITH":
			return myDependencies.get(0).execute();
	}
	}

	@Override
	public int getNumberOfArguments() {
		return implementers.valueOf(myInstructionType.toUpperCase()).numArgs;
	}
}