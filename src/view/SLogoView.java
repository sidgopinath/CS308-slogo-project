package view;

import java.util.ResourceBundle;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SLogoView {
	
	private Stage myStage;
	private Scene myScene;
	private GridPane myRoot;
    private ResourceBundle myResources;
    private Group myDefaultGrid;
    
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources.display/";
	public static final int GRID_SIZE = 100;

    
	public SLogoView(Stage s) {
		myRoot = new GridPane();
		myStage = s;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "english");
		myDefaultGrid = new Group(new Rectangle(0, 0, GRID_SIZE, GRID_SIZE));
		
		configureUI();
		setupGameScene();

	}
	
	private void configureUI() {
		myRoot.setAlignment(Pos.CENTER);
		myRoot.setHgap(10);
		myRoot.setVgap(10);
		myRoot.add(myDefaultGrid, 1, 1);
		myRoot.getColumnConstraints().add(new ColumnConstraints(500));
	}
	
	private void setupGameScene() {
		myScene = new Scene(myRoot);
		myStage.setTitle(myResources.getString("Title"));
		myStage.setScene(myScene);
		myStage.show();
	}

}
