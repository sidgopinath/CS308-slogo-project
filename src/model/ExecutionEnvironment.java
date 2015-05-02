package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import model.instructions.Instruction;

/**
 * Execution environment holds user instructions, variables, and a list of turtles
 * This environment is observable and is accessed by the Parser and View
 * By making it observable, there are fewer method calls and public variables
 * Whenever any of the variables is updated, the view will know about it
 * @author Greg
 *
 */

public class ExecutionEnvironment extends Observable{
	
	private Map<String,Instruction> myUserInstructionMap;
	private Map<String, Double> myVariableMap;
	private Set<Integer> myTurtleList;
	private Set<Integer> activeTurtleList;
	private int activeTurtle;
	private int myWindowBehavior;
	
	public ExecutionEnvironment(){
		clear();
	}
	
	public void addCommand(String commandName, Instruction inInstruction){
		if(myUserInstructionMap.containsKey(commandName))
			myUserInstructionMap.remove(commandName);
		myUserInstructionMap.put(commandName, inInstruction);
		updateObserver();
	}
	
	public double setWindowBehavior(double d){
	    myWindowBehavior = (int) d;
	    updateObserver();
	    return myWindowBehavior;
	}
	public int getWindowBehavior(){
	    return myWindowBehavior;
	}
	public void addTurtle(double d){
		myTurtleList.add((int) d);
		updateObserver();
	}
	
	public Set<Integer> getTurtles(){
		return myTurtleList;
	}
	
	public void addVariable(String variableName, Double value){
		if(myVariableMap.containsKey(variableName))
			myVariableMap.remove(variableName);
		myVariableMap.put(variableName, value);
		updateObserver();
	}

	private void updateObserver() {
		setChanged();
		notifyObservers();
		clearChanged();
	}

	public Double getVariable(String key){
		return myVariableMap.get(key);
	}
	
	public void removeDuplicate(String s){
		if(myVariableMap.containsKey(s))
			myVariableMap.remove(s);
	}
		
	public Instruction getCommand(String key){
		return myUserInstructionMap.get(key);
	}
	
	public void clear(){
		myUserInstructionMap = new HashMap<String, Instruction>();
		myVariableMap = new HashMap<String, Double>();
		myTurtleList = new HashSet<Integer>();
		myTurtleList.add(1);
		activeTurtleList = new HashSet<Integer>();
		activeTurtleList.add(1);
		activeTurtle = 1;
	}
	
	public int getActiveTurtle(){
		return activeTurtle;
	}
	
	public void setActiveTurtle(double turtle){
		activeTurtle = (int) turtle;
		updateObserver();
	}
	
	public Map<String, Instruction> getUserCommandMap(){
		return myUserInstructionMap;
	}
	
	public Map<String, Double> getVariableMap(){
		return myVariableMap;
	}

	public Set<Integer> getActiveList(){
		return activeTurtleList;
	}
	
	public void clearActiveList(){
		activeTurtleList.clear();
		updateObserver();
	}
	
	public void addTurtleToActiveList(double turtle) {
		activeTurtleList.add((int)turtle);
		updateObserver();
	}
}