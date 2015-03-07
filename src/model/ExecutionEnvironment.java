package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import model.instructions.Instruction;

public class ExecutionEnvironment extends Observable{
	
	private Map<String,Instruction> myUserInstructionMap;
	private Map<String, Double> myVariableMap;
	private Set<Integer> myTurtleList;
	private Set<Integer> activeTurtleList;
	private int activeTurtle;
	public ExecutionEnvironment(){
		clear();
	}
	
	public void addCommand(String commandName, Instruction inInstruction){
		if(myUserInstructionMap.containsKey(commandName))
			myUserInstructionMap.remove(commandName);
		myUserInstructionMap.put(commandName, inInstruction);
		updateObserver();
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
	
	/*public void updateVariableName(String oldName, String newName){
		myVariableMap.put(newName, myVariableMap.get(oldName));
		myVariableMap.remove(oldName);
		updateObserver();
	}*/

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
		myTurtleList = new HashSet();
		myTurtleList.add(1);
		activeTurtleList = new HashSet();
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