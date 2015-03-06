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

	// ^^ why do i have so many of these here that are passed back and forth?

	public Editor(SLogoController controller, SideBar sidebar, Dimension2D dimensions) {
		// HBox bottomRow = new HBox();
		mySidebar = sidebar;
		myController = controller;
		setPadding(new Insets(dimensions.getWidth() / 89, 0, dimensions.getWidth() / 85,
				dimensions.getWidth() / 89));
		setSpacing(15);

		// text area
		textEditor = new TextArea();
		// textEditor.setMaxHeight(Double.MAX_VALUE);
		// textEditor.setMaxWidth(dimensions.getWidth());
		textEditor.setPrefSize(dimensions.getWidth() * .685,
				dimensions.getHeight() * .095); // this should be dynamically
		// alterable?

		// textEditor.clear();
		// TODO: fix the textEditor clear. It does not udate directly to the
		// group
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

	private void parse() {
		String userText = textEditor.getText();
		textEditor.clear();
		System.out.println(userText);
		mySidebar.setHistory(userText);
		try {
			myController.parseInput(userText);

			// getChildren().add(textEditor);

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

	/*
	 * public Button getRunButton(){ return runButton; }
	 */

}
