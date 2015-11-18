package gui.drawer;

import java.util.List;

import element.Element;
import element.Ground;
import element.ground.LinearGround;
import element.ground.PhysicalPolygonObject;
import gui.ImageLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import utils.Point;
import utils.Utils;
import utils.Vector;

public class PolygonGroundDrawer extends AbstractDrawerObject  {

	private ImageLoader imageLoader;
	private static final String TYPE_WORLD = "Planet";

	public PolygonGroundDrawer(Group pane) {
		super(pane);
		imageLoader = new ImageLoader();
	}

	@Override
	public Node getElementToDraw(Element elementToDraw) {
		Ground ground = (Ground) elementToDraw;
		List<Point> points = ground.getPoint();
		
		Polygon shape = new Polygon();
		
		ImagePattern imagePattern = imageLoader.getImage(TYPE_WORLD);
		
		for (Point point : points) {
			shape.getPoints().add( point.x);
			shape.getPoints().add(point.y);
		}
	
		shape.setFill(imagePattern);
		return shape;
	}

		

}
