package view;

import java.lang.reflect.InvocationTargetException;

import controller.SLogoController;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

public class Editor extends HBox {

	private Button runButton;
	private TextArea textEditor;
	private SLogoController myController;
	private SideBar mySidebar;

	public Editor(SLogoController controller, SideBar sidebar, Dimension2D dimensions) {
		mySidebar = sidebar;
		myController = controller;
		setPadding(new Insets(dimensions.getWidth() / 89, 0, dimensions.getWidth() / 85,
				dimensions.getWidth() / 89));
		setSpacing(15);

		createTextEditor(dimensions);
		createRunButton();
	}

	private void parse() {
		String userText = textEditor.getText();
		textEditor.clear();
		System.out.println(userText);
		mySidebar.setHistory(userText);
		
		//TODO: remove catch
		try {
			myController.parseInput(userText);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createTextEditor(Dimension2D dimensions){
		textEditor = new TextArea();
		textEditor.setPrefSize(dimensions.getWidth() * .685,
				dimensions.getHeight() * .095); 
		getChildren().add(textEditor);
	}
	
	private void createRunButton(){
		runButton = new Button("Run");
		runButton.setMaxWidth(Double.MAX_VALUE);
		runButton.setMaxHeight(Double.MAX_VALUE);
		getChildren().add(runButton);
		runButton.setOnMouseClicked(e -> parse());
	}
}
