package model.instructions;

import java.util.List;

import model.ExecutionEnvironment;
import view.SLogoView;

public class FrontEndCommands extends Instruction{
	public FrontEndCommands(List<Instruction> dependencies,
			String instructionType, SLogoView view,
			ExecutionEnvironment environment) {
		super(dependencies, instructionType, view, environment);
		// TODO Auto-generated constructor stub
	}

	public enum implementers {
		SetBackground(1),
		SetPenColor(1),
		SetPenSize(1),
		SetShape(1),
		SetPalette(4),
		GetPenColor(0),
		GetShape(0),
		Stamp(0),
		ClearStamps(0);
		
    private int numArgs;
	implementers(int args){
    	this.numArgs=args;
	}
}

	@Override
	public double execute() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfArguments() {
		// TODO Auto-generated method stub
		return 0;
	}
}
