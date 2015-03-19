package element;

import world.Vector;

public interface Element 
{
	public abstract Vector getPosition();
	public abstract void setPosition(Vector position);
	
	public abstract boolean isFluttering();
	public abstract boolean isMoving();
	
	public abstract int getX();
	public abstract int getY();
	public abstract int getHeight();
	public abstract int getWidth();
	public abstract Vector getCurrentSpeed();
	public abstract void setCurrentSpeed(Vector speed);
}
