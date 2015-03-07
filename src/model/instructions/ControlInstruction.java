package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import view.ViewUpdater;

/**
 * Control Instructions deal with program flow or variable creation.  Since many of these commands use the same loop, a loop is available at the bottom.
 * This class plays a small role in recursion.
 * @author Greg, Sid
 *
 */

public class ControlInstruction extends Instruction{

	public ControlInstruction(List<Instruction> dependencies, String instructionType, ViewUpdater updater, ExecutionEnvironment environment) {
		super(dependencies, instructionType, updater, environment);
	}

	public enum implementers {
		MAKEVARIABLE(2),
		REPEAT(2),
		DOTIMES(2),
		FOR(2),
		IF(2),
		IFELSE(3),
		MAKEUSERINSTRUCTION(3);
		private int numArgs;
	implementers(int args){
    	this.numArgs=args;
    }
	}

	@Override
	public double execute(){
		double returnVal=0;
		int endRange = 0;
		int startRange = 0;
		int increment = 0;
		switch(myInstructionType.toUpperCase()){
		case "MAKEVARIABLE":
			return makeVariable(myDependencies.get(0), myDependencies.get(1));
		case "REPEAT":
			for(int i =1; i<=myDependencies.get(0).execute(); i++){
				makeVariable(new Variable(":repcount", myEnvironment), new Constant(Integer.toString(i), myEnvironment));
				for(Instruction instruct:myDependencies.get(1).myDependencies){
					returnVal = instruct.execute();
				}
			}
			return returnVal;
		case "DOTIMES":
			endRange = (int) Math.round(myDependencies.get(0).myDependencies.get(0).execute());
			return forLoop(myDependencies.get(0), myDependencies.get(1), 1, endRange, 1);
		case "FOR":
			startRange = (int) Math.round(myDependencies.get(0).myDependencies.get(1).execute());
			endRange = (int) Math.round(myDependencies.get(0).myDependencies.get(2).execute());
			increment = (int) Math.round(myDependencies.get(0).myDependencies.get(3).execute());
			return forLoop(myDependencies.get(0), myDependencies.get(1), startRange, endRange, increment);
		case "IF":
			if(myDependencies.get(0).execute() != 0){
				executeList(myDependencies.get(1));
			}
			return returnVal;
		case "IFELSE":
			if(myDependencies.get(0).execute() != 0){
				return executeList(myDependencies.get(1));
			}
			return executeList(myDependencies.get(2));
		case "MAKEUSERINSTRUCTION":
			return addToMap();
		default:
			return 0.0;
		}
	}
	public double addToMap(){
		String commandName = myDependencies.get(0).getName();
		myEnvironment.addCommand(commandName, this);
		return 1;
	}
	public double forLoop(Instruction varHead, Instruction listHead, int startIndex, int endIndex, int increment){
		double returnVal = 0;
		for(int i =startIndex; i<=endIndex; i+=increment){
			makeVariable(new Variable(varHead.myDependencies.get(0).getName(), myEnvironment), new Constant(Integer.toString(i), myEnvironment));
			for(Instruction instruct:listHead.myDependencies){
				returnVal = instruct.execute();
			}
		}
		return returnVal;
	}
	
	public double executeList(Instruction listHead){
		double returnVal = 0;
		for(Instruction myInt : listHead.myDependencies){
			returnVal =  myInt.execute();
		}
		return returnVal;
	}
	
	public double makeVariable(Instruction input, Instruction value){
		myEnvironment.addVariable(input.getName(), value.execute());
		return value.execute();
	}
	
	@Override
	public int getNumberOfArguments() {
		return implementers.valueOf(myInstructionType.toUpperCase()).numArgs;
	}
}