package model.instructions;

import java.util.List;

import com.sun.tracing.dtrace.DependencyClass;

import model.ExecutionEnvironment;
import view.SLogoView;

public class ControlInstruction extends Instruction{

	public ControlInstruction(List<Instruction> dependencies, String instructionType, SLogoView view, ExecutionEnvironment environment) {
		super(dependencies, instructionType, view, environment);
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
	public double execute() {
		double returnVal=0;
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
			int endRange = (int) Math.round(myDependencies.get(0).myDependencies.get(0).execute());
			for(int i =1; i<=endRange; i++){
				makeVariable(new Variable(myDependencies.get(0).myDependencies.get(0).getName(), myEnvironment), new Constant(Integer.toString(i), myEnvironment));
				for(Instruction instruct:myDependencies.get(1).myDependencies){
					System.out.println(":var is "+myEnvironment.getVariable(":var").execute());
					returnVal = instruct.execute();
				}
			}
			return returnVal;
		case "FOR":
			//similar to DoTimes
			//for loop from "start" to "end"
			//goes up by increment
			//all values are in a list which makes it harder
			//variable assigned to current "i" every time
			//command(s) run on that variable
			//return value of last expression
			return 0.0;
		case "IF":
			if(myDependencies.get(0).execute() != 0){
				return myDependencies.get(1).execute(); 
			}
			return 0.0;
		case "IFELSE":
			if(myDependencies.get(0).execute() != 0){
				return myDependencies.get(1).execute();
			}
			else{
				return myDependencies.get(2).execute();
			}
		case "MAKEUSERINSTRUCTION":
			//needs user commands map AND variables map
			//saves commandName as a key in userCommandsMap
			//not positive, but think each variable in list correspods to one command
			//run each command with its corresponding variable
			//store that entire string of instructions in the user command map
			//variables are all stored in the variablesMap
			//returns 1 if it works, otherwise 0
			return 0.0;
		default:
			return -1;
		}
	}
	public double makeVariable(Instruction input, Instruction value){
		myEnvironment.removeDuplicate(input.getName());
		myEnvironment.addVariable(input.getName(), value);
		return value.execute();
	}
	@Override
	public int getNumberOfArguments() {
		return implementers.valueOf(myInstructionType.toUpperCase()).numArgs;
	}
}