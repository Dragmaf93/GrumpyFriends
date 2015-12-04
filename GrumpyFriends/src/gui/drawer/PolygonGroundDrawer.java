package gui.drawer;

import java.util.List;

import physic.PhysicalPolygonObject;
import element.Element;
import element.Ground;
import element.ground.LinearGround;
import gui.ImageLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import utils.Point;
import utils.Utils;
import utils.Vector;

public class PolygonGroundDrawer extends AbstractDrawerObject  {

	private ImageLoader imageLoader;
	private static final String TYPE_WORLD = "Planet";
	
	public PolygonGroundDrawer(Group pane, ImageLoader imageLoader) {
		super(pane);
//		this.imageLoader = new ImageLoader();
		this.imageLoader = imageLoader;
	}

	@Override
	public Node getElementToDraw(Element elementToDraw) {
		Ground ground = (Ground) elementToDraw;
		List<Point> points = ground.getPoint();
		
		Polygon shape = new Polygon();
		
		for (Point point : points) {
			shape.getPoints().add(point.x-ground.getX());
			shape.getPoints().add(point.y-ground.getY());
		}
		
		ImageView imageView = null;
		if (ground.getHeight() > ground.getWidth())
			imageView = new ImageView(imageLoader.getImageGrounds(TYPE_WORLD,"Height"));
		else
			imageView = new ImageView(imageLoader.getImageGrounds(TYPE_WORLD,"Width"));
			
		if (imageView != null) {
			imageView.resize(ground.getWidth(), ground.getHeight());
			imageView.setClip(shape);
			imageView.setLayoutX(ground.getX());
			imageView.setLayoutY(ground.getY());
			return imageView;
		}
		return null;
	}

		

}
