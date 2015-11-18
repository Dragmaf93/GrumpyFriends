package gui.drawer;

import java.util.List;


import element.Element;
import element.Ground;
import element.ground.LinearGround;
import element.ground.PhysicalPolygonObject;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Polygon;
import utils.Point;
import utils.Utils;
import utils.Vector;

public class PolygonGroundDrawer extends AbstractDrawerObject  {

	public PolygonGroundDrawer(Group pane) {
		super(pane);
	}

	@Override
	public Node getElementToDraw(Element elementToDraw) {
		Ground ground = (Ground) elementToDraw;
		List<Point> points = ground.getPoint();
		
		
		System.out.println(points);
		
		Polygon shape = new Polygon();
		for (Point point : points) {
			System.out.println("PUNTO : ");
			System.out.println(ground.getPhysicObject().getX() + " "+ ground.getPhysicObject().getY());
			System.out.println(point.x +"  "+point.y);
			shape.getPoints().add( point.x);
			shape.getPoints().add(point.y);
		}
	
		return shape;
	}

		

}
