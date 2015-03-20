package model.instructions;

import java.util.List;
import java.util.Random;

import model.ExecutionEnvironment;
import view.ViewUpdater;

/**
 * All math instructions are stored here
 * Math instructions consist entirely of constants as dependancies.
 * A combination of math in this class and constants/variables being executed makes this work
 * @author Greg, Sid
 *
 */

public class MathInstruction extends Instruction{
	
	public MathInstruction(List<Instruction> dependencies, String instructionType, ViewUpdater updater, ExecutionEnvironment environment) {
		super(dependencies, instructionType, updater, environment);
	}

	public enum implementers {
		SUM(2),
		DIFFERENCE (2),
		PRODUCT(2),
		QUOTIENT(2),
		REMAINDER(2),
		MINUS(1),
		RANDOM(1),
		SINE(1),
		COSINE(1),
		TANGENT(1),
		ARCTANGENT(1),
		NATURALLOG(1),
		POWER(2),
		PI(0);
    private int numArgs;
	implementers(int args){
    	this.numArgs=args;
	}
}

	@Override
	public double execute() {
		switch(myInstructionType.toUpperCase()){
		case "SUM":
			return myDependencies.get(0).execute()+myDependencies.get(1).execute();
		case "DIFFERENCE":
			return myDependencies.get(0).execute()-myDependencies.get(1).execute();
		case "PRODUCT":
			return myDependencies.get(0).execute()*myDependencies.get(1).execute();
		case "QUOTIENT":
			return divByZeroCheck(myDependencies.get(0).execute()/myDependencies.get(1).execute());
		case "REMAINDER":
			return myDependencies.get(0).execute()%myDependencies.get(1).execute();
		case "MINUS":
			return -myDependencies.get(0).execute();
		case "RANDOM":
			Random randomNum = new Random();
			return randomNum.nextInt((int)myDependencies.get(0).execute());
		case "SINE":
			return Math.toDegrees(Math.sin(myDependencies.get(0).execute()));
		case "COSINE":
			return Math.toDegrees(Math.cos(myDependencies.get(0).execute()));
		case "TANGENT":
			return Math.toDegrees(divByZeroCheck((Math.tan(myDependencies.get(0).execute()))));
		case "ARCTANGENT":
			return Math.toDegrees(divByZeroCheck((Math.atan(myDependencies.get(0).execute()))));
		case "NATURALLOG":
			return Math.log(myDependencies.get(0).execute());
		case "POWER":
			return Math.pow(myDependencies.get(0).execute(), myDependencies.get(1).execute());
		case "PI":
			return Math.PI;
		default: 
			return 0;
		}
	}

	private double divByZeroCheck(double zeroCheck) {
		if(zeroCheck==Double.POSITIVE_INFINITY||zeroCheck==Double.NEGATIVE_INFINITY||zeroCheck==Double.NaN){
			return 0.0;
		}
		return zeroCheck;
	}

	@Override
	public int getNumberOfArguments() {
		return implementers.valueOf(myInstructionType.toUpperCase()).numArgs;
	}
}