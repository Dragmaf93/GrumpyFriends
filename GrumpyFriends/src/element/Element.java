package element;

import org.jbox2d.dynamics.Body;


public interface Element 
{
	public abstract float getX();
	public abstract float getY();
	
	public abstract float getHeight();
	public abstract float getWidth();
	
	public abstract Body getBody();
}
