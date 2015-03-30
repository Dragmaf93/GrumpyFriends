package physicEngine;

import element.Element;
import world.Vector;

public interface MovableElement extends Element{
	
	public abstract boolean isMoving();
	public abstract void setFall(boolean fall);
	public abstract void setJump(boolean jump);
	public abstract void setMovement(boolean movement);
	
	public abstract double getTime0();
	public abstract void resetTime0();
	
	public abstract Vector getSpeed();
	public abstract void setSpeed(Vector speed);
	
	public abstract void setSpeed0(Vector speed);
	public abstract Vector getSpeed0();
	
	public abstract void setPosition0(Vector speed);
	public abstract Vector getPosition0();
	
	public abstract void afterCollision();
	public abstract void doSingleMove();
	
	
}
