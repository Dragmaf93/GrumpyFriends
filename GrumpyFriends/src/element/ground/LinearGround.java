package element.ground;

import element.Ground;
import physic.PhysicalObject;
import physic.PhysicalObjectManager;
import physic.PhysicalRectangularObject;
import utils.Util;


public class LinearGround implements Ground{

	private PhysicalObject physicBody;

	
	public LinearGround(float x, float y,float width,float height) {
		
		this.physicBody = new PhysicalRectangularObject(x, y, width, height);
		PhysicalObjectManager.getInstance().buildPhysicObject(physicBody);
	}

	@Override
	public double getY() {
		return Util.toPixelPosY(physicBody.getY())-physicBody.getHeight();
	}

	@Override
	public double getX() {
		return Util.toPixelPosX(physicBody.getX());
	}

	@Override
	public double getHeight() {
		return Util.toPixelHeight(physicBody.getHeight());
	}

	@Override
	public double getWidth() {
		return Util.toPixelWidth(physicBody.getWidth());
	}
	@Override
	public PhysicalObject getPhysicObject() {
		return physicBody;
	}
}
