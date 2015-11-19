package element.ground;

import java.util.List;

import physic.PhysicalObjectManager;
import physic.PhysicalPolygonObject;
import utils.Point;

public class GenericGround extends AbstractGround{

	public GenericGround(List<Point> points) {
		super(points);
		physicalObject = new PhysicalPolygonObject(points);
//		
//		physicalObject = new PhysicalCurveObject();
		PhysicalObjectManager.getInstance().buildPhysicObject(physicalObject);
		
		
	}

	@Override
	public void setPosition() {
		positionX = points.get(0).x;
		positionY = points.get(0).y;
	}

	@Override
	public void setSize() {
		double minX=Double.MAX_VALUE,minY=Double.MAX_VALUE,
				maxY=Double.MIN_VALUE,maxX=Double.MIN_VALUE;
		for (Point point : points) {
			if(point.x<minX) minX=point.x;
			if(point.x>maxX) maxX=point.x;
			if(point.y<minY) minY=point.y;
			if(point.y>maxY) maxY=point.y;
		}
		
		height=maxY-minY;
		width=maxX-minX;
	}
	
	

}
