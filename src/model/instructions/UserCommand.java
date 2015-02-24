package model.instructions;


public class UserCommand extends Instruction {
	private int numArgs;
	public UserCommand(String[] input) {
		super(input);
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
