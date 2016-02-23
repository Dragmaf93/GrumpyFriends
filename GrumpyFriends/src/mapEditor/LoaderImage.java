package mapEditor;

import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Point2D;

public class LoaderImage {
	Map<String, SquarePolygon> images;
	Curve quadCurve;
	MapEditor mapEditor;
	int index = 0;

	public LoaderImage(MapEditor mapEditor) {
		images = new HashMap<String, SquarePolygon>();

		this.mapEditor = mapEditor;
	}

	public Map<String, SquarePolygon> getImages() {
		return images;
	}

	public void load() {
		images.put("square", new Square(mapEditor, "linearGround", new Point2D(
				0.0, 0.0), new Point2D(0.0, 60.0), new Point2D(60.0, 60.0),
				new Point2D(60.0, 0.0)));
		images.put("triangle", new Triangle(mapEditor, "inclinedGround",
				new Point2D(0.0, 0.0), new Point2D(0.0, 60.0), new Point2D(
						60.0, 60.0)));
		images.put("genericPolygon", new GenericPolygon(mapEditor,
				"genericGround", new Point2D(30.0, 0.0),
				new Point2D(0.0, 24.0), new Point2D(0.0, 60.0), new Point2D(
						60.0, 60.0), new Point2D(60.0, 24.0)));
		// images.put("curve", new Curve(mapEditor, "curveGround", new
		// Point2D(0.0, 0.0), new Point2D(60.0, 60.0)));
		quadCurve = new Curve(mapEditor, "curveGround", new Point2D(0.0, 0.0),
				new Point2D(60.0, 60.0));
	}

	public SquarePolygon copyImage(String name) {
		SquarePolygon imageCopy = null;
		imageCopy = (SquarePolygon) images.get(name).clone();

		return imageCopy;
	}

	public Curve getQuadCurve() {
		return quadCurve;
	}
}
