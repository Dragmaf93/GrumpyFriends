package gui.drawer;

import java.util.List;

import utils.Point;
import javafx.scene.Node;

public interface DrawerObject {

	abstract public void draw(List<Point> elementToDraw);
	abstract public Node getElementToDraw(List<Point> elementToDraw);
}
