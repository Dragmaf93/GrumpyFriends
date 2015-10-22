package mapEditor;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class Triangle extends PolygonObject {

	public Triangle(MapEditor mapEditor, String nameObject, Point2D point1, Point2D point2, Point2D point3) {
		super(mapEditor, nameObject);
		
		setUpperLeftPosition(point1, false);
		setBottomLeftPosition(point2, false);
		setBottomRightPosition(point3, false);
		
		getPoints().addAll(new Double[]{
        	    point1.getX(), point1.getY(),
        	    point2.getX(), point2.getY(),
        	    point3.getX(), point3.getY()});
		
		width = point3.getX()-point2.getX();
		height = point2.getY()-point1.getY();
	}
	
	@Override
	protected PolygonObject clone() throws CloneNotSupportedException 
	{
		PolygonObject newImage = new Triangle(this.mapEditor, this.nameObject, this.upperLeftPosition, this.bottomLeftPosition, 
				this.bottomRightPosition);
//		
//		if (this.nameObject.equals("inclinedGround"))
//			newImage.angleRotation = this.angleRotation;
		return newImage;
	}
	
	@Override
	public void modifyPosition(Point2D point, double width, double height) {
		this.upperLeftPosition = new Point2D(point.getX(), point.getY());
		this.bottomLeftPosition = new Point2D(point.getX(), point.getY()+height);
		this.bottomRightPosition = new Point2D(point.getX()+width, point.getY()+height);
		
		points.add(upperLeftPosition);
		points.add(bottomLeftPosition);
		points.add(bottomRightPosition);
	}
	
	@Override
	public void modifyPositionWithVertex(Point2D newPoint, String idVertex) {
		if (idVertex.equals("UpperLeft"))
			upperLeftPosition = newPoint;
		if (idVertex.equals("BottomLeft"))
			bottomLeftPosition = newPoint;
		if (idVertex.equals("BottomRight"))
			bottomRightPosition = newPoint;
		clearAndAddPoints();
	}
	
	@Override
	protected void clearAndAddPoints() {
		this.getPoints().clear();
		getPoints().addAll(new Double[]{
        	    upperLeftPosition.getX(), upperLeftPosition.getY(),
        	    bottomLeftPosition.getX(), bottomLeftPosition.getY(),
        	    bottomRightPosition.getX(), bottomRightPosition.getY()});
	}
	
	@Override
	protected void computeDistanceVertex() {
		distanceUpperLeft = computeDistance(this.getUpperLeftPosition().getX(), this.getUpperLeftPosition().getY());
        distanceBottomLeft = computeDistance(this.getBottomLeftPosition().getX(), this.getBottomLeftPosition().getY());
        distanceBottomRight = computeDistance(this.getBottomRightPosition().getX(), this.getBottomRightPosition().getY());
	}
	
	@Override
	protected void modifyAllVertex(double eventX, double eventY) {
		this.modifyPositionWithVertex(new Point2D(eventX-distanceUpperLeft.getX(), eventY-distanceUpperLeft.getY()), "UpperLeft");
		this.modifyPositionWithVertex(new Point2D(eventX-distanceBottomLeft.getX(), eventY+distanceBottomLeft.getY()), "BottomLeft");
		this.modifyPositionWithVertex(new Point2D(eventX+distanceBottomRight.getX(), eventY+distanceBottomRight.getY()), "BottomRight");
	}
	
}
