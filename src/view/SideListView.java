package view;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

public abstract class SideListView extends SidePropertiesView{

	ListView myList;

	public SideListView(String s, ObservableList<Node> children) {
		super(s, children);
	}

	@Override
	protected void createView() {
		myList = new ListView<Property>();
		createColumns();
	}
	
	protected abstract void createColumns();
	
	protected abstract void update();
}
