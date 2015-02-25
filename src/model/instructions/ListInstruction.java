package model.instructions;

import java.util.List;

public class ListInstruction extends Instruction {
	
	public ListInstruction(List<Instruction> dependencies, String instructionType) {
		super(dependencies, instructionType);
	}

	@Override
	public double execute() {
		//for loop, execute every node in the instruction set. return the value of the last one.
		for(Instruction i:myDependencies){
			i.execute();
		}
		return 0;
	}

	@Override
	public int getNumberOfArguments() {
		return 1;
	}
}
