package model.instructions;


public abstract class Instruction {
    
    public Instruction(String[] input){
    }
    
    public abstract double execute();

	public abstract int getNumberOfArguments();
}

