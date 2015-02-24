package model;

import model.instructions.Instruction;

/// TODO: make this abstract and create a commandnode and datanode
public class Node{
		
		private Node myLeft;
		private Node myRight;
		private String myValue;
		
		public Node(String me){
			myValue = me;
		}
		public String getValue(){
			return myValue;
		}
	public void addChildRight(Node n){
		myRight = n;
	}
	public void addChildLeft(Node n){
		myLeft= n;
	}
	public Node getRight(){
		return myRight;
	}
	public Node getLeft(){
		return myLeft;
	}
}
