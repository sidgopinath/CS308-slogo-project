package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import model.instructions.Instruction;

public class ExecutionEnvironment extends Observable{
	private Map<String,Instruction> userCommandMap;
	private Map<String, Instruction> variableMap;
	public ExecutionEnvironment(){
		clear();
	}
	public void addCommand(String commandName, Instruction inInstruction){
		if(userCommandMap.containsKey(commandName))
			userCommandMap.remove(commandName);
		userCommandMap.put(commandName, inInstruction);
		setChanged();
		notifyObservers();
		clearChanged();
	}
	public void addVariable(String variableName, Instruction value){
		if(variableMap.containsKey(variableName))
			variableMap.remove(variableName);
		variableMap.put(variableName, value);
		setChanged();
		notifyObservers();
		}
	public Instruction getVariable(String key){
		return variableMap.get(key);
	}
	public Instruction getCommand(String key){
		return userCommandMap.get(key);
	}
	public void clear(){
		userCommandMap=new HashMap();
		variableMap = new HashMap();
	}
	
	public Map<String, Instruction> getUserCommandMap(){
		return userCommandMap;
	}
	
	public Map<String, Instruction> getVariableMap(){
		return variableMap;
	}
	
	
}
