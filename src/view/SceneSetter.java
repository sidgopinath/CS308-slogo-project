package view;

import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneSetter {
    
    public void setupScene(Stage stage, Parent root, double xSize, double ySize, ResourceBundle myResources) {
        Scene scene = new Scene(root, xSize, ySize);
        stage.setTitle(myResources.getString("Title"));
        stage.setScene(scene);
        // scene.setOnKeyPressed(e -> handleKeyInput(e));
        stage.show();
    }
    
}
