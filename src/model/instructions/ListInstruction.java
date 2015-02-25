package model.instructions;

import java.util.List;



public class ListInstruction extends Instruction {
	
	List<Instruction> myDependencies;
	public ListInstruction(List<Instruction> input, String myInstructionType) {
		myDependencies = input;
	}
	
//	public List<Instruction> getInstructionList() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
//		return Parser.parseAndExecute(list);
//	}
	
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
