package physic.weapon;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

public abstract class AbstractPhysicalWeapon implements PhysicalWeapon{
	
	protected Body body;
	protected BodyDef bodyDef;
	protected Fixture fixture;
	protected FixtureDef fixtureDef;
	
	@Override
	public void setActive(boolean flag) {
		body.setActive(flag);
	}
	
	@Override
	public void setAngularVelocity(float speed) {
		body.setAngularVelocity(speed);
	}
	
	@Override
	public void setTransform(Vec2 v1, float angle) {
		body.setTransform(v1, angle);
	}
	
	@Override
	public void setLinearVelocity(Vec2 speed) {
		body.setLinearVelocity(speed);
	}
	@Override
	public Body getBody() {
		return body;
	}
	@Override
	public void addToPhisicalWorld() {
		fixture = body.createFixture(fixtureDef);
	}
	
	@Override
	public void removeToPhisicalWorld() {
		if(fixture!=null)
			body.destroyFixture(fixture);
	}
	
	@Override
	public void setSize(float width, float height) {
	}
	
}
