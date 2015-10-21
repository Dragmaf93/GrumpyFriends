package world;

import physic.PhysicalObject;
import physic.PhysicalObjectManager;
import physic.PhysicalRectangularObject;


public class LinearGround implements Ground{

	private PhysicalObject physicBody;

	
	public LinearGround(float x, float y,float width,float height) {
		
		this.physicBody = new PhysicalRectangularObject(x, y, width, height);
		PhysicalObjectManager.getInstance().buildPhysicObject(physicBody);
	
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
		return physicBody;
	}
}
