package model.instructions;

import java.util.List;

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
		switch(myInstructionType.toUpperCase()){
		case "MAKEVARIABLE":
			myEnvironment.removeDuplicate(myDependencies.get(0).getName());
			myEnvironment.addVariable(myDependencies.get(0).getName(), myDependencies.get(1));
			return myDependencies.get(0).execute();
		case "REPEAT":
			for(int i =0; i<myDependencies.get(0).execute(); i++){
				myDependencies.get(1).execute();
			}
			return myDependencies.get(1).execute();
		case "DOTIMES":
			//for loop from 1 to limit
			//the limit is within a list though
			//the list is [ variable limit ]
			//so run the for loop till then
			//then for every command, run it using the current "i"
			//need variable map to store i so command(s) can be called with it
			//return value of last expression
			return 0.0;
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

	@Override
	public int getNumberOfArguments() {
		return implementers.valueOf(myInstructionType.toUpperCase()).numArgs;
	}
}