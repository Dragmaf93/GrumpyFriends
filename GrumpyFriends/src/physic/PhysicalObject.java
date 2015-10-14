package physic;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;


public interface PhysicalObject{

	abstract public void buildSelf(World world);
	abstract public Body getBody();
}
