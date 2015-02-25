//test class. this will be tested later on to hold SLogoView and other subclasses

package view;

import java.util.ResourceBundle;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainView {
	
	private Stage myStage;
	private Scene myScene;
	private GridPane myRoot;

    
    //public static final String DEFAULT_RESOURCE_DISPLAY_PACKAGE = "resources.display/";
    
	public MainView(Stage s) {
		myRoot = new GridPane();
		myStage = s;
	//	myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_DISPLAY_PACKAGE + "english");
	
		
	//	configureUI();
	//	setupGameScene();

	}
	


}
