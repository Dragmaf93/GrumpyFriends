package mapEditor;

import javafx.geometry.Point2D;

public class Square extends PolygonObject {

	public Square(MapEditor mapEditor, String nameObject, Point2D point1, Point2D point2, Point2D point3, Point2D point4) 
	{
		super(mapEditor, nameObject);

		setUpperLeftPosition(point1, false);
		setBottomLeftPosition(point2, false);
		setBottomRightPosition(point3, false);
		setUpperRightPosition(point4, false);
		
		getPoints().addAll(new Double[]{
        	    point1.getX(), point1.getY(),
        	    point2.getX(), point2.getY(),
        	    point3.getX(), point3.getY(),
        	    point4.getX(), point4.getY()});
		
		width = point4.getX()-point1.getX();
		height = point2.getY()-point1.getY();
		
		
	}
	
	@Override
	protected PolygonObject clone() throws CloneNotSupportedException 
	{
		PolygonObject newImage = new Square(this.mapEditor, this.nameObject, this.upperLeftPosition, this.bottomLeftPosition, 
				this.bottomRightPosition, this.upperRightPosition);
		
		if (this.nameObject.equals("inclinedGround"))
			newImage.angleRotation = this.angleRotation;
		return newImage;
	}
}
