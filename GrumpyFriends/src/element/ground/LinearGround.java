package element.ground;

import java.util.List;

import element.Ground;
import physic.PhysicalObject;
import physic.PhysicalObjectManager;
import physic.PhysicalPolygonObject;
import physic.PhysicalRectangularObject;
import utils.Point;
import utils.Utils;
import utils.Vector;


public class LinearGround extends AbstractGround{
	
	public LinearGround(List<Point> points){
		super(points);
		this.physicalObject = new PhysicalPolygonObject(points);
		PhysicalObjectManager.getInstance().buildPhysicObject(physicalObject);
	}
	
	public LinearGround(float x, float y,float width,float height) {
		
		this.physicalObject = new PhysicalRectangularObject(x, y, width, height);
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
		positionX=minX;
		positionY=minY;
	}
	
}
