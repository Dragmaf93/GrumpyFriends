package mapEditor;
import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light.Point;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


public abstract class PolygonObject extends Polygon {

	protected String nameObject;
	protected MapEditor mapEditor;
	
	protected Point2D upperLeftPosition;
	protected Point2D upperRightPosition;
	protected Point2D bottomLeftPosition;
	protected Point2D bottomRightPosition;
	
	protected Point2D distanceUpperLeft;
	protected Point2D distanceUpperRight;
	protected Point2D distanceBottomLeft;
	protected Point2D distanceBottomRight;
	
	protected double width;
	protected double height;
	protected double angleRotation;
	
	protected DropShadow borderGlow;
	private double xTmp;
	private double yTmp;
	
	protected ArrayList<Point2D> points;
	
	public PolygonObject(MapEditor mapEditor, String nameObject) 
	{
		if (nameObject.equals("inclinedGround"))
			this.angleRotation = angleRotation;
//		TODO vedere come si calcola l'angolo avendo gli estremi
		
		this.mapEditor = mapEditor;
		
		this.nameObject = nameObject;
		
		points = new ArrayList<Point2D>();
		
		borderGlow = new DropShadow();
		borderGlow.setOffsetX(0f);
		borderGlow.setOffsetY(0f);
		borderGlow.setColor(Color.rgb(0, 0, 0));
		borderGlow.setWidth(10f);
		borderGlow.setHeight(10f);
		this.setEffect(borderGlow);
//		this.setEffect(new Reflection());
	}
	
	@Override
	protected PolygonObject clone() throws CloneNotSupportedException 
	{
		PolygonObject newObject = new Square(this.mapEditor, this.nameObject, this.upperLeftPosition, this.bottomLeftPosition, this.bottomRightPosition, this.bottomRightPosition);
		
		if (this.nameObject.equals("inclinedGround"))
			newObject.angleRotation = this.angleRotation;
		return newObject;
	}
	
	public String getNameObject() {
		return nameObject;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public String getAngleRotation() {
		return Integer.toString((int) angleRotation);
//		TODO VEdere se è possibile trasformare un double in string e non un int
	}
	
	public Point2D getUpperLeftPosition() {
		return upperLeftPosition;
	}
	
	public Point2D getUpperRightPosition() {
		return upperRightPosition;
	}

	public Point2D getBottomLeftPosition() {
		return bottomLeftPosition;
	}
	
	public Point2D getBottomRightPosition() {
		return bottomRightPosition;
	}
	
	public void setUpperLeftPosition(Point2D startPosition, boolean clear) {
		this.upperLeftPosition = startPosition;
		
		if (clear)
			clearAndAddPoints();
	}
	
	public void setUpperRightPosition(Point2D startRightPosition, boolean clear) {
		this.upperRightPosition = startRightPosition;

		if (clear)
			clearAndAddPoints();
	}

	public void setBottomLeftPosition(Point2D endPosition, boolean clear) {
		this.bottomLeftPosition = endPosition;
		
		if (clear)
			clearAndAddPoints();
	}
	
	public void setBottomRightPosition(Point2D bottomRightPosition, boolean clear) {
		this.bottomRightPosition = bottomRightPosition;

		if (clear)
			clearAndAddPoints();
	}
		
	public void modifyPosition(Point2D point,double width, double height) {
		System.out.println("entro qui ");
		modifyPositionFirst(point, width, height);
		clearAndAddPoints();
	}
	
	
	public void modifyPositionFirst(Point2D point,double width, double height) {
		this.upperLeftPosition = new Point2D(point.getX(), point.getY());
		this.bottomLeftPosition = new Point2D(point.getX(), point.getY()+height);
		this.bottomRightPosition = new Point2D(point.getX()+width, point.getY()+height);
		this.upperRightPosition = new Point2D(point.getX()+width, point.getY());
		
		points.add(upperLeftPosition);
		points.add(bottomLeftPosition);
		points.add(bottomRightPosition);
		points.add(upperRightPosition);
	}
	
	protected void clearAndAddPoints() {
		this.getPoints().clear();
		getPoints().addAll(new Double[]{
        	    upperLeftPosition.getX(), upperLeftPosition.getY(),
        	    bottomLeftPosition.getX(), bottomLeftPosition.getY(),
        	    bottomRightPosition.getX(), bottomRightPosition.getY(),
        	    upperRightPosition.getX(), upperRightPosition.getY()});
	}
	
	public boolean isInTheArea(MouseEvent event) {
		return (event.getY() > upperLeftPosition.getY() && event.getY() < bottomLeftPosition.getY()) 
				&& (event.getX() > upperLeftPosition.getX() && event.getX() < upperRightPosition.getX());
	}

	public boolean isInTheLimit(MouseEvent event) {
		return ((int)event.getX() == (int)upperLeftPosition.getX() ||
				(int)event.getX() == (int)upperRightPosition.getX() || 
				(int)event.getY() == (int)upperLeftPosition.getY() ||
				(int)event.getY() == (int)bottomLeftPosition.getY());
	}
	
	public void setWidth(double width) {
		this.width = width;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "PolygonObject [nameObject=" + nameObject
				+ ", upperLeftPosition=" + upperLeftPosition
				+ "\n, upperRightPosition=" + upperRightPosition
				+ "\n, bottomLeftPosition=" + bottomLeftPosition
				+ "\n, bottomRightPosition=" + bottomRightPosition + ", width="
				+ width + ", height=" + height + "]";
	}

	public void setX(double d) {
		xTmp = d;
	}

	public void setY(double d) {
		yTmp = d;
	}

	public double getX() {
		return xTmp;
	}
	
	public double getY() {
		return yTmp;
	}
	
	public ArrayList<Point2D> getPointsVertex() {
		return points;
	}

	public void modifyPositionWithVertex(Point2D newPoint, String idVertex) {
		if (idVertex.equals("UpperLeft"))
			upperLeftPosition = newPoint;
		if (idVertex.equals("UpperRight"))
			upperRightPosition = newPoint;
		if (idVertex.equals("BottomLeft"))
			bottomLeftPosition = newPoint;
		if (idVertex.equals("BottomRight"))
			bottomRightPosition = newPoint;
		clearAndAddPoints();
	}
	
	public boolean containsPoint(Point2D test) {
	      int i;
	      int j;
	      boolean result = false;
	      for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
	        if ((points.get(i).getY() > test.getY()) != (points.get(j).getY() > test.getY()) && (test.getX() < (points.get(j).getX() - points.get(i).getX()) * (test.getY() - points.get(i).getY()) / (points.get(j).getY()-points.get(i).getY()) + points.get(i).getX())) {
	          result = !result;
	         }
	      }
	      return result;
    }
	
	protected void computeDistanceVertex() {
		distanceUpperLeft = computeDistance(this.getUpperLeftPosition().getX(), this.getUpperLeftPosition().getY());
        distanceBottomLeft = computeDistance(this.getBottomLeftPosition().getX(), this.getBottomLeftPosition().getY());
        distanceBottomRight = computeDistance(this.getBottomRightPosition().getX(), this.getBottomRightPosition().getY());
        distanceUpperRight = computeDistance(this.getUpperRightPosition().getX(), this.getUpperRightPosition().getY());
	}
	
	protected Point2D computeDistance(double x, double y) {
		return new Point2D(Math.abs(x - mapEditor.getMouseX()), Math.abs(y - mapEditor.getMouseY()));
	}
	
	protected void modifyAllVertex(double eventX, double eventY) {
		this.modifyPositionWithVertex(new Point2D(eventX-distanceUpperLeft.getX(), eventY-distanceUpperLeft.getY()), "UpperLeft");
		this.modifyPositionWithVertex(new Point2D(eventX-distanceBottomLeft.getX(), eventY+distanceBottomLeft.getY()), "BottomLeft");
		this.modifyPositionWithVertex(new Point2D(eventX+distanceBottomRight.getX(), eventY+distanceBottomRight.getY()), "BottomRight");
		this.modifyPositionWithVertex(new Point2D(eventX+distanceUpperRight.getX(), eventY-distanceUpperRight.getY()), "UpperRight");
	}
	
}
