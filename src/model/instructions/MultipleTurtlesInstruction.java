package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import model.Polar;
import model.instructions.BooleanInstruction.implementers;
import view.SLogoView;

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
			String instructionType, SLogoView view,
			ExecutionEnvironment environment) {
		super(dependencies, instructionType, view, environment);
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