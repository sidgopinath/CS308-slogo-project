package model.instructions;



public class ListInstruction extends Instruction {
	
	String list;
	public ListInstruction(String[] input) {
		System.out.println("Constructed! "+ input[0]);
		list = input[0];
	}
	
//	public List<Instruction> getInstructionList() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
//		return Parser.parseAndExecute(list);
//	}
	
	@Override
	public double execute() {
		//for loop, execute every node in the instruction set. return the value of the last one.
		return 0;
	}

	@Override
	public int getNumberOfArguments() {
		return 1;
	}



}
