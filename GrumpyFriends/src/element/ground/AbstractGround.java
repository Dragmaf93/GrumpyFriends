package element.ground;

import java.util.List;

import element.Ground;
import physic.PhysicalObject;
import physic.PhysicalObjectManager;
import utils.Point;


public abstract class AbstractGround implements Ground{
	
	
	protected List<Point> points;
	protected double positionX;
	protected double positionY;
	
	
	public AbstractGround() {
	
	}
	
	public AbstractGround(List<Point> points){
		this.points = points;
		setPosition();
		setSize();
	}
	
	
	protected double width;
	
	protected double height;
	
	
	protected PhysicalObject physicalObject;
	
	
	@Override
	public double getHeight() {
		return height;
	}
	
	@Override
	public double getX() {
		return positionX;
	}

	
	@Override
	public double getWidth() {
		return width;
	}
	
	@Override
	public double getY() {
		return positionY;
	}
	@Override
	public void createPhysicObject() {
		PhysicalObjectManager.getInstance().buildPhysicObject(physicalObject);
	}
	@Override
	public PhysicalObject getPhysicObject() {
		return physicalObject;
	}
	
	
	@Override
	public List<Point> getPoints() {
		return points;
	}
}

