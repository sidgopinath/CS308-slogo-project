//test class. this will be tested later on to hold SLogoView and other subclasses

package view;

import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainView {
    
    private Dimension2D myDimensions;
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources.display/";
    protected ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "english");
	private Stage myStage;
    private TabPane myTabPane = new TabPane();
    private SceneSetter mySceneSetter = new SceneSetter();
    private int myTabCount=1;

	// public static final String DEFAULT_RESOURCE_DISPLAY_PACKAGE =
	// "resources.display/";

	public MainView(Stage s) {
		myTabPane = new TabPane();
        GridPane grid = new GridPane();
		myStage = s;      
		myStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                System.out.println(myResources.getString("Closing"));
            }
        });
		myStage.setResizable(false);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        myDimensions = new Dimension2D(bounds.getWidth(), bounds.getHeight());
        SLogoView mySlogoView = new SLogoView(s,myDimensions);
		Tab tab = new Tab();
		tab.setText("SLogoView 1");
	    tab.setContent(mySlogoView.configureUI());
	    myTabPane.getTabs().add(tab);
	    RowConstraints row0 = new RowConstraints();
	    row0.setPercentHeight(4);
	    RowConstraints row1 = new RowConstraints();
	    row1.setPercentHeight(96);
	    grid.getRowConstraints().add(row0);
	    grid.getRowConstraints().add(row1);
	    grid.add(configureTopMenu(),0,0);
        grid.add(myTabPane,0,1);
        mySceneSetter.setupScene(myStage, grid, myDimensions.getWidth(), myDimensions.getHeight(), myResources);
	}

    private MenuBar configureTopMenu() {
        Menu file = new Menu(myResources.getString("File"));
        Menu info = new Menu(myResources.getString("Info"));
        MenuItem help = new MenuItem(myResources.getString("Help"));
        MenuItem work = new MenuItem(myResources.getString("NewWork"));

        // perhaps change these expressions into lambdas?
        help.setOnAction(e -> displayPage("/src/resources/help.html"));
        work.setOnAction(e -> newTab());
        MenuItem exit = new MenuItem(myResources.getString("Exit"));
        file.getItems().addAll(work);
        file.getItems().addAll(exit);
        info.getItems().addAll(help);

        exit.setOnAction(e -> Platform.exit());

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(file, info);
        return menuBar;
    }
    
    private void newTab () {
        SLogoView mySlogoView = new SLogoView(myStage,myDimensions);
        myTabCount++;
        String tabTitle = "SLogoView "+myTabCount;
        Tab tab = new Tab();
        tab.setText(tabTitle);
        tab.setContent(mySlogoView.configureUI());
        myTabPane.getTabs().add(tab);
    }

    private void displayPage(String loc) {
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        webEngine.load("file://" + System.getProperty("user.dir") + loc);

        Stage stage = new Stage();
        mySceneSetter.setupScene(stage, browser, 1000, 750, myResources);
    }

}
