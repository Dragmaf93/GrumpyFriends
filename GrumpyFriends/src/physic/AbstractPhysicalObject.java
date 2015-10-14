package physic;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;


public abstract class AbstractPhysicalObject implements PhysicalObject{
	
	protected Body body;
	protected BodyDef bodyDef;

	protected final float start_x;
	protected final float start_y;
	protected final float start_width;
	protected final float start_height;
	
	public AbstractPhysicalObject(float x, float y, float width, float height){
		bodyDef = new BodyDef();
		this.start_x=bodyDef.position.x=x;
		this.start_y=bodyDef.position.y=y;
		
		this.start_width=width;
		this.start_height=height;
	}
	
	@Override
	public Body getBody() {
		return body;
	}
}
