// This entire file is part of my masterpiece.
// Sid Gopinath

package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import model.instructions.Instruction;

/**
 * Execution environment holds user instructions, variables, and a list of
 * turtles This environment is observable and is accessed by the Parser and View
 * By making it observable, there are fewer method calls and public variables
 * Whenever any of the variables is updated, the view will know about it
 * 
 * @author Greg
 *
 */

public class ExecutionEnvironment extends Observable {

	public Map<String, Instruction> myUserInstructionMap;
	public Map<String, Double> myVariableMap;
	public Set<Integer> myTurtleList;
	public Set<Integer> myActiveTurtleList;
	public int myActiveTurtle;

	public ExecutionEnvironment() {
		clear();
	}

	public void updateObserver() {
		setChanged();
		notifyObservers();
		clearChanged();
	}

	private void clear() {
		myUserInstructionMap = new HashMap<String, Instruction>();
		myVariableMap = new HashMap<String, Double>();
		myTurtleList = new HashSet<Integer>();
		myTurtleList.add(1);
		myActiveTurtleList = new HashSet<Integer>();
		myActiveTurtleList.add(1);
		myActiveTurtle = 1;
	}
}