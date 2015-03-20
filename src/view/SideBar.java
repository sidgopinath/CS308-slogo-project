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
	private TableView<Property> myTurtlePropertiesTable;
	private ObservableList<String> myCommandItems;
	private ExecutionEnvironment myEnvironment;
	
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources.display/";
	private ResourceBundle myResources = ResourceBundle
			.getBundle(DEFAULT_RESOURCE_PACKAGE + "english");
	private static final String NAME_VARIABLE = "myName";
	private static final String PROPERTY_VARIABLE = "myProperty";

	private TurtlePropertiesView myTurtlePropertiesPane;
	private VariablesView myVariablesPane;

	public SideBar() {
		setDimensionRestrictions();
		
		myTurtlePropertiesPane = new TurtlePropertiesView(myResources.getString("PropertiesHeader"), getChildren(), myResources.getString("Properties"), myResources.getString("Properties"), NAME_VARIABLE, PROPERTY_VARIABLE);
		myVariablesPane = new VariablesView(myResources.getString("PropertiesHeader"), getChildren(), myResources.getString("Properties"), myResources.getString("Properties"), NAME_VARIABLE, PROPERTY_VARIABLE, myEnvironment);
		
		//below options would be instantiated similarly
		createUserCommandsPane();
		createHistoryPane();

	}

	public void setParser(Parser parser) {
		myParser = parser;
	}
	
	public void updateVariable(Property variable) {
		myVariablesPane.update(variable);
	}
	
	public void setHistory(String string) {
		myHistoryItems.add(string);
	}
	
	protected void updateTurtleProperties(int ID, Workspace workspace) {
		myTurtlePropertiesPane.update(ID, workspace);
	}

	private void setDimensionRestrictions() {
		setPadding(new Insets(5, 15, 0, 0));
		setSpacing(3);
		setMaxWidth(Double.MAX_VALUE);
	}
	
	public void updateExecutionEnvironment(ExecutionEnvironment env) {
		myEnvironment = env;
	}
	
	
	
	
	
	
	
	
	
	
	//----------these below methods would be for the historyview and usercommandsview that have not yet been created in this setup

	public void updateCommand(String newCommand) {
		for (String command : myCommandItems) {
			if (command.equals(newCommand))
				return;
		}
		myCommandItems.add(newCommand);
	}


	
	private void createHistoryPane() {
	//	createTitleText(myResources.getString("HistoryHeader"));
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
	//	createTitleText(myResources.getString("UserDefinedCommandsHeader"));
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


}