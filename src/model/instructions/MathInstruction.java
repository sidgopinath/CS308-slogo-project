package model.instructions;

import java.util.List;
import java.util.Random;

import view.SLogoView;

public class MathInstruction extends Instruction{
	

	public MathInstruction(List<Instruction> dependencies, String instructionType, SLogoView view) {
		super(dependencies, instructionType, view);
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
		double firstDependency = myDependencies.get(0).execute();
		double secondDependency = myDependencies.get(1).execute();
		switch(myInstructionType.toUpperCase()){
		case "SUM":
			return firstDependency+secondDependency;
		case "DIFFERENCE":
			return firstDependency-secondDependency;
		case "PRODUCT":
			return firstDependency*secondDependency;
		case "QUOTIENT":
			return divByZeroCheck(firstDependency/secondDependency);
		case "REMAINDER":
			return firstDependency%secondDependency;
		case "MINUS":
			return -firstDependency;
		case "RANDOM":
			Random randomNum = new Random();
			return randomNum.nextInt((int)firstDependency);
		case "SIN":
			return Math.toDegrees(Math.sin(firstDependency));
		case "COS":
			return Math.toDegrees(Math.cos(secondDependency));
		case "TAN":
			return Math.toDegrees(divByZeroCheck((Math.tan(secondDependency))));
		case "ATAN":
			return Math.toDegrees(divByZeroCheck((Math.atan(secondDependency))));
		case "LOG":
			return Math.log(firstDependency);
		case "POW":
			return Math.pow(firstDependency, secondDependency);
		case "PI":
			return Math.PI;
		default: 
			// view.displayException(new Exception());
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