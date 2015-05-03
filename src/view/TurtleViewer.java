package view;

import java.util.Map;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class TurtleViewer extends ScrollPane {

    Workspace myWorkspace;
    VBox turtleDisplay;
    CustomizationBar myCustomizationBar;

    public TurtleViewer (Workspace workspace, CustomizationBar customBar) {
        turtleDisplay = new VBox();
        myWorkspace = workspace;
        myCustomizationBar = customBar;
        setContent(turtleDisplay);
        turtleDisplay.setAlignment(Pos.CENTER);
        Map<Integer, TurtleView> map = myWorkspace.getTurtleMap();
    }

    public void updateDisplay () {
        turtleDisplay.getChildren().clear();
        Map<Integer, TurtleView> map = myWorkspace.getTurtleMap();

        for (int key : map.keySet()) {
            HBox turtleBox = new HBox();
            Text turtleKey = new Text(" Key: " + key);
            TurtleView currentTurtle = map.get(key);
            turtleDisplay.setOnMouseClicked(e -> displayChooser(currentTurtle));
            turtleDisplay.getChildren().add(turtleBox);
            turtleBox.getChildren().addAll(currentTurtle.getUniqueImage(), turtleKey);
        }

    }

    private void displayChooser (TurtleView turtle) {
        myCustomizationBar.uploadTurtleFile(turtle);
        updateDisplay();
    }

    /*
     * ObservableMap<Integer, TurtleView> observableMap = FXCollections.observableMap(map);
     * 
     * 
     * observableMap.addListener(new MapChangeListener<Integer, TurtleView>() {
     * 
     * @Override
     * public void onChanged (MapChangeListener.Change<? extends Integer, ? extends TurtleView>
     * change) {
     * updateDisplay();
     * 
     * }
     * });
     */
}
