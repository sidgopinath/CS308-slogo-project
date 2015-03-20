// This entire file is part of my masterpiece.
// CALLIE MAO

package view;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public abstract class SideTableView extends SidePropertiesView{
		
	
	private static final int COLUMN_WIDTH = 165;
	
	private TableView<Property> myTable;
	
	private String myLeftCol;
	private String myRightCol;
	private String myLeftVal;
	private String myRightVal;
	
	TableColumn<Property, String> leftTableCol;
	TableColumn<Property, String> rightTableCol;

	public SideTableView(String s, ObservableList<Node> children, String leftCol, String rightCol, String leftVal, String rightVal) {
		super(s, children);
		myRightCol = rightCol;
		myLeftCol = leftCol;
		myLeftVal = leftVal;
		myRightVal = rightVal;
	}

	@Override
	protected void createView() {
		myTable = new TableView<Property>();
		createColumns();
		myTable.setMaxWidth(Double.MAX_VALUE);
		myTable.setPrefHeight(130);
		getChildren().add(myTable);
	}
	
	protected void createColumns() {
		leftTableCol = new TableColumn<Property, String>(
				myLeftCol);
		rightTableCol = new TableColumn<Property, String>(
				myRightCol);
		leftTableCol.setCellValueFactory(new PropertyValueFactory<Property, String>(
				myLeftVal));
		rightTableCol.setCellValueFactory(new PropertyValueFactory<Property, String>(
				myRightVal));
		leftTableCol.setPrefWidth(COLUMN_WIDTH); // TODO: set dynamically
		rightTableCol.setPrefWidth(COLUMN_WIDTH); // divide width by 7.3 or 7.4
		myTable.getColumns().addAll(leftTableCol, rightTableCol);
	}
	
	public TableColumn<Property, String> getLeftTableCol() {
		return leftTableCol;
	}

	public TableColumn<Property, String> getRightTableCol() {
		return rightTableCol;
	}
	
	public TableView<Property> getTable(){
		return myTable;
	}
}
