package view;

import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import model.Parser;

public class Editor extends HBox {

	private Button runButton;
	private TextArea textEditor;
	private Parser myParser;
	private SideBar mySidebar;

	public Editor(Parser parser, SideBar sidebar, Dimension2D dimensions) {
		mySidebar = sidebar;
		myParser = parser;
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
			myParser.parseAndExecute(userText);
		} catch (
				IllegalArgumentException | SecurityException e) {
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
