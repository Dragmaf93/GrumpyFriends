package gui.drawer;

import element.Element;
import element.ground.LinearGround;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class LinearGroundDrawer extends AbstractDrawerObject  {

	public LinearGroundDrawer(Pane pane) {
		super(pane);
	}

	@Override
	public Node getElementToDraw(Element elementToDraw) {
		LinearGround ground = (LinearGround) elementToDraw;
		Rectangle rectangle = new Rectangle(
				ground.getX(),
				ground.getY(), 
				ground.getWidth(),
				ground.getHeight());
		return rectangle;
	}

		

}
