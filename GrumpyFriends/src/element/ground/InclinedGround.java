package element.ground;

import physic.PhysicalPolygonObject;
import physic.PhysicalTriangularObject;
import utils.Point;

import java.util.List;

import physic.PhysicalObjectManager;

public class InclinedGround  extends AbstractGround {

	private float angleRotation;
	
	public InclinedGround(float x, float y, float width, float height, int angleRotationDegree) {
		
		this.angleRotation=(float) Math.toRadians(angleRotationDegree);
		
		physicalObject = new PhysicalTriangularObject(x, y, width, height, angleRotation);
		PhysicalObjectManager.getInstance().buildPhysicObject(physicalObject);
	
	}

	public InclinedGround(List<Point> points) {
		super(points);
		physicalObject = new PhysicalPolygonObject(points);
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
