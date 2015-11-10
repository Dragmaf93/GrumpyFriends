package mapEditor;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
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
	private Point2D pointForFirstTime;
	private ArrayList<Point2D> distancePoints;
	private ArrayList<Point2D> points;
	private int xMagg;
	private double xControl;
	private double yControl;
	
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
		setEndX(end.getX()+width);
		setEndY(end.getY()+height);
		xControl = 2*(start.getX()+start.getX()/2) - getStartX()/2 -getEndX()/2;
	    yControl = 2*start.getY() - getStartY()/2 -getEndY()/2;
		
		setControlX(start.getX()+start.getX()/2);
		setControlY(start.getY());
//		setControlX(start.getX()+start.getX()/2);
//		setControlY(start.getY());
		
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
	}
	
	public void modifyPositionFirst(Point2D point) {

		pointForFirstTime = point;
		setStartX(point.getX());
		setStartY(point.getY()+height);
		setEndX(point.getX()+width);
		setEndY(point.getY()+height);
		
//		double x1 = 2*(point.getX()+width/2) - getStartX()/2 -getEndX()/2;
//	    double y1 = 2*point.getY() - getStartY()/2 -getEndY()/2;
//		
//	    System.out.println(y1);
	    
		setControlX(point.getX()+width/2);
		setControlY(point.getY());

		start = new Point2D(getStartX(), getStartY());
		end = new Point2D(getEndX(), getEndY());
		
		points.clear();
		
		points.add(0,start);
		points.add(1,end);
		points.add(2,new Point2D(getControlX(), getControlY()));
		
		System.out.println(this);
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
				{
					System.out.println("PRIMO: "+points.get(i));
					newPoint = new Point2D((event.getX())-distancePoints.get(i).getX(), event.getY()-distancePoints.get(i).getY());
				}
				else if (points.get(i).getX() <= (event.getX()) && points.get(i).getY() >= event.getY())
				{
					System.out.println("SECONDO: "+points.get(i));
					newPoint = new Point2D((event.getX())-distancePoints.get(i).getX(), event.getY()+distancePoints.get(i).getY());
				}
				else if (points.get(i).getX() >= ((event.getX())) && points.get(i).getY() >= event.getY())
				{
					System.out.println("TERZO: "+points.get(i));
					newPoint = new Point2D((event.getX())+distancePoints.get(i).getX(), event.getY()+distancePoints.get(i).getY());
				}
				else if (points.get(i).getX() >= ((event.getX())) && points.get(i).getY() <= event.getY())
				{
					System.out.println("QUARTO: "+points.get(i)+" -----------> "+ event.getY()+" "+ (event.getY()-distancePoints.get(i).getY()));
					newPoint = new Point2D((event.getX())+distancePoints.get(i).getX(), event.getY()-distancePoints.get(i).getY());
				}
			}
			if (newPoint != null) {
					
				if (i == 0) {
					setStartX(newPoint.getX());
					setStartY(newPoint.getY());
				}
				else if (i == 2) {
					setControlX(newPoint.getX());
					setControlY(newPoint.getY());
//					double x1 = 2*(newPoint.getX()) - getStartX()/2 -getEndX()/2;
//					double y1 = 2*newPoint.getY() - getStartY()/2 -getEndY()/2;
//					
//					setControlX(x1);
//					setControlY(y1);
				}
				else {
					setEndX(newPoint.getX());
					setEndY(newPoint.getY());
				}
			}
		}
		
		System.out.println("-------------------------------------------");
//		setStartX(event.getX()-distancePoints.get(0).getX());
//		setStartY(event.getY()-distancePoints.get(0).getY());
//		setEndX(event.getX()-distancePoints.get(2).getX());
//		setEndY(event.getY()-distancePoints.get(2).getY());
//		setControlX(event.getX()-distancePoints.get(1).getX());
//		setControlY(event.getY()-distancePoints.get(1).getY());
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
//		double x1 = 2*(start.getX()+start.getX()/2) - getStartX()/2 -getEndX()/2;
//	    double y1 = 2*start.getY() - getStartY()/2 -getEndY()/2;
		
		curve.setControlX(this.points.get(2).getX());
		curve.setControlY(this.points.get(2).getY());
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
	
}
