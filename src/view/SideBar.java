package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import resources.Strings;

public class SideBar extends SLogoView {

	//make this into a new class with its own stuff that have variablesView and commandView and historyView?
		//sidebarPane class with parameters that specify column constraints / location
		public SideBar(){
			
			VBox sidebar = new VBox();
			
			sidebar.setPadding(new Insets(0,15,0,15));
			sidebar.setSpacing(8);

		    Hyperlink infoPage = new Hyperlink ("Get help");
		  //  title.setTextAlignment(TextAlignment.CENTER); //why does this not work
	   //     VBox.setMargin(infoPage, new Insets(0, 0, 0, 50));
		    sidebar.getChildren().add(infoPage);
		    
		    //customization
		    Text title = new Text("Customization");
		    title.setFont(new Font(13));
		    title.setUnderline(true);
		    sidebar.getChildren().add(title);
	 
		    
		    // select turtle image    
		    HBox hbox = new HBox(10);  
		    Text selectTurtle = new Text("Select Turtle");    
		    Button uploadImg = new Button("Upload");
		    uploadImg.setPrefSize(100, 20);
		    uploadImg.setPadding(new Insets(0,0,0,3));
		    uploadImg.setOnAction(e -> uploadTurtleFile(myTurtles.get(0)));

		    hbox.getChildren().addAll(selectTurtle, uploadImg);
		    sidebar.getChildren().add(hbox); 
		    
		    // select pen color	
		    HBox hbox2 = new HBox(10);  
		    Text selectPenColor = new Text(Strings.SELECT_PEN_COLOR);   
		    ColorPicker penColorChoice = new ColorPicker(Color.BLACK);
		    hbox2.getChildren().addAll(selectPenColor, penColorChoice);
		    sidebar.getChildren().add(hbox2);

		    // select background color
		    HBox hbox3 = new HBox(10);  
		    Text selectBackgroundColor = new Text(Strings.SELECT_BACKGROUND_COLOR);   
		    ColorPicker backgroundChoice = new ColorPicker(Color.WHITE);
	        backgroundChoice.setOnAction(e -> changeBackgroundColor(backgroundChoice.getValue()));

		    
		    // User can pick color for the stroke
	        ColorPicker strokeColorChoice = new ColorPicker(Color.BLACK);
	        strokeColorChoice.setOnAction(e -> drawer.changeColor(strokeColorChoice.getValue()));
	        
		    hbox3.getChildren().addAll(selectBackgroundColor, backgroundChoice);
		    sidebar.getChildren().add(hbox3);
		    
		    
		    // variables pane
		    Text variables = new Text(myResources.getString(Strings.VARIABLES_HEADER)); //is this necessary to use a .properties file AND a strings class?
		    variables.setFont(new Font(15));
		    variables.setUnderline(true);
		    sidebar.getChildren().add(variables); 
		    

		    
		    ObservableList<Variable> variablesList =FXCollections.observableArrayList (
		    	new Variable("var1", 1),
		    	new Variable("var2", 2)	
		    );
		    variablesList.add(new Variable("Added var2.5", 5));

		    
		    TableView<Variable> variablesTable = new TableView<Variable>();
		    variablesTable.setEditable(true);
		    
		    //why can't I add columns to this table??
		    //http://code.makery.ch/java/javafx-8-tutorial-part2/
		    //http://docs.oracle.com/javafx/2/ui_controls/table-view.htm
		    
	        TableColumn<Variable, String> variablesCol = new TableColumn<Variable, String>("Variables");
	        //System.out.println("pref: " + sidePane.getMaxWidth());
	        //variablesCol.setPrefWidth(sidePane.getPrefWidth()/2);
	        TableColumn<Variable, Integer> valuesCol = new TableColumn<Variable, Integer>("Values");
	        
	        variablesCol.setCellValueFactory(new PropertyValueFactory<Variable,String>("myName"));
	        valuesCol.setCellValueFactory(new PropertyValueFactory<Variable,Integer>("myValue"));
	        
	       // variablesCol.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
	       // valuesCol.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
	        
	        //variablesTable.getColumns().setAll(variablesCol, valuesCol);
	        variablesTable.setItems(variablesList);
	        
	        variablesTable.setMaxWidth(Double.MAX_VALUE);
	        variablesTable.setPrefHeight(150);
	        
	        sidebar.getChildren().add(variablesTable);
		    
		    /*When you call Cell.commitEdit(Object) an event is fired to the TableView, which you can observe by adding an EventHandler via TableColumn.setOnEditCommit(javafx.event.EventHandler). Similarly, you can also observe edit events for edit start and edit cancel.*/
		    //how to remove the extra column?
		    
		    //example of how to set new elements to the observablelist
		    variablesList.add(new Variable("Added var3", 3));
		    variablesTable.setItems(variablesList);
		    
		    //user-defined commands
		    Text userCommands = new Text(Strings.USER_DEFINED_COMMANDS_HEADER);
		    userCommands.setFont(new Font(15));
		    userCommands.setUnderline(true);
		    getChildren().add(userCommands);
		    
		    ListView<String> userCommandsList = new ListView<String>();
		    ObservableList<String> commandsItems =FXCollections.observableArrayList (
		    	    "String1", "String2", "String3", "String 4","String1", "String2", "String3", "String 4", "String1", "String2", "String3", "String 4");
		    userCommandsList.setItems(commandsItems);
		    userCommandsList.setMaxWidth(Double.MAX_VALUE);
		    userCommandsList.setPrefHeight(150);
		    sidebar.getChildren().add(userCommandsList);
		    
		    // history pane
		    Text history = new Text(Strings.HISTORY_HEADER);
		    history.setFont(new Font(15));
		    history.setUnderline(true);
		    sidebar.getChildren().add(history);
		    
		    ListView<String> historyList = new ListView<String>();
		    ObservableList<String> historyItems =FXCollections.observableArrayList (
		    	    "String1", "String2", "String3", "String 4","String1", "String2", "String3", "String 4", "String1", "String2", "String3", "String 4");
		    historyList.setItems(historyItems);
		    historyList.setMaxWidth(Double.MAX_VALUE);
		    historyList.setPrefHeight(150);
		    sidebar.getChildren().add(historyList);
		}
}
