package view;

import java.io.File;

import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Parser;

/**
 * Customization for the bar at the top that allows the user to set the visual representations of the IDE
 * @author Callie
 *
 */

public class CustomizationBar extends HBox {

	private Parser myParser;
	private Workspace myWorkspace;
	private Stage myStage;
    private static final String DEFAULT_RESOURCE_PACKAGE = "resources.display/";
    private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "english");

	public CustomizationBar(
			Map<Integer, TurtleView> turtleList, Drawer drawer, Workspace workspace,
			Stage stage, Dimension2D dimensions) {

		myWorkspace = workspace;
		myStage = stage;
		setPadding(new Insets(0, dimensions.getWidth() / 120, 0,dimensions.getWidth() / 120));
		setSpacing(dimensions.getWidth() / 120);
		getChildren().add(configureLanguageOptions());
		selectTurtleImage(dimensions);
		//TODO: set pen color for the active turtle. 
		selectPenColor(drawer);
		selectBackgroundColor(dimensions);
	}
	
	public void setParser(Parser parser){
		myParser = parser;
	}

	private void selectBackgroundColor(Dimension2D dimensions) {
		HBox customizeBackgroundBox = new HBox(10);
		Text selectBackgroundColor = new Text(myResources.getString("SelectBackgroundColor"));
		ColorPicker backgroundChoice = new ColorPicker(Color.WHITE);
		backgroundChoice.setOnAction(e -> changeBackgroundColor(backgroundChoice
				.getValue()));
		customizeBackgroundBox.getChildren().addAll(selectBackgroundColor,
				backgroundChoice);
		HBox spaceHolder = new HBox();
		spaceHolder.setPrefWidth(dimensions.getWidth() / 40);
		getChildren().addAll(customizeBackgroundBox, spaceHolder);
	}

	private void selectPenColor(Drawer drawer) {
		HBox customizePenBox = new HBox(10);
		Text selectPenColor = new Text(myResources.getString("SelectPenColor"));
		ColorPicker penColorChoice = new ColorPicker(Color.BLACK);
		penColorChoice.setOnAction(e -> drawer.changeColor(penColorChoice.getValue()));
		customizePenBox.getChildren().addAll(selectPenColor, penColorChoice);
		getChildren().add(customizePenBox);
	}

	public void uploadTurtleFile(TurtleView turtle) {
		File file = displayFileChooser();
		changeTurtleImage(turtle, new Image(file.toURI().toString()));
	}

	private File displayFileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(myResources.getString("OpenImage"));
		fileChooser.getExtensionFilters()
				.add(new FileChooser.ExtensionFilter(myResources.getString("ImageFiles"), "*.png", "*.jpg",
						"*.gif"));
		return fileChooser.showOpenDialog(myStage);
	}

	private void changeTurtleImage(TurtleView turtle, Image img) {
		turtle.setImage(img);
	}

	private void changeBackgroundColor(Color color) {
		myWorkspace.setBackground(color);
	}

	private HBox configureLanguageOptions() {
		HBox selectLanguage = new HBox(10);
		Text select = new Text(myResources.getString("SelLan"));
		ComboBox<String> languageOptions = new ComboBox<String>();
		ObservableList<String> languages = FXCollections.observableArrayList("English",
				"Chinese", "French", "German", "Italian", "Japanese", "Korean",
				"Portugese", "Russian", "Spanish");
		languageOptions.setItems(languages);
		languageOptions.setValue(languages.get(0));
		selectLanguage.getChildren().addAll(select, languageOptions);
		languageOptions.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> ov,
							String old_val, String new_val) {
						myParser.setLanguage(new_val);
					}
				});
		return selectLanguage;
	}
	
	private void selectTurtleImage(Dimension2D dimensions){
		HBox customizeTurtleBox = new HBox(10);
		Text selectTurtle = new Text(myResources.getString("SelTurtle"));
		Button uploadImg = new Button(myResources.getString("Upload"));
		uploadImg.setPrefSize(dimensions.getWidth() / 12, dimensions.getHeight() / 29);
		uploadImg.setOnAction(e -> uploadTurtleFile(myWorkspace.getActiveTurtle()));
		customizeTurtleBox.getChildren().addAll(selectTurtle, uploadImg);
		getChildren().add(customizeTurtleBox);
	}
}