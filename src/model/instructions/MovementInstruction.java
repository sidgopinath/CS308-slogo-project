package model.instructions;


public class MovementInstruction extends Instruction {

	public MovementInstruction(String[] input) {
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
	public int getNumberOfArguments() {
		// TODO Auto-generated method stub
		return 1;
	}
}
