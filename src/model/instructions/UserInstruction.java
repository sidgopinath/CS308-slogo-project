package model.instructions;


public class UserInstruction extends Instruction {
	private int numArgs;
	public enum implementers {
		To;
	}
	public UserInstruction(String[] input) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public double execute() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfArguments() {
		// TODO Auto-generated method stub
		return numArgs;
	}

}
