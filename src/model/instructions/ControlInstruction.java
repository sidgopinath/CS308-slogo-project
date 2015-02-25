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
			return 0;
		case "REPEAT":
			for(int i =0; i<myDependencies.get(0).execute(); i++){
				myDependencies.get(1).execute();
			}
			return myDependencies.get(0).execute();
		case "DOTIMES":
			return 0.0;
		case "FOR":
			return 0.0;
		case "IF":
			return 0.0;
		case "IFELSE":
			return 0.0;
		case "NOT":
			return 0.0;
		case "TO":
			return 0.0;
		default:
			//TODO: need Error
			return -1;
		}
	}

	@Override
	public int getNumberOfArguments() {
		return implementers.valueOf(myInstructionType.toUpperCase()).numArgs;
	}
}