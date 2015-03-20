package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import view.ViewUpdateModule;
import view.ViewUpdater;

/**
 * This class holds instructions that return boolean values
 * Hence, there is a boolToDouble function that allows us to cast booleans to Doubles
 * The functions here all return true or false in a double form
 * @author Greg, Sid
 *
 */

public class BooleanInstruction extends Instruction {

	public BooleanInstruction(List<Instruction> dependencies, String instructionType, ViewUpdater updater,ViewUpdateModule module, ExecutionEnvironment environment) {
		super(dependencies, instructionType, updater,module, environment);
	}

	public enum implementers {
			LESSTHAN (2),
			GREATERTHAN (2),
			EQUAL (2),
			NOTEQUAL (2),
			AND (2),
			OR (2),
			NOT (1);
	    private int numArgs;
		implementers(int args){
	    	this.numArgs=args;
	    }
	}
	
	@Override
	public double execute() {
		switch(myInstructionType.toUpperCase()){
		case "LESSTHAN":
			return boolToDouble(myDependencies.get(0).execute()<myDependencies.get(1).execute());
		case "GREATERTHAN":
			return boolToDouble(myDependencies.get(0).execute()>myDependencies.get(1).execute());
		case "EQUAL":
			return boolToDouble(myDependencies.get(0).execute()==myDependencies.get(1).execute());
		case "NOTEQUAL":
			return boolToDouble(myDependencies.get(0).execute()!=myDependencies.get(1).execute());
		case "AND":
			return boolToDouble(myDependencies.get(0).execute()!=0 && myDependencies.get(1).execute()!=0);
		case "OR":
			return boolToDouble(myDependencies.get(0).execute()!=0 || myDependencies.get(1).execute()!=0);
		case "NOT":
			return boolToDouble(myDependencies.get(0).execute()==0);
		default:
			return -1;
		}
	}
	
	private double boolToDouble(Boolean input){
		if(input){
			return 1;
		}
		return 0;
	}
	
	@Override
	public int getNumberOfArguments() {
		return implementers.valueOf(myInstructionType.toUpperCase()).numArgs;
	}
}