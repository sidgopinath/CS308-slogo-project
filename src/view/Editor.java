package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

public class Editor extends HBox {
	
	public Editor(){
		//HBox bottomRow = new HBox();
		setPadding(new Insets(15));
		setSpacing(15);

		// text area
		TextArea textEditor = new TextArea();
		textEditor.setMaxHeight(Double.MAX_VALUE);
		textEditor.setPrefSize(750, 120); // this should be dynamically
											// alterable?
		getChildren().add(textEditor);

		// run button
		Button runButton = new Button("Run");
		// runButton.setPrefSize(100, 120);
		runButton.setMaxWidth(Double.MAX_VALUE);
		runButton.setMaxHeight(Double.MAX_VALUE);
		// runButton.setPadding(new Insets(0,0,0,3));
		getChildren().add(runButton);
	}

}
