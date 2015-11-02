package gui.drawer;

import element.Element;
import javafx.scene.Node;

public interface DrawerObject {

	abstract public void draw(Element elementToDraw);
	abstract public Node getElementToDraw(Element elementToDraw);
}
