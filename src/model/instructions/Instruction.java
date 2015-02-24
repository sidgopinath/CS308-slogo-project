package model.instructions;

import model.Polar;

public abstract class Instruction {
    
    public Instruction(String[] input){
    }
    
    public abstract void execute();

	public abstract int getNumberOfArguments();
}

