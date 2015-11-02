package gui.drawer;

import element.Element;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public abstract class AbstractDrawerObject implements DrawerObject{

	protected Pane pane;
	
	public AbstractDrawerObject(Pane pane){
		this.pane=pane;
	}
	
	@Override
	public void draw(Element elementToDraw) {
		Node e = getElementToDraw(elementToDraw);
		pane.getChildren().add(e);
	}
}
