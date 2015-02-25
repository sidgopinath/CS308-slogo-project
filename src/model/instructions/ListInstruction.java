package model.instructions;

import java.util.List;

import view.SLogoView;

public class ListInstruction extends Instruction {
	

	public ListInstruction(List<Instruction> dependencies, String instructionType, SLogoView view) {
		super(dependencies, instructionType, view);
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
