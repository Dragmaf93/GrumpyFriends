package element.ground;

import physic.PhysicalTriangularObject;
import utils.Point;
import utils.Vector;

import java.util.List;

import element.Ground;
import physic.PhysicalObject;
import physic.PhysicalObjectManager;

public class InclinedGround  implements Ground {

	private float angleRotation;
	
	private PhysicalObject physicObject;
	private List<Point> points;
	
	public InclinedGround(float x, float y, float width, float height, int angleRotationDegree) {
		
		this.angleRotation=(float) Math.toRadians(angleRotationDegree);
		
		this.physicObject = new PhysicalTriangularObject(x, y, width, height, angleRotation);
		PhysicalObjectManager.getInstance().buildPhysicObject(physicObject);
	
	}

	public InclinedGround(List<Vector> points2) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PhysicalObject getPhysicObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Point> getPoint() {
		// TODO Auto-generated method stub
		return null;
	}
}
