package model.instructions;


public class Variable extends Instruction{
	public Variable(String myName) {
		super(myName);
	}

	@Override
	public double execute() {
		return myEnvironment.getVariable(myInstructionType).execute();
	}
	@Override
	public int getNumberOfArguments() {
		return 0;
	}
	

}
