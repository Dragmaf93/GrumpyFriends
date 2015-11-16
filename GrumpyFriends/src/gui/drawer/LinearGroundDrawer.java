package gui.drawer;

import java.util.List;


import element.Element;
import element.ground.LinearGround;
import element.ground.PhysicalPolygonObject;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Polygon;
import utils.Utils;
import utils.Vector;

public class LinearGroundDrawer extends AbstractDrawerObject  {

	public LinearGroundDrawer(Group pane) {
		super(pane);
	}

	@Override
	public Node getElementToDraw(Element elementToDraw) {
		LinearGround ground = (LinearGround) elementToDraw;
		List<Vector> points = ((PhysicalPolygonObject) ground.getPhysicObject()).getPoints();
		
		
		
		Polygon shape = new Polygon();
		for (Vector vector : points) {
			System.out.println(vector.x +"  "+vector.y);
			shape.getPoints().add((double) Utils.xFromJbox2dToJavaFx(vector.x));
			shape.getPoints().add((double) Utils.yFromJbox2dToJavaFx(vector.y));
		}
	
		return shape;
	}

		

}
