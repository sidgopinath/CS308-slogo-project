package model.instructions;


public class Forward extends Instruction {

	public Forward(String[] input) {
		super(input);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double execute() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String toString(){
		return this.getClass().toString();
	}

	@Override
	public int getNumberOfArguments(String match) {
		// TODO Auto-generated method stub
		return 1;
	}
}
