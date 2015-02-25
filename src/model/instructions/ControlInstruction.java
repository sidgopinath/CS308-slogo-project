package model.instructions;

import java.util.List;


public class ControlInstruction extends Instruction{
	// if else is implemented as two commands: an if with the expression an if with the expression and the false statement, with the expression leading to the true statement
	List<Instruction> myDependencies;
	String myType;
	public ControlInstruction(List<Instruction> input, String myInstructionType) {
		myDependencies = input;
		myType = myInstructionType;
	}
	public enum implementers {
		MAKEVARIABLE(2),
		REPEAT(2),
		DOTIMES(2),
		FOR(2),
		IF(2),
		IFELSE(3);
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
		return implementers.valueOf(myType.toUpperCase()).numArgs;
	}

}
