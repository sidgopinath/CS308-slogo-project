package model;

import java.util.List;

import model.instructions.Instruction;

public class Executer {
	
	public List<String> variables;
	
	public Executer(List<Node> executables){
		for(Node root:executables){
			System.out.println("new tree");
			inOrderTraversal(root);
		}
		
	}
	public void makeCommandStack(List<Node> nodeList){
		
	}
	private static void inOrderTraversal(Node root){
		if(root==null)
			return;
		if(root.getLeft()!=null){
			inOrderTraversal(root.getLeft());
		}
		System.out.println(root.getValue());
		if(root.getRight()!=null){
			inOrderTraversal(root.getRight());
		}
	}
}
