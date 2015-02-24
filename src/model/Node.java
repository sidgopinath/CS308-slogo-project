package model;

import model.instructions.Instruction;

/// TODO: make this abstract and create a commandnode and datanode
class Node{
		private Node myLeft;
		private Node myRight;
		Instruction myInstruction;
		String myValue;
		public Node(Instruction i, String me){
			myInstruction = i;
			myValue = me;
		}
		public void updateNode(String newValue){
			myValue = newValue;
		}
		public Instruction getInstruction(){
			return myInstruction;
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
