package element.weaponsManager;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public interface ExplosiveObject {
	
	abstract public Vec2 getCenter();
	abstract public float getBlastPower();
	abstract public void explode();
	abstract public boolean isExplosed();
	abstract public float getBlastRadius();
	public abstract Body getBody();
}
