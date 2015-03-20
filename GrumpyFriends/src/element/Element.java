package element;

import world.Vector;

public interface Element 
{
	public abstract Vector getPosition();
	public abstract void setPosition(Vector position);
	
	public abstract int getX();
	public abstract int getY();
	
	public abstract int getHeight();
	public abstract int getWidth();
}
