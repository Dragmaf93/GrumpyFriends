package gui.drawer;

import java.util.List;

import utils.Point;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public abstract class AbstractDrawerObject implements DrawerObject{

	protected Group pane;
	
	public AbstractDrawerObject(Group pane){
		this.pane=pane;
	}
	
	@Override
	public void draw(List<Point> elementToDraw) {
		Node e = getElementToDraw(elementToDraw);
		pane.getChildren().add(e);
	}
}
