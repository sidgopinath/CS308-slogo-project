package view;

import java.util.ResourceBundle;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public abstract class PropertiesView {
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources.display/";
    private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "english");
	// protected class PropertiesView(Region parent);

	protected void setTitleText(String string) {
		Text title = new Text(myResources.getString("Cust"));
		title.setFont(new Font(13));
		title.setUnderline(true);
		title.setTextAlignment(TextAlignment.CENTER);
		// getChildren().add(title);

	}

	// protected abstract

	// update();
}
