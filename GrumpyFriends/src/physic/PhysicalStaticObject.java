package physic;

import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

public abstract class PhysicalStaticObject extends AbstractPhysicalObject {

	public PhysicalStaticObject(float x, float y, float width, float height) {
		super(x, y, width, height);
		bodyDef=new BodyDef();
		
		bodyDef.position.x=start_x;
		bodyDef.position.y=start_y;
		bodyDef.type = BodyType.STATIC;		
	}
}
