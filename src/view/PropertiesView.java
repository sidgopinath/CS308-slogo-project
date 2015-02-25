package view;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public abstract class PropertiesView {

	protected void setTitleText(String string){
		Text title = new Text("Customization");
		title.setFont(new Font(13));
		title.setUnderline(true);
		title.setTextAlignment(TextAlignment.CENTER);
		getChildren().add(title);
	}
	
	protected abstract 
	
	
	//update();
}
