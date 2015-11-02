package element.ground;

import element.Ground;
import physic.PhysicalObject;
import physic.PhysicalObjectManager;
import physic.PhysicalRectangularObject;
import utils.Utils;


public class LinearGround implements Ground{

	private PhysicalObject physicBody;

	
	public LinearGround(float x, float y,float width,float height) {
		
		this.physicBody = new PhysicalRectangularObject(x, y, width, height);
		PhysicalObjectManager.getInstance().buildPhysicObject(physicBody);
	}

	@Override
	public double getY() {
		return Utils.yFromJbox2dToJavaFx(physicBody.getY());
	}

	@Override
	public double getX() {
		return Utils.xFromJbox2dToJavaFx(physicBody.getX());
	}

	@Override
	public double getHeight() {
		return Utils.heightFromJbox2dToJavaFx(physicBody.getHeight());
	}

	@Override
	public double getWidth() {
		return Utils.widthFromJbox2dToJavaFx(physicBody.getWidth());
	}
	@Override
	public PhysicalObject getPhysicObject() {
		return physicBody;
	}
}
