package gui.drawer;

import element.Element;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public abstract class AbstractDrawerObject implements DrawerObject{

	protected Group pane;
	
	public AbstractDrawerObject(Group pane){
		this.pane=pane;
	}
	
	@Override
	public void draw(Element elementToDraw) {
		Node e = getElementToDraw(elementToDraw);
		pane.getChildren().add(e);
	}
}
