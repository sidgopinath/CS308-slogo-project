package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.io.File;
import java.util.ResourceBundle;

import controller.Command;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SLogoView {
	
	private Stage myStage;
	private Scene myScene;
	private GridPane myRoot;
    private ResourceBundle myResources;
    
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources.display/"; //can this be put somewhere else? public variable in a different class?
	public static final int GRID_SIZE = 00;

	public SLogoView(Stage s) {
		myStage = s;
		
		//potentially make this into an individual class
		//create root node
		myRoot = new GridPane();
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "english");
		
		configureUI();
		setupScene();

	}
	
	private void configureUI() {
	//	myRoot.setAlignment(Pos.CENTER);
		//myRoot.add(makeSimulationButtons(), 1, 2);   // new root from a class? or just a new mehtod?
		//use a new class for things like the text box since pressing update will have to update naw and then the view will have to be updated as well
	//	myRoot.setHgap(10);
	//	myRoot.setVgap(10);
		
		Text title = new Text("Slogo");
	    title.setFont(new Font(30));
	    title.setTextAlignment(TextAlignment.CENTER); //why does this not work
		myRoot.add(title,0,0,2,1);
		
		RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(10);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(70);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(20);
		myRoot.getRowConstraints().add(row1);
		myRoot.getRowConstraints().add(row2);
		myRoot.getRowConstraints().add(row3);
		
		ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(70);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(30);
        
        Group display = new Group(new Rectangle(0, 0, GRID_SIZE, GRID_SIZE));
       // display.setAlignment(Pos.CENTER);
        myRoot.add(display,0,1);
        
        //getchildren.clear()
        
		myRoot.getColumnConstraints().add(col1);
		myRoot.getColumnConstraints().add(col2);
		myRoot.add(makeRightSidebar(),1,1,1,2); //col, row, colspan, rowspan
		myRoot.add(makeEditor(),0,2);
	}
	
	//make this into a new class with its own stuff that have variablesView and selectionView and historyView?
	//sidebarPane class with parameters that specify column constraints / location
	private VBox makeRightSidebar(){
		VBox sidePane = new VBox();
		sidePane.setPadding(new Insets(15));
		sidePane.setSpacing(12);

	    Hyperlink infoPage = new Hyperlink ("Get help");
	  //  title.setTextAlignment(TextAlignment.CENTER); //why does this not work
   //     VBox.setMargin(infoPage, new Insets(0, 0, 0, 50));
	    sidePane.getChildren().add(infoPage);
		
	    
	    //customization
	    Text title = new Text("Customization");
	    title.setFont(new Font(15));
	    title.setUnderline(true);
	    sidePane.getChildren().add(title);

	    //title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
	    

	    
	    // select turtle image
	    
	    HBox hbox = new HBox(10);  
	    Text selectTurtle = new Text("Select Turtle");    
	    Button buttonCurrent = new Button("Upload");
	    buttonCurrent.setPrefSize(100, 20);
	    buttonCurrent.setPadding(new Insets(0,0,0,3));

	    hbox.getChildren().addAll(selectTurtle, buttonCurrent);
	    sidePane.getChildren().add(hbox);
	    
	   
	    
	    // select pen color	
	    HBox hbox2 = new HBox(10);  
	    Text selectPenColor = new Text("Select Pen Color");   
	    ColorPicker penColorChoice = new ColorPicker(Color.BLACK);
	    hbox2.getChildren().addAll(selectPenColor, penColorChoice);
	    sidePane.getChildren().add(hbox2);

	    // select background color
	    HBox hbox3 = new HBox(10);  
	    Text selectBackgroundColor = new Text("Select Background Color");   
	 //   ChoiceBox backgroundChoice = new ChoiceBox(FXCollections.observableArrayList("Black", "Red", "Orange", "Yellow", "Green", "Blue", "Purple"));
//	    backgroundChoice.getSelectionModel().select(0);
	    ColorPicker backgroundChoice = new ColorPicker(Color.BLACK);
	    hbox3.getChildren().addAll(selectBackgroundColor, backgroundChoice);
	    sidePane.getChildren().add(hbox3);
	    
	    // variables pane
	    
	    Text variables = new Text("Variables");
	    variables.setFont(new Font(15));
	    variables.setUnderline(true);
	    sidePane.getChildren().add(variables);
	    
	    ListView<String> list = new ListView<String>();
	    ObservableList<String> items =FXCollections.observableArrayList (
	    	    "String1", "String2");
	    list.setItems(items);
	    list.setMaxWidth(Double.MAX_VALUE);
	    list.setPrefHeight(200);
	    
	    sidePane.getChildren().add(list);
	    
	    
	    //example of how to set new elements
	    items.add("hi");
	    list.setItems(items);
	    
	    
	  //getObservableList
	    //addto observablelist
	    
	    // history pane

	    Text history = new Text("History");
	    history.setFont(new Font(15));
	    history.setUnderline(true);
	    sidePane.getChildren().add(history);
	    
	    ListView<String> historyList = new ListView<String>();
	    ObservableList<String> historyItems =FXCollections.observableArrayList (
	    	    "String1", "String2");
	    historyList.setItems(historyItems);
	    historyList.setMaxWidth(Double.MAX_VALUE);
	    historyList.setPrefHeight(200);
	    
	    sidePane.getChildren().add(historyList);


	    
	    return sidePane;
	}
	
	//bottom row
	private HBox makeEditor(){
		HBox bottomRow = new HBox();
		bottomRow.setPadding(new Insets(15));
		bottomRow.setSpacing(15);
		
		// text area
	    TextArea textEditor = new TextArea();   
	    textEditor.setMaxHeight(Double.MAX_VALUE);
	    textEditor.setPrefSize(750, 120); //this should be dynamically alterable?
	    bottomRow.getChildren().add(textEditor);
	    
	    // run button
	    Button runButton = new Button("Run");
	  //  runButton.setPrefSize(100, 120);
	    runButton.setMaxWidth(Double.MAX_VALUE);
	    runButton.setMaxHeight(Double.MAX_VALUE);
	    //runButton.setPadding(new Insets(0,0,0,3));
	    bottomRow.getChildren().add(runButton);
		
		return bottomRow;
	}
	
	private File displayFileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Image File");
		fileChooser.getExtensionFilters().add(
		new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		return fileChooser.showOpenDialog(myStage);
	}
	
	
	private void update(Command command){
		//update the grid
		//updateGrid(command.getLines());
		//VariablesView.updateVars(command.getVariables());
		//variables view will have configuredisplay(), update(), and event handlers
		//updateHistory(command.getStrings());
	}
	
	private void setupScene() {
		myScene = new Scene(myRoot, 1200, 700);
		myStage.setTitle(myResources.getString("Title"));
		myStage.setScene(myScene);
		//what happens if you set multiple scenes?
		myStage.show();
	}
	
	//UPON BUTTON CLICK:
	
	//  File newFile = displayFileChooser();
	
	/* colorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                text.setFill(colorPicker.getValue());               
            }
        });*/

}
