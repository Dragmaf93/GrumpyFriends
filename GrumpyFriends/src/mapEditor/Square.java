package mapEditor;

import javafx.geometry.Point2D;

public class Square extends PolygonObject {

	public Square(MapEditor mapEditor, String nameObject, Point2D point1, Point2D point2, Point2D point3, Point2D point4) 
	{
		super(mapEditor, nameObject);

		points.add(point1);
		points.add(point2);
		points.add(point3);
		points.add(point4);
		
		getPoints().addAll(new Double[]{
        	    point1.getX(), point1.getY(),
        	    point2.getX(), point2.getY(),
        	    point3.getX(), point3.getY(),
        	    point4.getX(), point4.getY()});
		
		width = point4.getX()-point1.getX();
		height = point2.getY()-point1.getY();
		
		computeDistanceVertex();
	}
	
	@Override
	public PolygonObject clone()
	{
		PolygonObject newImage = new Square(this.mapEditor, this.nameObject, this.points.get(0), this.points.get(1), 
				this.points.get(2), this.points.get(3));
		
		return newImage;
	}

	@Override
	public boolean vertexEquals(SquarePolygon polygon) {
		if (polygon instanceof PolygonObject)
			return vertexEqual((PolygonObject) polygon);
		return false;
	}
}
