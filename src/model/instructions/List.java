package model.instructions;

import java.util.ArrayList;

public class List extends Instruction {
	ArrayList<String> list;
	public List(String[] input) {
		super(input);
		list = new ArrayList<String>();
		for(int i =1; i<input.length;i++){
			list.add(input[i]);
		}
	}

	private int getLength(){
		return list.size();
	}
	@Override
	public double execute() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfArguments(String match) {
		// TODO Auto-generated method stub
		return 0;
	}

}
