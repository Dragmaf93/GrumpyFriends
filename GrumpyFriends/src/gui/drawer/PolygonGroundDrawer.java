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
import utils.Point;
import utils.Utils;
import utils.Vector;

public class PolygonGroundDrawer extends AbstractDrawerObject {

	private ImageLoader imageLoader;
	private final static double VALUE_OF_UNDRAWABLE_INCLINE = 1.5;
	// private static final String TYPE_WORLD = "Planet";
	private Image image;

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

		addGrass(shape);
		return imageView;
//		 return shape;
	}

	private void addGrass(Polygon polygon) {

		ObservableList<Double> points = polygon.getPoints();

		for (int i = 0; i < points.size(); i += 2) {
			double pX1 = points.get(i);
			double pX2 = points.get((i + 2) % points.size());

			double pY1 = points.get((i + 1) % points.size());
			double pY2 = points.get((i + 3) % points.size());

			if (pX1 < pX2) {
				double m = (pY2 - pY1) / (pX2 - pX1);
				if (m > -VALUE_OF_UNDRAWABLE_INCLINE
						&& m < VALUE_OF_UNDRAWABLE_INCLINE) {
					Line line = new Line(pX1, pY1, pX2, pY2);
					line.setStroke(Color.GREEN);
					line.setStrokeWidth(5);

					pane.getChildren().add(line);
				}
			}

		}

	}

}
