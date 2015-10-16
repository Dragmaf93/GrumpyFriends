package physic;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;


public interface PhysicalObject{

	static final float MAX_MOTOR_TORQUE=9999999999999999999999999999999999f;

	abstract public void buildSelf(World world);
	abstract public Body getBody();
	abstract public float getX();
	abstract public float getY();
	abstract public float getHeight();
	abstract public float getWidth();
}
