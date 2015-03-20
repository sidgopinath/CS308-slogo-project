package model;

import java.util.ArrayList;
import java.util.List;

import model.instructions.Instruction;

/**
 * This node class holds a node of the tree created in Parser
 * A node holds an actual instruction as well as a list of all of its child nodes
 * @author Greg
 *
 */

public class Node{
		
	private List<Node> myChildren;
	private Instruction myInstruction;
	
	public Node(Instruction instruction){
		myInstruction = instruction;
		myChildren = new ArrayList<Node>();
	}
	
	public void addChild(Node n){
		myChildren.add(n);
	}
	
	public List<Node> getChildren(){
		return myChildren;
	}
	
	public Instruction getInstruction(){
		return myInstruction;
	}
	
}