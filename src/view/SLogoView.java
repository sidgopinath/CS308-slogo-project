package view;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Polar;
import model.turtle.Turtle;
import model.turtle.TurtleCommand;
import resources.Strings;

public class SLogoView {
	private Stage myStage;
	//private Scene myScene;
	private GridPane myRoot;
    private ResourceBundle myResources;
    private Map<String,Node> variables;
    private Drawer drawer = new Drawer();
    //private StackPane myWorkspace;
    private Workspace myWorkspace;
    private int count=1;
	private Map<Integer, TurtleView> myTurtles = new HashMap<Integer,TurtleView>();
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources.display/"; //can this be put somewhere else? public variable in a different class?
	
	public SLogoView(Stage s) {
		myStage = s;	
		//create root node
		myRoot = new GridPane();
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "english");
		
		configureUI();
		//lila may not need instance variables anymore
		setupScene(myStage, myRoot, 1200, 750);
	}
	
	private void configureUI() {
	//	myRoot.setAlignment(Pos.CENTER);
		//myRoot.add(makeSimulationButtons(), 1, 2);   // new root from a class? or just a new mehtod?
		//use a new class for things like the text box since pressing update will have to update naw and then the view will have to be updated as well
		
		Text title = new Text("SLogo");
	    title.setFont(new Font(30));
	    title.setTextAlignment(TextAlignment.CENTER); //why does this not work
		myRoot.add(title,0,0,2,1);
		
		RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(5);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(75);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(20);
		myRoot.getRowConstraints().add(row1);
		myRoot.getRowConstraints().add(row2);
		myRoot.getRowConstraints().add(row3);
		
		ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(70);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(30);
        
        
        //add lines to a group
	    TurtleView turtle = new TurtleView(new Image(Strings.DEFAULT_TURTLE_IMG));
	    myTurtles.put(0,turtle);
        myWorkspace = new Workspace(myTurtles);
        //LILA
        myRoot.add(myWorkspace, 0, 1);
		myRoot.getColumnConstraints().add(col1);
		myRoot.getColumnConstraints().add(col2);
		myRoot.add(makeRightSidebar(),1,1,1,2); //col, row, colspan, rowspan
		myRoot.add(makeEditor(),0,2);
	}
	
	//make this into a new class with its own stuff that have variablesView and commandView and historyView?
		//sidebarPane class with parameters that specify column constraints / location
		private VBox makeRightSidebar(){
			VBox sidePane = new VBox();
			sidePane.setPadding(new Insets(0,15,0,15));
			sidePane.setSpacing(8);
			sidePane.setMaxWidth(Double.MAX_VALUE);

		    Hyperlink infoPage = new Hyperlink("Get help");
		    infoPage.setOnAction((event) -> {
		    	WebView browser = new WebView();
		    	WebEngine webEngine = browser.getEngine();
		    	webEngine.load("http://www.cs.duke.edu/courses/compsci308/spring15/assign/03_slogo/commands.php");
		    	
		    	Stage stage = new Stage();
		    	setupScene(stage, browser, 1000, 750);   	 
		    }
		    );
		    
		   // title.setTextAlignment(TextAlignment.CENTER); //why does this not work
	   //     VBox.setMargin(infoPage, new Insets(0, 0, 0, 50));
		    sidePane.getChildren().add(infoPage);
		    
		    
		    //customization
		    
		    Text title = new Text("Customization");
		    title.setFont(new Font(13));
		    title.setUnderline(true);
		    title.setTextAlignment(TextAlignment.CENTER); 
		    sidePane.getChildren().add(title);
		    
	 
		    
		    // select turtle image    
		    HBox hbox = new HBox(10);  
		    Text selectTurtle = new Text("Select Turtle");    
		    Button uploadImg = new Button("Upload");
		    uploadImg.setPrefSize(100, 20);
		    uploadImg.setPadding(new Insets(0,0,0,3));
		    uploadImg.setOnAction(e -> uploadTurtleFile(myTurtles.get(0)));

		    hbox.getChildren().addAll(selectTurtle, uploadImg);
		    sidePane.getChildren().add(hbox); 
		    
		    // select pen color	
		    HBox hbox2 = new HBox(10);  
		    
	        Text selectPenColor = new Text(Strings.SELECT_PEN_COLOR);   
		    
	        ColorPicker penColorChoice = new ColorPicker(Color.BLACK);
	        penColorChoice.setOnAction(e -> drawer.changeColor(penColorChoice.getValue()));
	        
		    hbox2.getChildren().addAll(selectPenColor, penColorChoice);
		  //  sidePane.getChildren().addAll(hbox2, strokeColorChoice);
		    sidePane.getChildren().add(hbox2);

		    // select background color
		    HBox hbox3 = new HBox(10);  
		    Text selectBackgroundColor = new Text(Strings.SELECT_BACKGROUND_COLOR);   
		    
		    ColorPicker backgroundChoice = new ColorPicker(Color.WHITE);
	        backgroundChoice.setOnAction(e -> changeBackgroundColor(backgroundChoice.getValue()));
	    
		  //   User can pick color for the stroke
  
		    hbox3.getChildren().addAll(selectBackgroundColor, backgroundChoice);
		    sidePane.getChildren().add(hbox3);
		    
		    
		    // variables pane
		    Text variables = new Text(myResources.getString(Strings.VARIABLES_HEADER)); //is this necessary to use a .properties file AND a strings class?
		    variables.setFont(new Font(15));
		    variables.setUnderline(true);
		    sidePane.getChildren().add(variables); 
		    
		    ObservableList<Variable> variablesList =FXCollections.observableArrayList (
		    	new Variable("var1", 1.5),
		    	new Variable("var2", 2.5)	
		    );
		    variablesList.add(new Variable("Added var2.5", 5));
		    
		    TableView<Variable> variablesTable = new TableView<Variable>();
		    
		    //why can't I add columns to this table??
		    //http://code.makery.ch/java/javafx-8-tutorial-part2/
		    //http://docs.oracle.com/javafx/2/ui_controls/table-view.htm
		    
	        TableColumn<Variable, String> variablesCol = new TableColumn<Variable, String>("Variables");
	        //System.out.println("pref: " + sidePane.getMaxWidth());
	        //variablesCol.setPrefWidth(sidePane.getPrefWidth()/2);
	        TableColumn<Variable, Double> valuesCol = new TableColumn<Variable, Double>("Values");
	        
	        variablesCol.setCellValueFactory(new PropertyValueFactory<Variable,String>("myName"));
	        valuesCol.setCellValueFactory(new PropertyValueFactory<Variable,Double>("myVar"));
	        
	        //is it possible to dynamically set this?
	        variablesCol.setPrefWidth(164);
	        valuesCol.setPrefWidth(164);
	        valuesCol.setEditable(true);
	        
	        variablesCol.setCellFactory(TextFieldTableCell.forTableColumn());
	        variablesCol.setOnEditCommit(
	            new EventHandler<CellEditEvent<Variable, String>>() {
	                @Override
	                public void handle(CellEditEvent<Variable, String> t) {
	                    ((Variable) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).setName(t.getNewValue());
	                }
	            }
	        );

	        
	        
	        //some issue with strings and ints
	        valuesCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Double>(){
	            
	        	@Override public Double fromString(String userInput) {
	                //try{
	        		return Double.valueOf(userInput);
	            	/*}
	            	catch(NumberFormatException e){
	            		System.out.println("Number Format Exception");
	            	}*/
	            }

	            @Override public String toString(Double t) {
	                return t.toString();
	            }
	        }));
	     //   CellValueFactory(.forTableColumn());
	        valuesCol.setOnEditCommit(
	            new EventHandler<CellEditEvent<Variable, Double>>() {
	                @Override
	                public void handle(CellEditEvent<Variable, Double> t) {
	                    ((Variable) t.getTableView().getItems().get(
	                        t.getTablePosition().getRow())
	                        ).setValue(t.getNewValue());
	                }
	            }
	        );
	  
	        variablesTable.getColumns().addAll(variablesCol, valuesCol);
		    variablesTable.setEditable(true);

	        
	        variablesTable.setMaxWidth(Double.MAX_VALUE);
	        variablesTable.setPrefHeight(150);
	        
		    sidePane.getChildren().add(variablesTable);
		    
		    /*When you call Cell.commitEdit(Object) an event is fired to the TableView, which you can observe by adding an EventHandler via TableColumn.setOnEditCommit(javafx.event.EventHandler). Similarly, you can also observe edit events for edit start and edit cancel.*/
		    //how to remove the extra column?
		    
		    //example of how to set new elements to the observablelist
		    variablesList.add(new Variable("Added var3", 3));
		    variablesTable.setItems(variablesList);
		    
		    //user-defined commands
		    Text userCommands = new Text(myResources.getString(Strings.USER_DEFINED_COMMANDS_HEADER));
		    userCommands.setFont(new Font(15));
		    userCommands.setUnderline(true);
		    sidePane.getChildren().add(userCommands);
		    
		    ListView<String> userCommandsList = new ListView<String>();
		    ObservableList<String> commandsItems =FXCollections.observableArrayList (
		    	    "String1", "String2", "String3", "String 4","String1", "String2", "String3", "String 4", "String1", "String2", "String3", "String 4");
		    userCommandsList.setItems(commandsItems);
		    userCommandsList.setMaxWidth(Double.MAX_VALUE);
		    userCommandsList.setPrefHeight(150);
		    sidePane.getChildren().add(userCommandsList);
		    
		    // history pane
		    Text history = new Text(myResources.getString(Strings.HISTORY_HEADER));
		    history.setFont(new Font(15));
		    history.setUnderline(true);
		    sidePane.getChildren().add(history);
		    
		    ListView<String> historyList = new ListView<String>();
		    ObservableList<String> historyItems =FXCollections.observableArrayList (
		    	    "String1", "String2", "String3", "String 4","String1", "String2", "String3", "String 4", "String1", "String2", "String3", "String 4");
		    historyList.setItems(historyItems);
		    historyList.setMaxWidth(Double.MAX_VALUE);
		    historyList.setPrefHeight(150);
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
	
	
	public void updateWorkspace(ArrayList<TurtleCommand> instructions){
		//update the grid
		//updateGrid(command.getLines());
		//VariablesView.updateVars(command.getVariables());
		//variables view will have configuredisplay(), update(), and event handlers
		//updateHistory(command.getStrings());
	    myWorkspace.getChildren().addAll(drawer.draw(myTurtles, instructions));
	}

	public Turtle getTurtleInfo(int index){
	    ImageView temp=myTurtles.get(index);
	    return new Turtle(temp.getX(),temp.getY(),temp.getRotate());
	}
	
   public void updateVariables(Map<String, Double> variableUpdates){
       Iterator<Entry<String, Double>> it = variableUpdates.entrySet().iterator();
       while(it.hasNext()){
           Entry<String, Double> variable = it.next();
           String name = variable.getKey();
           double value = variable.getValue();
           if(variables.get(name)==null){
               // create the UI element to hold this variable
               // then add this element to variables (variables.put(name,UI node));
               // then add this element to the grid
           }else{
               // variables.get(name).setText(value);
           }
       }
    }
	
	private void setupScene(Stage stage, Parent root, int xSize, int ySize) {
		Scene scene = new Scene(root, xSize, ySize);
		stage.setTitle(myResources.getString("Title"));
		stage.setScene(scene);
		scene.setOnKeyPressed(e -> handleKeyInput(e));
		//what happens if you set multiple scenes?
		stage.show();
	}
	
	private void handleKeyInput (KeyEvent e) {
	    KeyCode keyCode = e.getCode();
	    if(keyCode == KeyCode.W){
            ArrayList<TurtleCommand> instructions = new ArrayList<TurtleCommand>();
            instructions.add(new TurtleCommand(0,new Polar(0,10*count),false,false));
            myWorkspace.getLines().getChildren().addAll(drawer.draw(myTurtles, instructions));
	    }
        count++;
    }

    private void changeBackgroundColor(Color color){
		myWorkspace.setBackground(color);
	}
	
	private void uploadTurtleFile(TurtleView turtle){
		File file = displayFileChooser();
		changeTurtleImage(turtle, new Image(file.toURI().toString()));
	}
	
	private void changeTurtleImage(TurtleView turtle, Image img){
		System.out.println(img.toString());
		turtle.setImage(img);
	}
	
	public String updateWorkspace(List<Instruction> instructionList){
		String returnString = null;
		
		for (Instruction instruction : instructionList){
			returnString += updateFromInstruction(instruction) + "\n";
		}
		
		//return value of command or null if there is no return value
		return returnString;
	}
	
	//make update from a single command
	private String updateFromInstruction(Instruction instruction){
		
		
		
		return "return value";
	}
	
	//UPON BUTTON CLICK:
	
	//  File newFile = displayFileChooser();
	
	/* colorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                text.setFill(colorPicker.getValue());               
            }
        });
        
        
        button.setOnAction(new EventHandler<ActionEvent>() {

    @Override
    public void handle(ActionEvent event) {
        String text = textField.getText();                              
    }
});*/
	
	//consider using labels instead of text?
	
	/*public void changeColor(Color c) {
        System.out.println("color" + c.toString());



    }*/
	

}
