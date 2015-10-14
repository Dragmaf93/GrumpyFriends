package world;

import physic.PhysicalTriangularObject;
import physic.PhysicalObject;
import physic.PhysicalObjectCreator;

public class InclinedGround  implements Ground {

	private float angleRotation;
	
	private PhysicalObject physicObject;

	public InclinedGround(float x, float y, float width, float height, int angleRotationDegree) {
		
		this.angleRotation=(float) Math.toRadians(angleRotationDegree);
		
		this.physicObject = new PhysicalTriangularObject(x, y, width, height, angleRotation);
		PhysicalObjectCreator.getInstance().buildPhysicObject(physicObject);
	
	}

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PhysicalObject getPhysicObject() {
		// TODO Auto-generated method stub
		return null;
	}
}
