package model.instructions;

import model.instructions.BooleanInstruction.implementers;

public class ControlInstruction extends Instruction{
	// if else is implemented as two commands: an if with the expression an if with the expression and the false statement, with the expression leading to the true statement
	String[] myInput;
	public ControlInstruction(String[] input) {
		super(input);
		myInput = input;
	}
	public enum implementers {
		MAKEVARIABLE(2),
		REPEAT(2),
		DOTIMES(2),
		FOR(2),
		IF(2),
		IFELSE(2);
		private int numArgs;
	implementers(int args){
    	this.numArgs=args;
    }
}

	@Override
	public double execute() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfArguments() {
		return implementers.valueOf(myInput[0].toUpperCase()).numArgs;
	}

}
