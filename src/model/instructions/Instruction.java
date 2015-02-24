package model.instructions;


public abstract class Instruction {
    
    public Instruction(String[] input){
    	System.out.println(input[0]);
    }
    
    public abstract double execute();

	public abstract int getNumberOfArguments();
}

