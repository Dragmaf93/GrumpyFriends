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
//		shape.getPoints().addAll(new Double[]{30.0, 0.0,
//				0.0, 24.0,  0.0, 60.0,   60.0, 60.0,   60.0, 24.0
//		});
		
		for (Point point : points) {
			shape.getPoints().add( point.x-ground.getX());
			shape.getPoints().add(point.y-ground.getY());
		}
//	
		
//		ImageView imageView = new ImageView(imageLoader.getImageGrounds("Planet"));
//		ImageView imageView = new ImageView(new Image("file:image/ground/groundPlanet.png"));
		
		ImagePattern imageP = new ImagePattern(imageLoader.getImageGrounds("Planet"));
//		shape.setFill(imageP);
		ImageView imageView = new ImageView(imageP.getImage());
		imageView.resize(ground.getWidth(), ground.getHeight());
		imageView.setClip(shape);
//		System.out.println(this.getClass()+"         "+ ground.getWidth()+"    "+ground.getHeight());
//		imageView.setFitHeight(ground.getHeight());
//		imageView.setFitWidth(ground.getWidth());
		imageView.setLayoutX(ground.getX());
		imageView.setLayoutY(ground.getY());
//		shape.setFill(imagePattern);
		return imageView;
//		return shape;
	}

		

}
