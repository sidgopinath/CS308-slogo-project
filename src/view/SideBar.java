// This entire file is part of my masterpiece.
// Sid Gopinath

package view;

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
import model.ExecutionEnvironment;
import model.Parser;

/**
 * Right sidebar containing display properties of a turle's history, commands
 * header, variables, and properties. The user can interact with some of these
 * modules and directly edit/change them
 * 
 * @author Callie
 *
 */

public class SideBar extends VBox {

	private ObservableList<String> myHistoryItems;
	private ObservableList<Property> myVariablesList;
	private Parser myParser;
	private TableView<Property> myVariablesTable;
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.display/";
	private ResourceBundle myResources = ResourceBundle
			.getBundle(DEFAULT_RESOURCE_PACKAGE + "english");
	private TableView<Property> myTurtlePropertiesTable;
	private ObservableList<String> myCommandItems;
	private ExecutionEnvironment myEnvironment;

	public SideBar() {
		setDimensionRestrictions();
		createTurtlePropertiesTable();
		createVariablesPane();
		createUserCommandsPane();
		createHistoryPane();

	}

	public void setParser(Parser parser) {
		myParser = parser;
	}

	public void setHistory(String string) {
		myHistoryItems.add(string);
	}

	public void updateVariable(Property variable) {
		for (Property o : myVariablesTable.getItems()) {
			if (o.getName().equals(variable.getName())) {
				o.setValue(variable.getValue());
				return;
			}
		}
		myVariablesList.add(variable);
	}

	public void updateCommand(String newCommand) {
		for (String command : myCommandItems) {
			if (command.equals(newCommand))
				return;
		}
		myCommandItems.add(newCommand);
	}

	private void createTurtlePropertiesTable() {
		createTitleText(myResources.getString("PropertiesHeader"));
		myTurtlePropertiesTable = new TableView<Property>();
		createColumns();
		myTurtlePropertiesTable.setEditable(true);
		myTurtlePropertiesTable.setMaxWidth(Double.MAX_VALUE);
		myTurtlePropertiesTable.setPrefHeight(130);
		getChildren().add(myTurtlePropertiesTable);
	}

	private void createColumns() {
		TableColumn<Property, String> propertiesCol = new TableColumn<Property, String>(
				myResources.getString("Properties"));
		TableColumn<Property, String> valuesCol = new TableColumn<Property, String>(
				myResources.getString("Values"));
		propertiesCol.setCellValueFactory(new PropertyValueFactory<Property, String>(
				"myName"));
		valuesCol.setCellValueFactory(new PropertyValueFactory<Property, String>(
				"myProperty"));
		propertiesCol.setPrefWidth(164); // TODO: set dynamically
		valuesCol.setPrefWidth(164); // divide width by 7.3 or 7.4
		myTurtlePropertiesTable.getColumns().addAll(propertiesCol, valuesCol);
	}

	protected void updateTurtleProperties(int ID, Workspace workspace) {
		DecimalFormat decimalFormat = new DecimalFormat("#.#");
		TurtleView updatedTurtle = workspace.getTurtleMap().get(ID);
		ObservableList<Property> turtlePropertiesList = FXCollections
				.observableArrayList(
						new Property(myResources.getString("ID"), String
								.valueOf(updatedTurtle.getID())),
						new Property(myResources.getString("XPos"), String
								.valueOf(decimalFormat.format(updatedTurtle
										.getTranslateX()))),
						new Property(myResources.getString("YPos"), String
								.valueOf(updatedTurtle.getYCoord())),
						new Property(myResources.getString("Heading"), String
								.valueOf(updatedTurtle.getHeading())),
						new Property(myResources.getString("PenPos"), updatedTurtle
								.getPenPosition()),
						new Property(myResources.getString("TurImg"), updatedTurtle
								.isShowing()));
		myTurtlePropertiesTable.setItems(turtlePropertiesList);
	}

	private void setDimensionRestrictions() {
		setPadding(new Insets(5, 15, 0, 0));
		setSpacing(3);
		setMaxWidth(Double.MAX_VALUE);
	}

	private void createTitleText(String s) {
		Text title = new Text(s);
		title.setFont(new Font(13));
		title.setUnderline(true);
		getChildren().add(title);
	}

	private void createVariablesPane() {
		createTitleText(myResources.getString("VariablesHeader"));
		myVariablesList = FXCollections.observableArrayList();
		myVariablesTable = new TableView<Property>();
		createVariableColumns();
		myVariablesTable.setEditable(true);
		myVariablesTable.setMaxWidth(Double.MAX_VALUE);
		myVariablesTable.setPrefHeight(130);
		getChildren().add(myVariablesTable);
		myVariablesTable.setItems(myVariablesList);
	}

	private void createVariableColumns() {
		TableColumn<Property, String> variablesCol = new TableColumn<Property, String>(
				myResources.getString("Variables"));
		TableColumn<Property, Double> valuesCol = new TableColumn<Property, Double>(
				myResources.getString("Values"));
		variablesCol.setCellValueFactory(new PropertyValueFactory<Property, String>(
				"myName"));
		valuesCol
				.setCellValueFactory(new PropertyValueFactory<Property, Double>("myVar"));
		variablesCol.setPrefWidth(164); // TODO: set dynamically
		valuesCol.setPrefWidth(164);
		variablesCol.setEditable(true);
		valuesCol.setCellFactory(TextFieldTableCell
				.forTableColumn(new StringConverter<Double>() {
					@Override
					public Double fromString(String userInput) {
						return Double.valueOf(userInput);
					}

					@Override
					public String toString(Double t) {
						return t.toString();
					}
				}));
		valuesCol.setOnEditCommit(new EventHandler<CellEditEvent<Property, Double>>() {
			@Override
			public void handle(CellEditEvent<Property, Double> t) {
				Property variable = t.getTableView().getItems()
						.get(t.getTablePosition().getRow());
				variable.setValue(t.getNewValue());
				if (myEnvironment == null) {
					System.out.println("lul");
				}
				myEnvironment.myVariableMap.put(variable.getName(), t.getNewValue());
				myEnvironment.updateObserver();
			}
		});

		myVariablesTable.getColumns().addAll(variablesCol, valuesCol);
	}

	private void createHistoryPane() {
		createTitleText(myResources.getString("HistoryHeader"));
		myHistoryItems = FXCollections.observableArrayList();
		ListView<String> historyList = createListView(myHistoryItems, 130);
		getChildren().add(historyList);

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

	private void createUserCommandsPane() {
		createTitleText(myResources.getString("UserDefinedCommandsHeader"));
		myCommandItems = FXCollections.observableArrayList();
		getChildren().add(createListView(myCommandItems, 130));
	}

	private ListView<String> createListView(ObservableList<String> items, int height) {
		ListView<String> list = new ListView<String>();
		list.setItems(items);
		list.setMaxWidth(Double.MAX_VALUE);
		list.setPrefHeight(130);
		return list;
	}

	public void updateExecutionEnvironment(ExecutionEnvironment env) {
		myEnvironment = env;
	}
}