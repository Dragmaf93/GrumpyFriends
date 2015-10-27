package element.ground;

import physic.PhysicalTriangularObject;
import element.Ground;
import physic.PhysicalObject;
import physic.PhysicalObjectManager;

public class InclinedGround  implements Ground {

	private float angleRotation;
	
	private PhysicalObject physicObject;

	public InclinedGround(float x, float y, float width, float height, int angleRotationDegree) {
		
		this.angleRotation=(float) Math.toRadians(angleRotationDegree);
		
		this.physicObject = new PhysicalTriangularObject(x, y, width, height, angleRotation);
		PhysicalObjectManager.getInstance().buildPhysicObject(physicObject);
	
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
}
