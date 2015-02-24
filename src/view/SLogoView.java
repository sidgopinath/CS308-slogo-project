package view;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import controller.SLogoController;
import resources.Strings;
import model.turtle.Turtle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.instructions.Instruction;

public class SLogoView {
	private Map<Integer, ImageView> turtles;
	private Stage myStage;
	private Scene myScene;
	private GridPane myRoot;
    private ResourceBundle myResources;
    private Rectangle myBackground;
    private Map<String,Node> variables;
    private Drawer drawer = new Drawer();
    private StackPane myWorkspace;
    private SLogoController myController = new SLogoController();
    private List<TurtleView> myTurtles = new ArrayList<TurtleView>();
    
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources.display/"; //can this be put somewhere else? public variable in a different class?
	public static final int GRID_WIDTH = 800;
	public static final int GRID_HEIGHT = 550;
	//adjusts anchorpane coordinates to set 0,0 as the center of the gridsets center point at the
	public static final double X_ADJUSTMENT = GRID_WIDTH / 2;  
	public static final double Y_ADJUSTMENT = GRID_HEIGHT / 2;  

	public SLogoView(Stage s) {
		 
		
		myStage = s;
		
		//potentially make this into an individual class
		//create root node
		myRoot = new GridPane();
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "english");
		
		configureUI();
		setupScene();
        System.out.println(myRoot.getColumnConstraints());
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
        
        myWorkspace = new StackPane();
        
        
       // Group display = new Group(new Rectangle(0, 0, GRID_SIZE, GRID_SIZE));
       // display.setAlignment(Pos.CENTER);
     //   myRoot.add(display,0,1);
        
        myWorkspace.setPadding(new Insets(15));
        
//        anchorPane.setStyle("-fx-background-color: black;");
        //problem with the above line is that it will set the anchorpane black all the way to the end fo the stage
        
        //is there a way to dynamically set grid size
        myBackground = new Rectangle(GRID_WIDTH,GRID_HEIGHT);
        myBackground.setFill(Color.WHITE);
        
        System.out.println(myRoot.getColumnConstraints());
        AnchorPane.setTopAnchor(myBackground, 0.0);
        AnchorPane.setLeftAnchor(myBackground, 0.0);
        
   

        TurtleView turtle = new TurtleView(new Image(Strings.DEFAULT_TURTLE_IMG));

        
        //why does the turtle end up so far down?
    
        //allow turtle to be initialized to this and then set this way somehow
       // AnchorPane.setTopAnchor(turtle, Y_ADJUSTMENT);
       // AnchorPane.setLeftAnchor(turtle,X_ADJUSTMENT);
        myWorkspace.setAlignment(turtle, Pos.CENTER);
        myWorkspace.getChildren().addAll(myBackground, turtle);
        myTurtles.add(turtle);
        System.out.println(turtle.getX());
        System.out.println(turtle.getY());
        //add lines to a group
        
        
        myRoot.add(myWorkspace, 0, 1);
        
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
		sidePane.setPadding(new Insets(0,15,0,15));
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
	    hbox2.getChildren().addAll(selectPenColor, penColorChoice);
	    sidePane.getChildren().add(hbox2);

	    // select background color
	    HBox hbox3 = new HBox(10);  
	    Text selectBackgroundColor = new Text(Strings.SELECT_BACKGROUND_COLOR);   
	    ColorPicker backgroundChoice = new ColorPicker(Color.WHITE);
        backgroundChoice.setOnAction(e -> changeBackgroundColor(backgroundChoice.getValue()));

	    
	    // User can pick color for the stroke
        ColorPicker strokeColorChoice = new ColorPicker(Color.BLACK);
        strokeColorChoice.setOnAction(t -> drawer.changeColor(strokeColorChoice.getValue()));
        // put the strokeColorChoice somewhere in the GUI
        
	    hbox3.getChildren().addAll(selectBackgroundColor, backgroundChoice);
	    sidePane.getChildren().add(hbox3);
	    
	    // variables pane
	    Text variables = new Text(myResources.getString(Strings.VARIABLES_HEADER)); //is this necessary to use a .properties file AND a strings class?
	    variables.setFont(new Font(15));
	    variables.setUnderline(true);
	    sidePane.getChildren().add(variables);
	    
	    ListView<String> list = new ListView<String>();
	    ObservableList<String> items =FXCollections.observableArrayList (
	    	    "String1", "String2");
	    list.setItems(items);
	    list.setMaxWidth(Double.MAX_VALUE);
	    list.setPrefHeight(225);
	    
	    sidePane.getChildren().add(list);
	    
	    
	    //example of how to set new elements to the observablelist
	    items.add("Added String");
	    list.setItems(items);
	    
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
	    historyList.setPrefHeight(225);
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
	
	
	public void updateWorkspace(ArrayList<Instruction> instructions){
		//update the grid
		//updateGrid(command.getLines());
		//VariablesView.updateVars(command.getVariables());
		//variables view will have configuredisplay(), update(), and event handlers
		//updateHistory(command.getStrings());
	    myWorkspace.getChildren().addAll(drawer.draw(turtles, instructions));
	}
	
	public void changeStrokeColor(Color c){
	    drawer.changeColor(c);
	}
	
	public Turtle getTurtleInfo(int index){
	    ImageView temp=turtles.get(index);
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
	
	private void setupScene() {
		myScene = new Scene(myRoot, 1200, 750);
		myStage.setTitle(myResources.getString("Title"));
		myStage.setScene(myScene);
		//what happens if you set multiple scenes?
		myStage.show();
	}
	
	private void changeBackgroundColor(Color color){
		myBackground.setFill(color);
	}
	
	private void uploadTurtleFile(TurtleView turtle){
		File file = displayFileChooser();
		changeTurtleImage(turtle, new Image(file.toURI().toString()));
	}
	
	private void changeTurtleImage(TurtleView turtle, Image img){
		System.out.println(img.toString());
		turtle.setImage(img);
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

}
