package element;

import physic.PhysicalObject;

public interface Element 
{
	
	public abstract double getX();
	public abstract double getY();
	
	public abstract double getHeight();
	public abstract double getWidth();
	
	public void createPhysicObject();
	public PhysicalObject getPhysicObject();
}
