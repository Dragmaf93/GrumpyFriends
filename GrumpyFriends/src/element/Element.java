package element;

import physic.PhysicalObject;

public interface Element 
{
	public abstract float getX();
	public abstract float getY();
	
	public abstract float getHeight();
	public abstract float getWidth();
	
	public PhysicalObject getPhysicObject();
}
