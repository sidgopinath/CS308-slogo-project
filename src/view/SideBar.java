package view;

//move lambda function into the main UI? 

import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import model.Parser;


public class SideBar extends VBox {

	private ListView<String> historyList;
	private ObservableList<String> historyItems;
	private ObservableList<Property> variablesList;
	private Parser myParser;
	private TableView<Property> variablesTable;
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources.display/";
    private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "english");
	private TableView<Property> turtlePropertiesTable;
	private ObservableList<String> commandItems;

	// make this into a new class with its own stuff that have variablesView and
	// commandView and historyView???
	// sidebarPane class with parameters that specify column constraints /
	// location

	public SideBar(Workspace workspace, Parser parser) {
		myParser = parser;
		
		setDimensionRestrictions();
		createTurtlePropertiesTable();
		createVariablesPane();

		

		
		createUserCommandsPane();
		createHistoryPane();
	}

	public void setHistory(String string) {
		historyItems.add(string);
	}

	public void updateVariable(Property variable) {
		// variablesTable.getItems().stream().forEach((o) ->
		for (Property o : variablesTable.getItems()) {
			if (o.getName().equals(variable.getName())) {
				o.setValue(variable.getValue());
				return;
			}
		}
		variablesList.add(variable);
	}

	public void updateCommand(String newCommand) {
		for (String command : commandItems) {
			if (command.equals(newCommand))
				return;
		}
		commandItems.add(newCommand);
	}

	private void createTurtlePropertiesTable() {
		createTitleText(myResources.getString("PropertiesHeader"));
		turtlePropertiesTable = new TableView<Property>();

		//TODO: remove hardcoding
		TableColumn<Property, String> propertiesCol = new TableColumn<Property, String>(
		        myResources.getString("Properties"));
		TableColumn<Property, String> valuesCol = new TableColumn<Property, String>(
		        myResources.getString("Values"));

		propertiesCol.setCellValueFactory(new PropertyValueFactory<Property, String>(
		        "myName"));
		valuesCol.setCellValueFactory(new PropertyValueFactory<Property, String>(
		        "myProperty"));

		propertiesCol.setPrefWidth(152); // TODO: set dynamically
		valuesCol.setPrefWidth(148);

		turtlePropertiesTable.getColumns().addAll(propertiesCol, valuesCol);
		turtlePropertiesTable.setEditable(true);

		turtlePropertiesTable.setMaxWidth(Double.MAX_VALUE);
		turtlePropertiesTable.setPrefHeight(148);

		getChildren().add(turtlePropertiesTable);
	}

	// if we use an observer this does not have to be updated every time
	protected void updateTurtleProperties(int ID, Workspace workspace) {
	    DecimalFormat decimalFormat = new DecimalFormat("#.#");
		TurtleView updatedTurtle = workspace.getTurtleMap().get(ID);
		ObservableList<Property> turtlePropertiesList = FXCollections
			.observableArrayList(
				new Property(myResources.getString("ID"), String.valueOf(updatedTurtle.getID())),
				new Property(myResources.getString("XPos"), String.valueOf(decimalFormat.format(updatedTurtle
						.getTranslateX()))),
				new Property(myResources.getString("YPos"), String.valueOf(updatedTurtle
						.getYCoord())),
				new Property(myResources.getString("Heading"), String.valueOf(updatedTurtle
						.getHeading())), new Property(myResources.getString("PenPos"),
						updatedTurtle.getPenPosition()), new Property(
						myResources.getString("TurImg"), updatedTurtle.isShowing()));
		turtlePropertiesTable.setItems(turtlePropertiesList);
	}
	
	private void setDimensionRestrictions(){
		setPadding(new Insets(5, 15, 0, 0));
		setSpacing(3);
		setMaxWidth(Double.MAX_VALUE);
	}
	
	private void createTitleText(String s){
		Text title = new Text(s);
		title.setFont(new Font(13));
		title.setUnderline(true);
		getChildren().add(title);
	}
	
	private void createVariablesPane(){
		createTitleText(myResources.getString("VariablesHeader"));

		variablesList = FXCollections.observableArrayList();
		variablesTable = new TableView<Property>();
		TableColumn<Property, String> variablesCol = new TableColumn<Property, String>(
		        myResources.getString("Variables"));
		TableColumn<Property, Double> valuesCol = new TableColumn<Property, Double>(
		        myResources.getString("Values"));

		variablesCol.setCellValueFactory(new PropertyValueFactory<Property, String>(
				"myName"));
		valuesCol.setCellValueFactory(new PropertyValueFactory<Property, Double>(
				"myVar"));

		variablesCol.setPrefWidth(164); // TODO: set dynamically
		valuesCol.setPrefWidth(164);
		// TODO:
		 valuesCol.setEditable(true);

		variablesCol.setCellFactory(TextFieldTableCell.forTableColumn());
		variablesCol
			.setOnEditCommit(new EventHandler<CellEditEvent<Property, String>>() {
				@Override
				public void handle(CellEditEvent<Property, String> t) {
					Property variable = t.getTableView().getItems().get(t.getTablePosition().getRow());
					variable.setName(t.getNewValue());
					
					myParser.parseInput("MAKE " + variable.getName() + " " + variable.getValue());
					//variablesTable.re
					//remove();
					//TODO
					System.out.println("MAKE " + variable.getName() + " " + variable.getValue());
					System.out.println("MAKE " + variable.getName() + " " + t.getNewValue());
					//TODO: send an update to the controller back to the backend. 
				}
			});

		// some issue with strings and ints
		valuesCol.setCellFactory(TextFieldTableCell
			.forTableColumn(new StringConverter<Double>() {

				@Override
				public Double fromString(String userInput) {
					// try{
					return Double.valueOf(userInput);
					/*
					 * } catch(NumberFormatException e){
					 * System.out.println("Number Format Exception"); }
					 */
				}

				@Override
				public String toString(Double t) {
					return t.toString();
				}
			}));
		valuesCol
				.setOnEditCommit(new EventHandler<CellEditEvent<Property, Double>>() {
					@Override
					public void handle(CellEditEvent<Property, Double> t) {
						t.getTableView().getItems().get(t.getTablePosition().getRow())
								.setValue(t.getNewValue());
					}
				});

		variablesTable.getColumns().addAll(variablesCol, valuesCol);
		variablesTable.setEditable(true);

		variablesTable.setMaxWidth(Double.MAX_VALUE);
		variablesTable.setPrefHeight(148);

		getChildren().add(variablesTable);
		variablesTable.setItems(variablesList);
	}
	
	private void createHistoryPane(){
		createTitleText(myResources.getString("HistoryHeader")); //TODO:

		historyList = new ListView<String>();
		historyItems = FXCollections.observableArrayList();
		historyList.setItems(historyItems);
		historyList.setMaxWidth(Double.MAX_VALUE);
		historyList.setPrefHeight(148);
		getChildren().add(historyList);

		historyList.getFocusModel().focusedItemProperty()
				.addListener(changeListener ->
								{if(historyList.getFocusModel().getFocusedItem()!=null){myParser.parseAndExecute(historyList.getFocusModel()
												.getFocusedItem());} historyList.getFocusModel().focus(-1);});
	}
	
	private void createUserCommandsPane(){
		createTitleText(myResources.getString("UserDefinedCommandsHeader")); 
		ListView<String> userCommandsList = new ListView<String>();
		commandItems = FXCollections.observableArrayList();
		// create an object instead
		userCommandsList.setItems(commandItems);
		userCommandsList.setMaxWidth(Double.MAX_VALUE);
		userCommandsList.setPrefHeight(148);
		getChildren().add(userCommandsList);

	}
	

}
