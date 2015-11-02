package physic.weapon;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

public interface PhysicalWeapon {
	
	abstract public void buildSelf(World world);
	abstract public Body getBody();
	abstract public void setActive(boolean flag);
	abstract public void setAngularVelocity(float speed);
	abstract public void setTransform(Vec2 v1, float angle);
	abstract public void setLinearVelocity(Vec2 speed);
	abstract public void addToPhisicalWorld();
	abstract public void removeToPhisicalWorld();
	
	abstract public void setSize(float width, float height);

	abstract public float getX();
	abstract public float getY();
	abstract public float getHeight();
	abstract public float getWidth();
}