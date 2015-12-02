package physic;

import org.jbox2d.dynamics.Body;

public interface RemovablePhysicalObject {
	abstract public Body[] getBodiesToRemove();
}
