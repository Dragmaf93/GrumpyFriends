package mapEditor;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.QuadCurve;

public class Curve extends QuadCurve {

	private double width;
	private double height;

	private double x;
	private double y;
	private DropShadow borderGlow;
	
	private MapEditor mapEditor;
	private String nameObject;
	private Point2D start;
	private Point2D end;
	private ArrayList<Point2D> distancePoints;
	private ArrayList<Point2D> points;
	private int xMagg;
	private static int id = 0;
	private int realId;
	
	public Curve(MapEditor mapEditor, String nameObject, Point2D start, Point2D end) {
		
		distancePoints = new ArrayList<Point2D>();
		points = new ArrayList<Point2D>();
		
		this.mapEditor = mapEditor;
		this.nameObject = nameObject;
		this.start = start;
		this.end = end;
		
		width = end.getX() - start.getX();
		height = 60.0;
		
		setStartX(start.getX());
		setStartY(start.getY()+height);
		setEndX(end.getX());
		setEndY(start.getY()+height);
//		xControl = 2*(start.getX()+start.getX()/2) - getStartX()/2 -getEndX()/2;
//	    yControl = 2*start.getY() - getStartY()/2 -getEndY()/2;
		
		setControlX(start.getX()+width/2);
		setControlY(start.getY());
		
		points.add(0,new Point2D(getStartX(), getStartY()));
		points.add(1,new Point2D(getEndX(), getEndY()));
		points.add(2,new Point2D(getControlX(), getControlY()));
		
		borderGlow = new DropShadow();
		borderGlow.setOffsetX(0f);
		borderGlow.setOffsetY(0f);
		borderGlow.setColor(Color.rgb(0, 0, 0));
		borderGlow.setWidth(10f);
		borderGlow.setHeight(10f);
		this.setEffect(borderGlow);
		
		this.realId = id;
		this.id ++;
	}
	
	public void modifyPositionFirst(Point2D point) {
		
		setStartX(point.getX());
		setStartY(point.getY()+height);
		setEndX(point.getX()+width);
		setEndY(point.getY()+height);
		
		setControlX(point.getX()+width/2);
		setControlY(point.getY());

		start = new Point2D(getStartX(), getStartY());
		end = new Point2D(getEndX(), getEndY());
		
		points.clear();
		
		points.add(0,start);
		points.add(1,end);
		points.add(2,new Point2D(getControlX(), getControlY()));
	}

	public void modifyPosition(Point2D event, double x, double y) {

//		pointForFirstTime = point;
		for (int i = 0; i < points.size(); i++) {
			
			Point2D newPoint = null;
			if (x != 0.0)
				xMagg++;
			if (x == 0.0 || xMagg == 1)
			{
				if (points.get(i).getX() <= event.getX() && points.get(i).getY() <= event.getY())
					newPoint = new Point2D((event.getX()-x)-distancePoints.get(i).getX(), (event.getY()+y)-distancePoints.get(i).getY());
				else if (points.get(i).getX() <= event.getX() && points.get(i).getY() >= event.getY())
					newPoint = new Point2D((event.getX()-x)-distancePoints.get(i).getX(), (event.getY()+y)+distancePoints.get(i).getY());
				else if (points.get(i).getX() >= event.getX() && points.get(i).getY() >= event.getY())
					newPoint = new Point2D((event.getX()-x)+distancePoints.get(i).getX(), (event.getY()+y)+distancePoints.get(i).getY());
				else if (points.get(i).getX() >= event.getX() && points.get(i).getY() <= event.getY())
					newPoint = new Point2D(event.getX()-x+distancePoints.get(i).getX(), (event.getY()+y)-distancePoints.get(i).getY());
			}
			else
			{
				if (points.get(i).getX() <= (event.getX()) && points.get(i).getY() <= event.getY())
					newPoint = new Point2D((event.getX())-distancePoints.get(i).getX(), event.getY()-distancePoints.get(i).getY());
				else if (points.get(i).getX() <= (event.getX()) && points.get(i).getY() >= event.getY())
					newPoint = new Point2D((event.getX())-distancePoints.get(i).getX(), event.getY()+distancePoints.get(i).getY());
				else if (points.get(i).getX() >= ((event.getX())) && points.get(i).getY() >= event.getY())
					newPoint = new Point2D((event.getX())+distancePoints.get(i).getX(), event.getY()+distancePoints.get(i).getY());
				else if (points.get(i).getX() >= ((event.getX())) && points.get(i).getY() <= event.getY())
					newPoint = new Point2D((event.getX())+distancePoints.get(i).getX(), event.getY()-distancePoints.get(i).getY());
			}
			if (newPoint != null) {
					
				if (i == 0) {
					setStartX(newPoint.getX());
					setStartY(newPoint.getY());
				}
				else if (i == 2) {
					setControlX(newPoint.getX());
					setControlY(newPoint.getY());
				}
				else {
					setEndX(newPoint.getX());
					setEndY(newPoint.getY());
				}
			}
		}
		start = new Point2D(getStartX(), getStartY());
		end = new Point2D(getEndX(), getEndY());
		
		points.clear();
		
		points.add(0,new Point2D(getStartX(), getStartY()));
		points.add(1,new Point2D(getEndX(), getEndY()));
		points.add(2,new Point2D(getControlX(), getControlY()));
	}
	
	@Override
	public String toString() {
		return "Curve [width=" + width + ", height=" + height
				+ ", getStartX()=" + getStartX() + ", getStartY()="
				+ getStartY() + ",\n getControlX()=" + getControlX()
				+ ", getControlY()=" + getControlY() + ",\n getEndX()="
				+ getEndX() + ", getEndY()=" + getEndY() + "]";
	}

	public void setX(double d) {
		x = d;
	}

	public void setY(double d) {
		y = d;
	}

	public double getY() {
		return y;
	}

	public double getX() {
		return x;
	}
	
	protected void computeDistanceVertex() {
		distancePoints.clear();
		
		for (Point2D point : points)
			distancePoints.add(computeDistance(point.getX(), point.getY()));
	}
	
	protected Point2D computeDistance(double x, double y) {
		return new Point2D(Math.abs(x - mapEditor.getMouseX()), Math.abs(y - mapEditor.getMouseY()));
	}
	
	@Override
	protected Curve clone() throws CloneNotSupportedException {
		Curve curve = new Curve(mapEditor, nameObject, start, end);
		
		curve.setStartX(start.getX());
		curve.setStartY(start.getY());
		curve.setEndX(end.getX());
		curve.setEndY(end.getY());
		
		curve.setControlX(this.getControlX());
		curve.setControlY(this.getControlY());
		
		curve.points.clear();
		
		curve.points.add(0,new Point2D(getStartX(), getStartY()));
		curve.points.add(1,new Point2D(getEndX(), getEndY()));
		curve.points.add(2,new Point2D(getControlX(), getControlY()));
		
		return curve;
	}

	public ObservableList<Double> getPoints() {
		ObservableList<Double> arrayReturn = FXCollections.observableArrayList();
		
		for (Point2D point : points) {
			arrayReturn.addAll(new Double[]{point.getX(),point.getY()});
		}
		
		return arrayReturn;
	}
	
	public ArrayList<Point2D> getRealPoints() {
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
		
		setStartX(points.get(0).getX());
		setStartY(points.get(0).getY());
		setControlX(points.get(2).getX());
		setControlY(points.get(2).getY());
		setEndX(points.get(1).getX());
		setEndY(points.get(1).getY());
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
	
	public boolean atleastOneDifferentVertex(Curve curve)
	{
		if (this.getStartX() != curve.getStartX() || this.getStartY() != curve.getStartY() ||
				this.getControlX() != curve.getControlX() || this.getControlY() != curve.getControlY() ||
				this.getEndX() != curve.getEndX() || this.getEndY() != curve.getEndY())
			return true;
		return false;
	}
	
	public void setIdObject(int id) {
		this.realId = id;
	}
	
	public int getIdObject() {
		return realId;
	}
	
	public void setPointWithExistingObject(Curve curve) {
		this.setStartX(curve.getStartX());
		this.setStartY(curve.getStartY());
		this.setEndX(curve.getEndX());
		this.setEndY(curve.getEndY());
		
		this.setControlX(curve.getControlX());
		this.setControlY(curve.getControlY());
	}

	public boolean vertexEquals(Curve dragged) {
		if (this.getStartX() == dragged.getStartX() && this.getStartY() == dragged.getStartY() &&
				this.getControlX() == dragged.getControlX() && this.getControlY() == dragged.getControlY() &&
						this.getEndX() == dragged.getEndX() && this.getEndY() == dragged.getEndY())
			return true;
		return false;
	}
}
