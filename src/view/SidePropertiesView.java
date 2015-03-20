// This entire file is part of my masterpiece.
// CALLIE MAO

package view;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public abstract class SidePropertiesView {
	
	private ObservableList<Node> myChildren;
	
	public SidePropertiesView(String s, ObservableList<Node> children){
		myChildren = children;
		createTitleText(s);
		createView();
	}
	
	private void createTitleText(String s) {
		Text title = new Text(s);
		title.setFont(new Font(13));
		title.setUnderline(true);
		myChildren.add(title);
	}
	
	protected ObservableList<Node> getChildren(){
		return myChildren;
	}
	
	protected abstract void createView();
}
