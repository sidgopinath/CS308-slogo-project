package model.instructions;


public abstract class Instruction {
    
    public Instruction(String[] input){
    }
    
    protected abstract double execute();

	public abstract int getNumberOfArguments();
}

