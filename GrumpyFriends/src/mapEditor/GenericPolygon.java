package mapEditor;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

public class GenericPolygon extends PolygonObject {
	
	public GenericPolygon(MapEditor mapEditor, String nameObject, Point2D point1, Point2D point2, Point2D point3, Point2D point4, Point2D point5) {
		super(mapEditor, nameObject);
		
		points.add(point1);
		points.add(point2);
		points.add(point3);
		points.add(point4);
		points.add(point5);
		
		getPoints().addAll(new Double[]{
        	    point1.getX(), point1.getY(),
        	    point2.getX(), point2.getY(),
        	    point3.getX(), point3.getY(),
        	    point4.getX(), point4.getY(),
        	    point5.getX(), point5.getY()});
		
		width = point5.getX() - point2.getX();
		height = point3.getY() - point1.getY();
		
		computeDistanceVertex();
	}
	
	@Override
	public PolygonObject clone()
	{
		PolygonObject newObject = new GenericPolygon(this.mapEditor, this.nameObject, this.points.get(0), this.points.get(1), 
				this.points.get(2),this.points.get(3), this.points.get(4));
		
		for (Point2D point : points) {
			if (!newObject.points.contains(point))
				newObject.points.add(point);
		}
		return newObject;
	}
	
	@Override
	public void modifyPositionFirst(Point2D point,double width, double height) {
		points.clear();
		points.add(0,new Point2D(point.getX()+width/2, point.getY()));
		points.add(1,new Point2D(point.getX(), point.getY()+height/2));
		points.add(2,new Point2D(point.getX(), point.getY()+height));
		points.add(3,new Point2D(point.getX()+width, point.getY()+height));
		points.add(4,new Point2D(point.getX()+width, point.getY()+height/2));
	}
	
	@Override
	public void addVertex(Point2D newVertex) {
		Line line;
		List<Point2D> listTmp = new ArrayList<Point2D>();
		for (int i = 0; i < points.size(); i++)
		{
			if (i < points.size()-1)
			{
				line = new Line(points.get(i).getX(), points.get(i).getY(), points.get(i+1).getX(),points.get(i+1).getY());
				if (line.intersects(newVertex.getX(), newVertex.getY(),1,1))
				{
					listTmp.add(points.get(i));
					listTmp.add(newVertex);
					listTmp.add(points.get(i+1));
					i++;
				}
				else
					listTmp.add(points.get(i));
			}
			else
			{
				line = new Line(points.get(i).getX(), points.get(i).getY(), points.get(0).getX(),points.get(0).getY());
				if (line.intersects(newVertex.getX(), newVertex.getY(),1,1))
				{
					listTmp.add(points.get(i));
					listTmp.add(newVertex);
				}
				else
					listTmp.add(points.get(i));
			}
		}
		
		points.clear();
		for (Point2D point : listTmp)
			points.add(point);

		clearAndAddPoints();
	}

	@Override
	public boolean vertexEquals(SquarePolygon polygon) {
		if (polygon instanceof PolygonObject)
			return vertexEqual((PolygonObject) polygon);
		return false;
	}
	
}
