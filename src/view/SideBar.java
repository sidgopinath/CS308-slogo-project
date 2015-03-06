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
		
		// turtle properties
		createTurtlePropertiesTable();
		// updateTurtleProperties();

		createVariablesPane();

		ListView<String> userCommandsList = new ListView<String>();
		commandItems = FXCollections.observableArrayList();
		// create an object instead
		userCommandsList.setItems(commandItems);
		userCommandsList.setMaxWidth(Double.MAX_VALUE);
		userCommandsList.setPrefHeight(150);
		getChildren().add(userCommandsList);

		// history pane
		createTitleText(Strings.HISTORY_HEADER); //TODO:

		historyList = new ListView<String>();
		historyItems = FXCollections.observableArrayList();
		historyList.setItems(historyItems);
		historyList.setMaxWidth(Double.MAX_VALUE);
		historyList.setPrefHeight(150);
		getChildren().add(historyList);

		// TODO: Selected item can only have action once until other item is
		// selected
		// TODO: Selecting the same command after another of the same command
		// does not work
		historyList.getFocusModel().focusedItemProperty()
				.addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> ov,
							String old_val, String new_val) {
						try {
							myParser.parseAndExecute(historyList.getFocusModel()
									.getFocusedItem());
						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
					}
					}});

		
		createUserCommandsPane();
		createHistoryPane();
	}

	public void setHistory(String string) {
		historyItems.add(string);
	}

	public void updateVariable(Property variable) {
		// variablesTable.getItems().stream().forEach((o) ->
		for (Property o : variablesTable.getItems()) {
			System.out.println("updatevar in sidebar class");
			System.out.println(o.getName());
			System.out.println(variable.getName());
			if (o.getName().equals(variable.getName())) {
				System.out.println("already exists. let's edit this variable");
				o.setValue(variable.getValue());
				return;
			}
		}

		variablesList.add(variable);

		/*
		 * t.getTableView().getItems()
		 * .get(t.getTablePosition().getRow())).setName(t.getNewValue());
		 */

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
		// variablesCol.setPrefWidth(sidePane.getPrefWidth()/2);
		TableColumn<Property, String> valuesCol = new TableColumn<Property, String>(
		        myResources.getString("Values"));

		propertiesCol.setCellValueFactory(new PropertyValueFactory<Property, String>(
		        "myName"));
		valuesCol.setCellValueFactory(new PropertyValueFactory<Property, String>(
		        "myProperty"));

		propertiesCol.setPrefWidth(152); // TODO: set dynamically
		valuesCol.setPrefWidth(150);

		turtlePropertiesTable.getColumns().addAll(propertiesCol, valuesCol);
		turtlePropertiesTable.setEditable(true);

		turtlePropertiesTable.setMaxWidth(Double.MAX_VALUE);
		turtlePropertiesTable.setPrefHeight(150);

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
		setSpacing(5);
		setMaxWidth(Double.MAX_VALUE);
	}
	
	private void createTitleText(String s){
		Text title = new Text(s);
		title.setFont(new Font(15));
		title.setUnderline(true);
		getChildren().add(title);
	}
	
	private void createVariablesPane(){
		Text variables = new Text(myResources.getString("ID"));
		// is this necessary to use a .properties file AND a strings class?
		variables.setFont(new Font(15));
		variables.setUnderline(true);
		getChildren().add(variables);

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
						t.getTableView().getItems().get(t.getTablePosition().getRow())
								.setName(t.getNewValue());
						
						//parseandexecute("MAKE :var 90");
						//TODO
						System.out.println("newvalue: " + t.getNewValue());
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
		variablesTable.setPrefHeight(150);

		getChildren().add(variablesTable);
		variablesTable.setItems(variablesList);
	}
	
	private void createHistoryPane(){
		createTitleText(myResources.getString("HistoryHeader")); //TODO:

		historyList = new ListView<String>();
		historyItems = FXCollections.observableArrayList();
		historyList.setItems(historyItems);
		historyList.setMaxWidth(Double.MAX_VALUE);
		historyList.setPrefHeight(150);
		getChildren().add(historyList);

		// TODO: Selected item can only have action once until other item is
		// selected
		// TODO: Selecting the same command after another of the same command
		// does not work
		historyList.getFocusModel().focusedItemProperty()
			.addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> ov,
					String old_val, String new_val) {
						myParser.parseAndExecute(historyList.getFocusModel()
								.getFocusedItem());

				}
			});
	}
	
	private void createUserCommandsPane(){
		createTitleText(myResources.getString("UserDefinedCommandsHeader")); 
		ListView<String> userCommandsList = new ListView<String>();
		commandItems = FXCollections.observableArrayList();
		// create an object instead
		userCommandsList.setItems(commandItems);
		userCommandsList.setMaxWidth(Double.MAX_VALUE);
		userCommandsList.setPrefHeight(150);
		getChildren().add(userCommandsList);
		
	}
	

}
