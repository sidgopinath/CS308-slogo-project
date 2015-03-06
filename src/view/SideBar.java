package view;

//move lambda function into the main UI? 

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.ResourceBundle;

import controller.SLogoController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

public class SideBar extends VBox {

	private Map<Integer, TurtleView> myTurtles;
	private ListView<String> historyList;
	private ObservableList<String> historyItems;
	private ObservableList<Property> variablesList;
	private SLogoController myController;
	private TableView<Property> variablesTable;
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources.display/";
    private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "english");
	 private Workspace myWorkspace;

	// when do you ever use getActiveTurtle???? If it is never used, remove
	// myWorkspace completely and the method from the workspace class

	// private ObservableList<Property> turtlePropertiesList;
	private TableView<Property> turtlePropertiesTable;
	// variablesList.add(new Variable("Added var2.5", 5));

	private ObservableList<String> commandItems;

	// make this into a new class with its own stuff that have variablesView and
	// commandView and historyView???
	// sidebarPane class with parameters that specify column constraints /
	// location

	public SideBar(Workspace workspace, SLogoController controller) {
	//	myTurtles = turtleList;
		myController = controller;
		 myWorkspace = workspace;
		
		
		setPadding(new Insets(5, 15, 0, 0));
		setSpacing(5);
		setMaxWidth(Double.MAX_VALUE);

		// turtle properties
		Text title = new Text("Turtle Properties");
		title.setFont(new Font(15));
		title.setUnderline(true);
		// title.setTextAlignment(TextAlignment.CENTER);
		getChildren().add(title);

		createTurtlePropertiesTable();
		// updateTurtleProperties();

		// variables pane
		Text variables = new Text(Strings.VARIABLES_HEADER);
		// is this necessary to use a .properties file AND a strings class?
		variables.setFont(new Font(15));
		variables.setUnderline(true);
		getChildren().add(variables);

		variablesList = FXCollections.observableArrayList();
		// variablesList.add(new Variable("Added var2.5", 5));

		variablesTable = new TableView<Property>();
		TableColumn<Property, String> variablesCol = new TableColumn<Property, String>(
		        myResources.getString("Variables"));
		// variablesCol.setPrefWidth(sidePane.getPrefWidth()/2);
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

		// user-defined commands
		Text userCommands = new Text(Strings.USER_DEFINED_COMMANDS_HEADER);
		userCommands.setFont(new Font(15));
		userCommands.setUnderline(true);
		getChildren().add(userCommands);

		ListView<String> userCommandsList = new ListView<String>();
		commandItems = FXCollections.observableArrayList();
		// create an object instead
		userCommandsList.setItems(commandItems);
		userCommandsList.setMaxWidth(Double.MAX_VALUE);
		userCommandsList.setPrefHeight(150);
		getChildren().add(userCommandsList);

		// history pane
		Text history = new Text(Strings.HISTORY_HEADER);
		history.setFont(new Font(15));
		history.setUnderline(true);
		getChildren().add(history);

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
							myController.parseInput(historyList.getFocusModel()
									.getFocusedItem());
						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InstantiationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

		// return sidePane;
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

		// turtlePropertiesList = FXCollections.observableArrayList(new
		// Property("ID", myWorkspace.getActiveTurtle().getID()), new
		// Property("Pen Position", "Down"));
		// variablesList.add(new Variable("Added var2.5", 5));

		turtlePropertiesTable = new TableView<Property>();

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
		//updateTurtleProperties(1, myWorkspace); // TODO: change ID to 1

		getChildren().add(turtlePropertiesTable);
		// turtlePropertiesTable.setItems(turtlePropertiesList);
	}

	// if we use an observer this does not have to be updated every time
	protected void updateTurtleProperties(int ID, Workspace workspace) {
		// myWorkspace.getActiveTurtle();
	    DecimalFormat decimalFormat = new DecimalFormat("#.#");
		TurtleView updatedTurtle = workspace.getTurtleMap().get(ID);
		// for (String property : turtlePropertiesList) {
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

		// remove all current
		// turtle ID
		/*
		 * HBox turtleIDBox = new HBox(); Text turtleIDText = new
		 * Text("Turtle ID"); turtleIDBox.getChildren().add(turtleIDText);
		 * 
		 * TextField turtleIDField = new TextField();
		 * turtleIDBox.getChildren().add(turtleIDField);
		 * 
		 * getChildren().add(turtleIDBox);
		 * 
		 * //pen up? HBox penUpBox = new HBox(); Text penUpText = new
		 * Text("Pen"); turtleIDBox.getChildren().add(penUpText);
		 * 
		 * TextField turtleIDField = new TextField();
		 * turtleIDBox.getChildren().add(turtleIDField);
		 * 
		 * getChildren().add(turtleIDBox);
		 */

	}

}
