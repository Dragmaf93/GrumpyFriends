package mapEditor;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;


public interface SquarePolygon {

	public String getNameObject();
	
	
	public double getWidth();
	
	public double getHeight();
	
	public String getAngleRotation();
	
	public void addVertex(Point2D newVertex);

	public void modifyPosition(Point2D point,double width, double height);
	
	public void modifyPositionFirst(Point2D point,double width, double height);
	
	public void clearAndAddPoints();

	
	public void setWidth(double width);
	
	public void setHeight(double height);
	
	
	public void setX(double d);
	
	public void setY(double d);

	
	public double getX();
	
	public double getY();
	
	public List<Point2D> getPointsVertex();

	public void modifyPositionWithVertex(Point2D oldPoint, Point2D newPoint);
	
	public boolean containsPoint(Point2D test);
	
	public void computeDistanceVertex(); 
	
	public Point2D computeDistance(double x, double y);
	
	public void modifyAllVertex(double eventX, double eventY, double x, double y);
	
	public boolean vertexEquals(SquarePolygon polygon);
	
	
	public void setIdObject(int id);
	
	
	public int getIdObject();

	public SquarePolygon clone();

	
	public void setLayoutX(double d);

	
	public void setLayoutY(double lastItemInserted);

	
	public double getLayoutX();

	
	public double getLayoutY();

	public ObservableList<Double> getPoints();

//	public boolean vertexEquals(Curve imageTmp);
	
	
}
