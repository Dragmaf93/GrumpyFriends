package gui.worldDrawer;

import element.Element;
import element.ground.LinearGround;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class LinearGroundDrawer implements DrawerObject  {

	@Override
	public Shape getShape(Element elementToDraw) {
		LinearGround ground = (LinearGround) elementToDraw;
		Rectangle rectangle = new Rectangle(
				ground.getX(),
				ground.getY(), 
				ground.getWidth(),
				ground.getHeight());
		return rectangle;
		
	}
}
