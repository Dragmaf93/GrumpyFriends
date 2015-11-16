package mapEditor;

import java.util.HashMap;

import javafx.geometry.Point2D;
import javafx.scene.shape.QuadCurve;

public class LoaderImage 
{
	HashMap<String, PolygonObject> images;
	Curve quadCurve;
	MapEditor mapEditor;
	int index = 0;
	
	public LoaderImage(MapEditor mapEditor) 
	{
		images = new HashMap<String, PolygonObject>();
		
		this.mapEditor = mapEditor;
	}
	
	public HashMap<String, PolygonObject> getImages()
	{
		return images;
	}
	
	public void load()
	{
		images.put("square",new Square(mapEditor,"linearGround",new Point2D(0.0, 0.0), new Point2D(0.0, 60.0),
				new Point2D(60.0, 60.0), new Point2D(60.0, 0.0)));
		images.put("triangle",new Triangle(mapEditor,"inclinedGround",new Point2D(0.0, 0.0), new Point2D(0.0, 60.0),
				new Point2D(60.0, 60.0)));
		images.put("genericPolygon",new GenericPolygon(mapEditor,"genericGround",new Point2D(30.0, 0.0), new Point2D(0.0, 24.0),
				new Point2D(0.0, 60.0), new Point2D(60.0, 60.0), new Point2D(60.0, 24.0)));
		quadCurve = new Curve(mapEditor, "curveGround", new Point2D(0.0, 0.0), new Point2D(60.0, 60.0));
	}
	
	public PolygonObject copyImage(String name)
	{
		PolygonObject imageCopy = null;
		try {
			imageCopy = (PolygonObject) images.get(name).clone();
		
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return imageCopy;
	}
	
	public Curve getQuadCurve() {
		return quadCurve;
	}
}
