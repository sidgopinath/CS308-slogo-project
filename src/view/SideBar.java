package view;

import java.io.File;
import java.util.List;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Hyperlink;
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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import resources.Strings;

public class SideBar extends VBox {

	Workspace myWorkspace;
	Stage myStage;
	Map<Integer,TurtleView> myTurtles;
	ListView<String> historyList;
	ObservableList<String> historyItems;

	// make this into a new class with its own stuff that have variablesView and
	// commandView and historyView?
	// sidebarPane class with parameters that specify column constraints /
	// location
	// dependencies are bad!!!!!!  
	public SideBar(Map<Integer,TurtleView> turtleList, Stage mainStage, Workspace workspace,
			Drawer drawer) {
		myStage = mainStage;
		myWorkspace = workspace;
		myTurtles = turtleList;

		setPadding(new Insets(0, 15, 0, 15));
		setSpacing(8);
		setMaxWidth(Double.MAX_VALUE);

		Hyperlink infoPage = new Hyperlink("Get help");
		infoPage.setOnAction((event) -> {
			WebView browser = new WebView();
			WebEngine webEngine = browser.getEngine();
			webEngine
					.load("http://www.cs.duke.edu/courses/compsci308/spring15/assign/03_slogo/commands.php");

			Stage stage = new Stage();
			// setupScene(stage, browser, 1000, 750);
			// TODO: Figure out how to utilize the setupScene method without
			// duplicating code
		});

		// title.setTextAlignment(TextAlignment.CENTER); //why does this not
		// work
		// VBox.setMargin(infoPage, new Insets(0, 0, 0, 50));
		getChildren().add(infoPage);

		// customization
		Text title = new Text("Customization");
		title.setFont(new Font(13));
		title.setUnderline(true);
		title.setTextAlignment(TextAlignment.CENTER);
		getChildren().add(title);

		// select turtle image
		HBox hbox = new HBox(10);
		Text selectTurtle = new Text("Select Turtle");
		Button uploadImg = new Button("Upload");
		uploadImg.setPrefSize(100, 20);
		uploadImg.setPadding(new Insets(0, 0, 0, 3));
		uploadImg.setOnAction(e -> uploadTurtleFile(myTurtles.get(0)));

		hbox.getChildren().addAll(selectTurtle, uploadImg);
		getChildren().add(hbox);

		// select pen color
		HBox hbox2 = new HBox(10);

		Text selectPenColor = new Text(Strings.SELECT_PEN_COLOR);
		ColorPicker penColorChoice = new ColorPicker(Color.BLACK);
		penColorChoice.setOnAction(e -> drawer.changeColor(penColorChoice.getValue()));

		hbox2.getChildren().addAll(selectPenColor, penColorChoice);
		// sidePane.getChildren().addAll(hbox2, strokeColorChoice);
		getChildren().add(hbox2);

		// select background color
		HBox hbox3 = new HBox(10);
		Text selectBackgroundColor = new Text(Strings.SELECT_BACKGROUND_COLOR);

		ColorPicker backgroundChoice = new ColorPicker(Color.WHITE);
		backgroundChoice.setOnAction(e -> changeBackgroundColor(backgroundChoice
				.getValue()));

		// User can pick color for the stroke

		hbox3.getChildren().addAll(selectBackgroundColor, backgroundChoice);
		getChildren().add(hbox3);

		// variables pane
		Text variables = new Text(Strings.VARIABLES_HEADER);
		// is this necessary to use a .properties file AND a strings class?
		variables.setFont(new Font(15));
		variables.setUnderline(true);
		getChildren().add(variables);

		ObservableList<Variable> variablesList = FXCollections.observableArrayList(
				new Variable("var1", 1.5), new Variable("var2", 2.5));
		variablesList.add(new Variable("Added var2.5", 5));

		TableView<Variable> variablesTable = new TableView<Variable>();

		// why can't I add columns to this table??
		// http://code.makery.ch/java/javafx-8-tutorial-part2/
		// http://docs.oracle.com/javafx/2/ui_controls/table-view.htm

		TableColumn<Variable, String> variablesCol = new TableColumn<Variable, String>(
				"Variables");
		// System.out.println("pref: " + sidePane.getMaxWidth());
		// variablesCol.setPrefWidth(sidePane.getPrefWidth()/2);
		TableColumn<Variable, Double> valuesCol = new TableColumn<Variable, Double>(
				"Values");

		variablesCol.setCellValueFactory(new PropertyValueFactory<Variable, String>(
				"myName"));
		valuesCol
				.setCellValueFactory(new PropertyValueFactory<Variable, Double>("myVar"));

		// is it possible to dynamically set this?
		variablesCol.setPrefWidth(164);
		valuesCol.setPrefWidth(164);
		valuesCol.setEditable(true);

		variablesCol.setCellFactory(TextFieldTableCell.forTableColumn());
		variablesCol.setOnEditCommit(new EventHandler<CellEditEvent<Variable, String>>() {
			@Override
			public void handle(CellEditEvent<Variable, String> t) {
				((Variable) t.getTableView().getItems()
						.get(t.getTablePosition().getRow())).setName(t.getNewValue());
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
		// CellValueFactory(.forTableColumn());
		valuesCol.setOnEditCommit(new EventHandler<CellEditEvent<Variable, Double>>() {
			@Override
			public void handle(CellEditEvent<Variable, Double> t) {
				((Variable) t.getTableView().getItems()
						.get(t.getTablePosition().getRow())).setValue(t.getNewValue());
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
		variablesList.add(new Variable("Added var3", 3));
		variablesTable.setItems(variablesList);

		// user-defined commands
		Text userCommands = new Text(
				Strings.USER_DEFINED_COMMANDS_HEADER);
		userCommands.setFont(new Font(15));
		userCommands.setUnderline(true);
		getChildren().add(userCommands);

		ListView<String> userCommandsList = new ListView<String>();
		ObservableList<String> commandsItems = FXCollections.observableArrayList(
				"String1", "String2", "String3");
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
		//setHistory("test");
		getChildren().add(historyList);
		
		
		//TODO: Fix
		 historyList.getSelectionModel().selectedItemProperty().addListener(
		            new ChangeListener<String>() {
		                public void changed(ObservableValue<? extends String> ov, 
		                    String old_val, String new_val) {
		                        myController.parseInput();
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
	
	public void setHistory(String string){
		historyItems.add(string);	
		//TODO: WHY DOESN'T THIS WORK?
		
	}
}
