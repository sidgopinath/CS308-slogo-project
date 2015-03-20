// This entire file is part of my masterpiece.
// CALLIE MAO

package view;

import java.text.DecimalFormat;

import model.ExecutionEnvironment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

public class TurtlePropertiesView extends SideTableView{

	public static final String IDValue = "ID";
	public static final String XPos = "X-Position";
	public static final String YPos = "Y-Position";
	public static final String Heading = "Heading";
	public static final String PenPos = "Pen Position";
	public static final String TurImg = "Turtle Image";
	
	public TurtlePropertiesView(String s, ObservableList<Node> children, String leftCol,
			String rightCol, String leftVal, String rightVal) {
		super(s, children, leftCol, rightCol, leftVal, rightVal);
	}
	
	protected void update(int ID, Workspace workspace) {
		DecimalFormat decimalFormat = new DecimalFormat("#.#");
		TurtleView updatedTurtle = workspace.getTurtleMap().get(ID);
		ObservableList<Property> turtlePropertiesList = FXCollections
				.observableArrayList(
						new Property(IDValue, String
								.valueOf(updatedTurtle.getID())),
						new Property(XPos, String
								.valueOf(decimalFormat.format(updatedTurtle
										.getTranslateX()))),
						new Property(YPos, String
								.valueOf(updatedTurtle.getYCoord())),
						new Property(Heading, String
								.valueOf(updatedTurtle.getHeading())),
						new Property(PenPos, updatedTurtle
								.getPenPosition()),
						new Property(TurImg, updatedTurtle
								.isShowing()));
		getTable().setItems(turtlePropertiesList);
	}

}
