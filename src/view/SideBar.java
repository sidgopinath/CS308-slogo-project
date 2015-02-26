package view;

//move lambda function into the main UI? 

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import controller.SLogoController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class SideBar extends VBox {

	Workspace myWorkspace;
	Stage myStage;
	Map<Integer, TurtleView> myTurtles;
	ListView<String> historyList;
	ObservableList<String> historyItems;
	ObservableList<VariableView> variablesList;
	SLogoController myController;
	TableView<VariableView> variablesTable;

	// make this into a new class with its own stuff that have variablesView and
	// commandView and historyView???
	// sidebarPane class with parameters that specify column constraints /
	// location

	public SideBar(Map<Integer, TurtleView> turtleList, Stage mainStage,
			Workspace workspace, Drawer drawer, SLogoController controller) {
		myStage = mainStage;
		myWorkspace = workspace;
		myTurtles = turtleList;
		myController = controller;

		setPadding(new Insets(0, 15, 0, 15));
		setSpacing(5);
		setMaxWidth(Double.MAX_VALUE);

		// customization
		Text title = new Text("Customization");
		title.setFont(new Font(15));
		title.setUnderline(true);
		title.setTextAlignment(TextAlignment.CENTER);
		getChildren().add(title);

		// select language
		HBox selectLanguage = new HBox(10);
		Text select = new Text("Select Language");
		ComboBox<String> languageOptions = new ComboBox<String>();
		ObservableList<String> languages = FXCollections.observableArrayList("English",
				"Chinese", "French", "German", "Italian", "Japanese", "Korean",
				"Portugese", "Russian", "Spanish");

		languageOptions.setItems(languages);
		languageOptions.setValue(languages.get(0));

		selectLanguage.getChildren().addAll(select, languageOptions);
		getChildren().add(selectLanguage);

		languageOptions.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> ov,
							String old_val, String new_val) {
						System.out.println(new_val);
						myController.setLanguage(new_val);

					}
				});

		// select turtle image
		HBox customizeTurtleBox = new HBox(10);
		Text selectTurtle = new Text("Select Turtle");
		Button uploadImg = new Button("Upload");
		uploadImg.setPrefSize(100, 20);
		uploadImg.setPadding(new Insets(0, 0, 0, 3));
		uploadImg.setOnAction(e -> uploadTurtleFile(myTurtles.get(0)));

		customizeTurtleBox.getChildren().addAll(selectTurtle, uploadImg);
		getChildren().add(customizeTurtleBox);

		// select pen color
		HBox customizePenBox = new HBox(10);

		Text selectPenColor = new Text(Strings.SELECT_PEN_COLOR);
		ColorPicker penColorChoice = new ColorPicker(Color.BLACK);
		penColorChoice.setOnAction(e -> drawer.changeColor(penColorChoice.getValue()));

		customizePenBox.getChildren().addAll(selectPenColor, penColorChoice);
		// sidePane.getChildren().addAll(hbox2, strokeColorChoice);
		getChildren().add(customizePenBox);

		// select background color
		HBox customizeBackgroundBox = new HBox(10);
		Text selectBackgroundColor = new Text(Strings.SELECT_BACKGROUND_COLOR);

		ColorPicker backgroundChoice = new ColorPicker(Color.WHITE);
		backgroundChoice.setOnAction(e -> changeBackgroundColor(backgroundChoice
				.getValue()));

		// User can pick color for the stroke

		customizeBackgroundBox.getChildren().addAll(selectBackgroundColor,
				backgroundChoice);
		getChildren().add(customizeBackgroundBox);

		// variables pane
		Text variables = new Text(Strings.VARIABLES_HEADER);
		// is this necessary to use a .properties file AND a strings class?
		variables.setFont(new Font(15));
		variables.setUnderline(true);
		getChildren().add(variables);

		variablesList = FXCollections.observableArrayList(new VariableView("var2.5", 5));
		//variablesList.add(new Variable("Added var2.5", 5));

		variablesTable = new TableView<VariableView>();
		TableColumn<VariableView, String> variablesCol = new TableColumn<VariableView, String>(
				"Variables");
		// variablesCol.setPrefWidth(sidePane.getPrefWidth()/2);
		TableColumn<VariableView, Double> valuesCol = new TableColumn<VariableView, Double>(
				"Values");

		variablesCol.setCellValueFactory(new PropertyValueFactory<VariableView, String>(
				"myName"));
		valuesCol
				.setCellValueFactory(new PropertyValueFactory<VariableView, Double>("myVar"));

		variablesCol.setPrefWidth(164); // TODO: set dynamically
		valuesCol.setPrefWidth(164);
		valuesCol.setEditable(true);

		variablesCol.setCellFactory(TextFieldTableCell.forTableColumn());
		variablesCol.setOnEditCommit(new EventHandler<CellEditEvent<VariableView, String>>() {
			@Override
			public void handle(CellEditEvent<VariableView, String> t) {
				t.getTableView().getItems().get(t.getTablePosition().getRow())
						.setName(t.getNewValue());
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
		valuesCol.setOnEditCommit(new EventHandler<CellEditEvent<VariableView, Double>>() {
			@Override
			public void handle(CellEditEvent<VariableView, Double> t) {
				t.getTableView().getItems().get(t.getTablePosition().getRow())
						.setValue(t.getNewValue());
			}
		});

		variablesTable.getColumns().addAll(variablesCol, valuesCol);
		variablesTable.setEditable(true);

		variablesTable.setMaxWidth(Double.MAX_VALUE);
		variablesTable.setPrefHeight(150);

		getChildren().add(variablesTable);

		/*
		 * When you call Cell.commitEdit(Object) an event is fired to the
		 * TableView, which you can observe by adding an EventHandler via
		 * TableColumn.setOnEditCommit(javafx.event.EventHandler). Similarly,
		 * you can also observe edit events for edit start and edit cancel.
		 */
		// how to remove the extra column?

		// example of how to set new elements to the observablelist
		//variablesList.add(new Variable("Added var3", 3));
		variablesTable.setItems(variablesList);

		//System.out.println("beforeupdatevar");
		updateVariable(new VariableView("Added var3", 4));
		//System.out.println("afterupdatevar");

		// user-defined commands
		Text userCommands = new Text(Strings.USER_DEFINED_COMMANDS_HEADER);
		userCommands.setFont(new Font(15));
		userCommands.setUnderline(true);
		getChildren().add(userCommands);

		ListView<String> userCommandsList = new ListView<String>();
		ObservableList<String> commandsItems = FXCollections.observableArrayList(
				"String1", "String2", "String3");
		//create an object instead
		userCommandsList.setItems(commandsItems);
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
		// setHistory("test");
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

	private void uploadTurtleFile(TurtleView turtle) {
		File file = displayFileChooser();
		changeTurtleImage(turtle, new Image(file.toURI().toString()));
	}

	private File displayFileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Image File");
		fileChooser.getExtensionFilters()
				.add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg",
						"*.gif"));
		return fileChooser.showOpenDialog(myStage);
	}

	private void changeTurtleImage(TurtleView turtle, Image img) {
		turtle.setImage(img);
	}

	private void changeBackgroundColor(Color color) {
		myWorkspace.setBackground(color);
	}

	public void setHistory(String string) {
		historyItems.add(string);
	}

	public void updateVariable(VariableView variable) {
		// variablesTable.getItems().stream().forEach((o) ->
		for (VariableView o : variablesTable.getItems()) {
			System.out.println("updatevar in sidebar class");
			System.out.println(o.getName());
			System.out.println(variable.getName());
			if (o.getName().equals(variable.getName())) {
				System.out.println("already exists. let's edit this variable");
				o.setValue(variable.getValue());
				return;
			}
		}
		;

		variablesList.add(variable);

		/*
		 * t.getTableView().getItems()
		 * .get(t.getTablePosition().getRow())).setName(t.getNewValue());
		 */

	}
}
