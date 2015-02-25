package model.instructions;

import java.util.List;

import model.turtle.TurtleCommand;


public abstract class Instruction {
 
    public abstract double execute();
	public abstract int getNumberOfArguments();
	public TurtleCommand getTurtleCommand() {
		return null;
	}
}

