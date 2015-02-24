package model.instructions;


public class Forward extends Instruction {

	public Forward(String[] input) {
		super(input);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected double execute() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfArguments() {
		// TODO Auto-generated method stub
		return 1;
	}
	public String toString(){
		return this.getClass().toString();
	}
}
