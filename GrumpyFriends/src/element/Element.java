package element;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;


public interface Element 
{
	public abstract float getX();
	public abstract float getY();
	
	public abstract float getHeight();
	public abstract float getWidth();
	
	public abstract Body getBody();
	public abstract void setBody(Body b);
	public abstract BodyDef getBodyDef();
	public abstract FixtureDef getFixtureDef();
}
