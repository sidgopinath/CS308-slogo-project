package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Dimension2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

/**
 * Main view class containing the menupane and tabpane, which do not change regardless of the number of tabs/scenes added
 * @author Callie, Mengchao
 *
 */

public class MainView {
    
    private Dimension2D myDimensions;
    private static final String DEFAULT_RESOURCE_PACKAGE = "resources.display/";
    private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "english");
	private Stage myStage;
    private TabPane myTabPane = new TabPane();
    private SceneSetter mySceneSetter = new SceneSetter();
    private int myTabCount;
    private SingleSelectionModel<Tab> selectionModel;
    BufferedImage bufferedImage = new BufferedImage(550, 400, BufferedImage.TYPE_INT_ARGB);

	public MainView(Stage s) {
		myTabCount = 1;
		myTabPane = new TabPane();
        GridPane grid = new GridPane();
		initStage(s);
        initScreen();
        SLogoView sLogoView = new SLogoView(s,myDimensions);
        initTabs(sLogoView);
	    initRowConstraints(grid);
        mySceneSetter.setupScene(myStage, grid, myDimensions.getWidth(), myDimensions.getHeight(), myResources);
        selectionModel = myTabPane.getSelectionModel();
	}

	private void initTabs(SLogoView sLogoView) {
		Tab tab = new Tab();
		tab.setText("SLogoView 1");
	    tab.setContent(sLogoView.configureUI());
	    myTabPane.getTabs().add(tab);
	}

	private void initScreen() {
		Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        myDimensions = new Dimension2D(bounds.getWidth(), bounds.getHeight());
	}

	private void initStage(Stage s) {
		myStage = s;      
		myStage.setResizable(false);
	}

	private void initRowConstraints(GridPane grid) {
		RowConstraints row0 = new RowConstraints();
	    row0.setPercentHeight(4);
	    RowConstraints row1 = new RowConstraints();
	    row1.setPercentHeight(96);
	    grid.getRowConstraints().add(row0);
	    grid.getRowConstraints().add(row1);
	    grid.add(configureTopMenu(),0,0);
        grid.add(myTabPane,0,1);
	}

    private MenuBar configureTopMenu() {
        Menu file = configureFileMenu();
        Menu info = configureInfoMenu();
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(file, info);
        return menuBar;
    }

	private Menu configureInfoMenu() {
		Menu info = new Menu(myResources.getString("Info"));
		MenuItem help = new MenuItem(myResources.getString("Help"));
        info.getItems().addAll(help);
        help.setOnAction(e -> displayPage("/src/resources/help.html"));
        return info;
	}
	
	private Menu configureFileMenu() {
		Menu file = new Menu(myResources.getString("File"));
		MenuItem work = new MenuItem(myResources.getString("NewWork"));
		work.setOnAction(e -> newTab());
		file.getItems().addAll(work);
		MenuItem exit = new MenuItem(myResources.getString("Exit"));
        MenuItem snap = new MenuItem(myResources.getString("Snap"));
		exit.setOnAction(e -> Platform.exit());
		snap.setOnAction(e -> saveImage());
		file.getItems().addAll(exit);
        file.getItems().addAll(snap);
		return file;
	}

    private void saveImage() {
        GridPane content = (GridPane) myTabPane.getTabs().get(selectionModel.getSelectedIndex()).getContent();
        WritableImage image = content.snapshot(new SnapshotParameters(), null);

        // TODO: probably use a file chooser here
        File file = new File("snapshot.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }
      }
    private void newTab () {
        SLogoView sLogoView = new SLogoView(myStage,myDimensions);
        myTabCount++;
        String tabTitle = "SLogoView " + (myTabCount);
        Tab tab = new Tab();
        tab.setText(tabTitle);
        tab.setContent(sLogoView.configureUI());
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
