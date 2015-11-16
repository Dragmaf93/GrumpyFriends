package mapEditor;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class Triangle extends PolygonObject {

	double angleBetweenUpLeftAndBottLeft;
	double angleBetweenBottRightAndUpLeft;
	double angleBetweenBottLeftAndBottRight;
	
	public Triangle(MapEditor mapEditor, String nameObject, Point2D point1, Point2D point2, Point2D point3) {
		super(mapEditor, nameObject);
		
		points.add(point1);
		points.add(point2);
		points.add(point3);
		
		getPoints().addAll(new Double[]{
        	    point1.getX(), point1.getY(),
        	    point2.getX(), point2.getY(),
        	    point3.getX(), point3.getY()});
		
		width = point3.getX()-point2.getX();
		height = point2.getY()-point1.getY();
		
		computeDistanceVertex();
		computeAngles();
	}
	
	@Override
	protected PolygonObject clone() throws CloneNotSupportedException 
	{
		PolygonObject newImage = new Triangle(this.mapEditor, this.nameObject, this.points.get(0), this.points.get(1), 
				this.points.get(2));
		return newImage;
	}
	
	@Override
	public void modifyPositionFirst(Point2D point, double width, double height) {
		points.clear();
		points.add(0,new Point2D(point.getX(), point.getY()));
		points.add(1,new Point2D(point.getX(), point.getY()+height));
		points.add(2,new Point2D(point.getX()+width, point.getY()+height));
	}
	
	@Override
	protected void modifyAllVertex(double eventX, double eventY, double x, double y) {
		super.modifyAllVertex(eventX, eventY, x, y);
		computeAngles();
	}
	
	private void computeAngles() {
		double firstSide = Math.hypot(points.get(1).getX()-points.get(0).getX(), points.get(1).getY()-points.get(0).getY());
		double secondSide = Math.hypot(points.get(2).getX()-points.get(1).getX(), points.get(2).getY()-points.get(1).getY());
		double hipotenuse = Math.hypot(points.get(2).getX()-points.get(0).getX(), points.get(2).getY()-points.get(0).getY());
		
		angleBetweenUpLeftAndBottLeft = Math.toDegrees(secondSide/hipotenuse);
		angleBetweenBottRightAndUpLeft = Math.toDegrees(firstSide/hipotenuse);
		angleBetweenBottLeftAndBottRight = 180 -(angleBetweenBottRightAndUpLeft+angleBetweenUpLeftAndBottLeft);
	
		System.out.println(angleBetweenBottLeftAndBottRight+" "+angleBetweenBottRightAndUpLeft+" "+angleBetweenUpLeftAndBottLeft);
	
//		TODO Rivede calcolo degli angoli, sono errati... con (secondSide(hipotenuse) si trova il coseno
	}
	
}
