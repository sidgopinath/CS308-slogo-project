package view;

import java.io.File;
import java.util.List;
import java.util.Map;

import controller.SLogoController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CustomizationBar extends HBox {
	
	Map<Integer, TurtleView> myTurtles;
	SLogoController myController;
	Drawer myDrawer;
	Workspace myWorkspace;
	Stage myStage;
	
	public CustomizationBar(SLogoController controller, Map<Integer, TurtleView> turtleList, Drawer drawer, Workspace workspace, Stage stage, Dimension2D dimensions){
		// select language
		setPadding(new Insets(0,dimensions.getWidth()/120,0,dimensions.getWidth()/120));
		setSpacing(dimensions.getWidth()/120);
		
		myController = controller;
		myTurtles = turtleList;
		myDrawer = drawer;
		myWorkspace = workspace;
		myStage = stage;
				HBox selectLanguage = new HBox(10);
				Text select = new Text("Select Language");
				ComboBox<String> languageOptions = new ComboBox<String>();
				ObservableList<String> languages = FXCollections.observableArrayList("English",
						"Chinese", "French", "German", "Italian", "Japanese", "Korean",
						"Portugese", "Russian", "Spanish");

				languageOptions.setItems(languages);
				languageOptions.setValue(languages.get(0));

				selectLanguage.getChildren().addAll(select, languageOptions);
				getChildren().add(selectLanguage);

				languageOptions.getSelectionModel().selectedItemProperty()
						.addListener(new ChangeListener<String>() {
							@Override
							public void changed(ObservableValue<? extends String> ov,
									String old_val, String new_val) {
								System.out.println(new_val);
								controller.setLanguage(new_val);

							}
						});

				// select turtle image
				HBox customizeTurtleBox = new HBox(10);
				Text selectTurtle = new Text("Select Turtle");
				Button uploadImg = new Button("Upload");
				uploadImg.setPrefSize(dimensions.getWidth()/12, dimensions.getHeight()/27);
				uploadImg.setPadding(new Insets(0, 0, 0, 3));
				uploadImg.setOnAction(e -> uploadTurtleFile(turtleList.get(0)));

				customizeTurtleBox.getChildren().addAll(selectTurtle, uploadImg);
				getChildren().add(customizeTurtleBox);

				// select pen color
				HBox customizePenBox = new HBox(10);

				Text selectPenColor = new Text(Strings.SELECT_PEN_COLOR);
				ColorPicker penColorChoice = new ColorPicker(Color.BLACK);
				penColorChoice.setOnAction(e -> drawer.changeColor(penColorChoice.getValue()));

				customizePenBox.getChildren().addAll(selectPenColor, penColorChoice);
				// sidePane.getChildren().addAll(hbox2, strokeColorChoice);
				getChildren().add(customizePenBox);

				// select background color
				HBox customizeBackgroundBox = new HBox(10);
				Text selectBackgroundColor = new Text(Strings.SELECT_BACKGROUND_COLOR);

				ColorPicker backgroundChoice = new ColorPicker(Color.WHITE);
				backgroundChoice.setOnAction(e -> changeBackgroundColor(backgroundChoice
						.getValue()));

				// User can pick color for the stroke

				customizeBackgroundBox.getChildren().addAll(selectBackgroundColor,
						backgroundChoice);
				HBox spaceHolder = new HBox();
				spaceHolder.setPrefWidth(dimensions.getWidth()/40);
				
				getChildren().addAll(customizeBackgroundBox,spaceHolder);
				

				// Add turtle button
				Button newTurtleButton = new Button("Add a turtle");
				newTurtleButton.setStyle("-fx-base: #b6e7c9;");
				newTurtleButton.setAlignment(Pos.CENTER_RIGHT);
				newTurtleButton.setOnAction(e -> myWorkspace.addTurtle());

			/*	TextField textbox = new TextField("" + turtleList.size());
				textbox.setEditable(false);
				textbox.setPrefWidth(dimensions.getWidth()/100);*/
				getChildren().addAll(newTurtleButton);		
				

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



}
