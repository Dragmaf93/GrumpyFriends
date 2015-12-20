package gui.drawer;

import java.util.Collections;
import java.util.List;

import com.sun.xml.internal.ws.dump.LoggingDumpTube.Position;

import physic.PhysicalPolygonObject;
import element.Ground;
import element.ground.LinearGround;
import gui.ImageLoader;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import utils.Point;
import utils.Utils;
import utils.Vector;

public class PolygonGroundDrawer extends AbstractDrawerObject {

	private ImageLoader imageLoader;
	private final static double VALUE_OF_UNDRAWABLE_INCLINE = 1.5;
	// private static final String TYPE_WORLD = "Planet";
	static private Image grassimage = new Image(
			"file:image/World/Planet/grass1.png");
	static private Image grassimage1 = new Image(
			"file:image/World/Planet/grass2.png");

	public PolygonGroundDrawer(Group pane, ImageLoader imageLoader) {
		super(pane);
		this.imageLoader = imageLoader;
		// this.imageLoader = imageLoader;

	}

	@Override
	public Node getElementToDraw(List<Point> elementToDraw) {
		// Ground ground = (Ground) elementToDraw;
		List<Point> points = elementToDraw;
		double positionX = Double.MAX_VALUE;
		double positionY = Double.MAX_VALUE;
		Group root = new Group();
		Polygon shape = new Polygon();

		for (Point point : points) {
			if (point.x < positionX)
				positionX = point.x;
			if (point.y < positionY)
				positionY = point.y;

			shape.getPoints().add(point.x);
			shape.getPoints().add(point.y);

			// pane.getChildren().add(new Circle(point.x,point.y,5,Color.RED));
		}

		ImageView imageView = new ImageView(imageLoader.getImageGrounds());
		imageView.setX(positionX);
		imageView.setY(positionY);

		imageView.resize(shape.getLayoutBounds().getWidth(), shape
				.getLayoutBounds().getHeight());
		imageView.setClip(shape);
		root.getChildren().add(imageView);
		addGrass(shape, root);
		return root;
		// return shape;
	}

	private void addGrass(Polygon polygon, Group root) {

		ObservableList<Double> points = polygon.getPoints();
		double widthGround = grassimage1.getWidth();

		for (int i = 0; i < points.size(); i += 2) {
			double pX1 = points.get(i);
			double pX2 = points.get((i + 2) % points.size());

			double pY1 = points.get((i + 1) % points.size());
			double pY2 = points.get((i + 3) % points.size());

			if (pX1 < pX2) {

				double m = (pY2 - pY1) / (pX2 - pX1);
				double distance = Math.sqrt(((pX2 - pX1) * (pX2 - pX1))
						+ ((pY2 - pY1) * (pY2 - pY1)));
				double angle = Math.toDegrees(Math.atan(m));
				if (distance >= widthGround) {

					if (m > -VALUE_OF_UNDRAWABLE_INCLINE
							&& m < VALUE_OF_UNDRAWABLE_INCLINE) {
						Group grass = new Group();
						for (double x = 0; x < distance; x += widthGround) {

							ImageView g = new ImageView(grassimage1);
							g.setX(pX1 + x-10);
							g.setY(pY1-10);
							grass.getChildren().add(g);

							// Rectangle rectangle =
							// new Rectangle(x, pY1,widthGround, 3);
							// rectangle.setFill(Color.GREEN);
							// grass.getChildren().add(rectangle);
						}

						Rotate rotate = new Rotate();
						rotate.setAxis(Rotate.Z_AXIS);
						rotate.setPivotX(pX1);
						rotate.setPivotY(pY1);
						rotate.setAngle(angle);
						grass.getTransforms().add(rotate);

						// Line line = new Line(pX1, pY1, pX2, pY2);
						// line.setStroke(Color.GREEN);
						// line.setStrokeWidth(5);

						root.getChildren().add(grass);
					}
				}
			}

		}

	}

}
