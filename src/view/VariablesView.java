// This entire file is part of my masterpiece.
// CALLIE MAO

package view;

import model.ExecutionEnvironment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

public class VariablesView extends SideTableView{

	ExecutionEnvironment myEnvironment;
	private ObservableList<Property> myVariablesList;

	
	public VariablesView(String s, ObservableList<Node> children, String leftCol,
			String rightCol, String leftVal, String rightVal, ExecutionEnvironment env) {
		super(s, children, leftCol, rightCol, leftVal, rightVal);
		myEnvironment = env;
		myVariablesList = FXCollections.observableArrayList();
		getTable().setEditable(true);
	}
	
	@Override
	protected void createColumns() {
		super.createColumns();
		myVariablesList = FXCollections.observableArrayList();
		getLeftTableCol().setEditable(true);
		getRightTableCol().setOnEditCommit(new EventHandler<CellEditEvent<Property, String>>() {
		@Override
		public void handle(CellEditEvent<Property, String> t) {
			Property variable = t.getTableView().getItems()
					.get(t.getTablePosition().getRow());
			variable.setValue(Double.parseDouble(t.getNewValue()));
			myEnvironment.addVariable(variable.getName(), Double.parseDouble(t.getNewValue()));
		}
	});
		getTable().getColumns().addAll(getLeftTableCol(), getRightTableCol());
	}

	protected void update(Property variable) {
		for (Property o : getTable().getItems()) {
			if (o.getName().equals(variable.getName())) {
				o.setValue(variable.getValue());
				return;
			}
		}
		myVariablesList.add(variable);
	}
	

}
