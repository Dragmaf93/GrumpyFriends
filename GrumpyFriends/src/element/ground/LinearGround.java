package element.ground;

import java.util.List;

import element.Ground;
import physic.PhysicalObject;
import physic.PhysicalObjectManager;
import physic.PhysicalRectangularObject;
import utils.Point;
import utils.Utils;
import utils.Vector;


public class LinearGround implements Ground{

	private PhysicalObject physicBody;
	private List<Point> points;
	
	public LinearGround(List<Vector> points){
		
		this.physicBody = new PhysicalPolygonObject(points);
		PhysicalObjectManager.getInstance().buildPhysicObject(physicBody);
	}
	
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

	@Override
	public List<Point> getPoint() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
