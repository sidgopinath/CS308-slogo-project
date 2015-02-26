package model.instructions;

import java.util.ArrayList;
import java.util.List;

import view.SLogoView;
import model.ExecutionEnvironment;
import model.Polar;
import model.turtle.TurtleCommand;


public class MovementInstruction extends Instruction {
	private TurtleCommand myTurtle;
	private boolean myJump;
	private Polar myPolar;
	
	public enum implementers {
		FORWARD(1),
		BACKWARD(1),
		LEFT(1),
		RIGHT(1),
		SETHEADING(1),
		TOWARDS(2),
		SETXY(2),
		PENDOWN(0),
		PENUP(0),
		SHOWTURTLE(0),
		HIDETURTLE(0),
		NATURALLOG(1),
		CLEARSCREEN(2),
		HOME(0);
		private int numArgs;
	implementers(int args){
    	this.numArgs=args;
    }
	}
	
	public MovementInstruction(List<Instruction> dependencies, String instructionType, SLogoView view, ExecutionEnvironment environment) {
		super(dependencies, instructionType, view, environment);
		myDependencies = dependencies;
		myInstructionType = instructionType;
		myTurtle = null;
		myJump = false;
	}
	
	double returnVal;
	@Override
	public double execute() {
		switch(myInstructionType.toUpperCase()){
		case "FORWARD":
			returnVal = myDependencies.get(0).execute();
			myPolar = new Polar(0, returnVal);
			//updateView();
			return returnVal;
		case "BACKWARD":
			returnVal = myDependencies.get(0).execute();
			myPolar = new Polar(0, -returnVal);
			updateView();
			return myDependencies.get(0).execute();
		case "LEFT":
			returnVal = myDependencies.get(0).execute();
			myPolar = new Polar(-returnVal, 0);
			updateView();
			return myDependencies.get(0).execute();
		case "RIGHT":
			returnVal = myDependencies.get(0).execute();
			myPolar = new Polar(returnVal, 0);
			updateView();
			return returnVal;
		case "SETHEADING":
			// Need view
			myPolar = new Polar(myDependencies.get(0).execute(), 0);
			return 0.0;
		case "TOWARDS":
			// need view
			return 0.0;
		case "SETXY":
			//need view
			myJump = true;
			myPolar = new Polar(myDependencies.get(0).execute(), myDependencies.get(1).execute());
			return 0.0;
		case "PENDOWN":
			myView.setPenUp(0, false);
			return 1.0;
		case "PENUP":
			myView.setPenUp(0, true);
			return 0.0;
		case "SHOWTURTLE":
			myView.showTurtle(0, true);
			return 1.0;
		case "HIDETURTLE":
			myView.showTurtle(0, false);
			return 0.0;
		case "CLEARSCREEN":
			//need view
			return 0.0;
		case "HOME":
			//need view
			return 0.0;
		default: 
			return 0;
		}
	}

	private void updateView() {
		List<TurtleCommand> commandList = new ArrayList<TurtleCommand>();
		commandList.add(new TurtleCommand(0, myPolar, myJump));
		//myView.updateWorkspace(commandList);
	}

	@Override
	public int getNumberOfArguments() {
		return implementers.valueOf(myInstructionType.toUpperCase()).numArgs;
	}

	@Override
	public TurtleCommand getTurtleCommand() {
		myTurtle = new TurtleCommand(0, myPolar, myJump);
		return myTurtle;
	}
	
	//somewhere add something to setpenup in the view
}