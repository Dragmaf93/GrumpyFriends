package gui.worldDrawer;

import element.Element;
import javafx.scene.shape.Shape;

public interface DrawerObject {

	abstract Shape getShape(Element elementToDraw);
}
