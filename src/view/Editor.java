package view;

import java.lang.reflect.InvocationTargetException;

import controller.SLogoController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

public class Editor extends HBox {
	
	private Button runButton;
	private TextArea textEditor;
	private SLogoController myController;
	
	public Editor(SLogoController controller){
		//HBox bottomRow = new HBox();
		myController = controller;
		setPadding(new Insets(15));
		setSpacing(15);

		// text area
		textEditor = new TextArea();
		textEditor.setMaxHeight(Double.MAX_VALUE);
		textEditor.setPrefSize(750, 120); // this should be dynamically
											// alterable?
		getChildren().add(textEditor);

		// run button
		runButton = new Button("Run");
		// runButton.setPrefSize(100, 120);
		runButton.setMaxWidth(Double.MAX_VALUE);
		runButton.setMaxHeight(Double.MAX_VALUE);
		// runButton.setPadding(new Insets(0,0,0,3));
		getChildren().add(runButton);
		runButton.setOnMouseClicked(e -> parse());
	}
	
	private void parse(){
		try {
			myController.parseInput(textEditor.getText());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*public Button getRunButton(){
		return runButton;
	}*/
	
	
	
	

}
