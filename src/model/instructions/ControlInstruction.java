package model.instructions;

import java.util.List;

import view.SLogoView;

public class ControlInstruction extends Instruction{

	public ControlInstruction(List<Instruction> dependencies, String instructionType, SLogoView view) {
		super(dependencies, instructionType, view);
	}

	public enum implementers {
		MAKEVARIABLE(2),
		REPEAT(2),
		DOTIMES(2),
		FOR(2),
		IF(2),
		IFELSE(3),
		TO(3);
		private int numArgs;
	implementers(int args){
    	this.numArgs=args;
    }
}

	@Override
	public double execute() {
		switch(myInstructionType.toUpperCase()){
		case "MAKEVARIABLE":
			//need variable map
			return 0;
		case "REPEAT":
			for(int i =0; i<myDependencies.get(0).execute(); i++){
				myDependencies.get(1).execute();
			}
			return myDependencies.get(0).execute();
		case "DOTIMES":
			//need variable map
			return 0.0;
		case "FOR":
			//need variable map
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
		case "TO":
			//need variable map/user commands
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