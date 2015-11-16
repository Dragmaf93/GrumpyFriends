package mapEditor;
import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurve;

public abstract class PolygonObject extends Polygon {

	protected String nameObject;
	protected MapEditor mapEditor;
	
	protected double width;
	protected double height;
	protected double angleRotation;
	
	protected DropShadow borderGlow;
	private double xTmp;
	private double yTmp;
	
	protected ArrayList<Point2D> points;
	protected ArrayList<Point2D> distancePoints;
	private int xMagg;
	
	private static int id = 0;
	private int realId;
	
	public PolygonObject(MapEditor mapEditor, String nameObject) 
	{
		this.mapEditor = mapEditor;
		
		this.nameObject = nameObject;
		
		points = new ArrayList<Point2D>();
		distancePoints = new ArrayList<Point2D>();
		
		borderGlow = new DropShadow();
		borderGlow.setOffsetX(0f);
		borderGlow.setOffsetY(0f);
		borderGlow.setColor(Color.rgb(0, 0, 0));
		borderGlow.setWidth(10f);
		borderGlow.setHeight(10f);
		this.setEffect(borderGlow);
		
		this.realId = id;
		this.id ++;
//		this.setEffect(new Reflection());
	}
	
	@Override
	protected PolygonObject clone() throws CloneNotSupportedException 
	{
		PolygonObject newObject = new Square(this.mapEditor, this.nameObject, this.points.get(0), this.points.get(1), 
				this.points.get(2),this.points.get(3));
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
		return Double.toString((double) angleRotation);
	}
	
	public void addVertex(Point2D newVertex) {}

	public void modifyPosition(Point2D point,double width, double height) {
		modifyPositionFirst(point, width, height);
		clearAndAddPoints();
	}
	
	public void modifyPositionFirst(Point2D point,double width, double height) {
		points.clear();
		points.add(0,new Point2D(point.getX(), point.getY()));
		points.add(1,new Point2D(point.getX(), point.getY()+height));
		points.add(2,new Point2D(point.getX()+width, point.getY()+height));
		points.add(3,new Point2D(point.getX()+width, point.getY()));
	}
	
	protected void clearAndAddPoints() {
		this.getPoints().clear();
		for (Point2D point : points)
			getPoints().addAll(new Double[]{point.getX(),point.getY()});
	}

	public void setWidth(double width) {
		this.width = width;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}
	
	@Override
	public String toString() {
		return "PolygonObject [nameObject=" + nameObject + " , "+realId+" points=" + points + "]";
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

	public void modifyPositionWithVertex(Point2D oldPoint, Point2D newPoint) {
		
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).equals(oldPoint))
			{
				points.remove(i);
				points.add(i, newPoint);
				break;
			}
		}
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
		distancePoints.clear();
		for (Point2D point : points)
			distancePoints.add(computeDistance(point.getX(), point.getY()));
	}
	
	protected Point2D computeDistance(double x, double y) {
		return new Point2D(Math.abs(x - mapEditor.getMouseX()), Math.abs(y - mapEditor.getMouseY()));
	}
	
	protected void modifyAllVertex(double eventX, double eventY, double x, double y) {
		if (x != 0.0)
			xMagg++;
		for (int i = 0; i < points.size(); i++) {
			Point2D newPoint = null;
			if (x == 0.0 || xMagg == 1)
			{
				if (points.get(i).getX() <= eventX && points.get(i).getY() <= eventY)
					newPoint = new Point2D((eventX-x)-distancePoints.get(i).getX(), (eventY+y)-distancePoints.get(i).getY());
				else if (points.get(i).getX() <= eventX && points.get(i).getY() >= eventY)
					newPoint = new Point2D((eventX-x)-distancePoints.get(i).getX(), (eventY+y)+distancePoints.get(i).getY());
				else if (points.get(i).getX() >= eventX && points.get(i).getY() >= eventY)
					newPoint = new Point2D((eventX-x)+distancePoints.get(i).getX(), (eventY+y)+distancePoints.get(i).getY());
				else if (points.get(i).getX() >= eventX && points.get(i).getY() <= eventY)
					newPoint = new Point2D(eventX-x+distancePoints.get(i).getX(), (eventY+y)-distancePoints.get(i).getY());
			}
			else
			{
				if (points.get(i).getX() <= (eventX-x) && points.get(i).getY() <= eventY)
					newPoint = new Point2D((eventX-x)-distancePoints.get(i).getX(), (eventY+y)-distancePoints.get(i).getY());
				else if (points.get(i).getX() <= (eventX-x) && points.get(i).getY() >= eventY)
					newPoint = new Point2D((eventX-x)-distancePoints.get(i).getX(), (eventY+y)+distancePoints.get(i).getY());
				else if (points.get(i).getX() >= (eventX-x) && points.get(i).getY() >= eventY)
					newPoint = new Point2D((eventX-x)+distancePoints.get(i).getX(), (eventY+y)+distancePoints.get(i).getY());
				else if (points.get(i).getX() >= (eventX-x) && points.get(i).getY() <= eventY)
					newPoint = new Point2D((eventX-x)+distancePoints.get(i).getX(), (eventY+y)-distancePoints.get(i).getY());
			}
			if (newPoint != null)
				this.modifyPositionWithVertex(points.get(i), newPoint);
		}
	}
	
	ArrayList<Point2D> getDistancePoints() {
		return distancePoints;
	}
	
	public boolean vertexEquals(PolygonObject polygon) {
		
		int contVertexEquals = 0;
		for (Point2D point : this.points) {
			if (polygon.points.contains(point))
				contVertexEquals++;
		}
		
		if (contVertexEquals == this.points.size())
			return true;
		return false;
	}
	
	public void setIdObject(int id) {
		this.realId = id;
	}
	
	public int getIdObject() {
		return realId;
	}
	
}
