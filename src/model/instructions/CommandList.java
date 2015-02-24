package model.instructions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import model.Node;
import model.Parser;

public class CommandList extends Instruction {
	String list;
	public CommandList(String[] input) {
		super(input);
		list = input[1];
	}
	public List<Node> getInstructionList() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		return Parser.parse(list);
	}
	@Override
	public double execute() {
		//for loop, execute every node in the instruction set. return the value of the last one.
		return 0;
	}

	@Override
	public int getNumberOfArguments(String match) {
		// TODO Auto-generated method stub
		return 0;
	}

}
