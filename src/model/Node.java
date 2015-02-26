package model;

import java.util.ArrayList;
import java.util.List;

import model.instructions.Instruction;

public class Node{
		
		private List<Node> myChildren;
		private Instruction myInstruction;
		public Node(Instruction me){
			myInstruction = me;
			myChildren = new ArrayList<Node>();
		}
	public Node addChild(Node n){
		myChildren.add(n);
		return n;
	}
	public List<Node> getChildren(){
		return myChildren;
	}
	public Instruction getInstruction(){
		return myInstruction;
	}
	public void setInstruction(Instruction inInstruction){
		myInstruction = inInstruction;
	}
}
