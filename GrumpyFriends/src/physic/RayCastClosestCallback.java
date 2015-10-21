package physic;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

public class RayCastClosestCallback implements RayCastCallback {

	public Body body;
	public Vec2 point;
	
	public RayCastClosestCallback() {
		body=null;
	}
	
	@Override
	public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal, float fraction) {
		body=fixture.m_body;
		this.point=point;
		return fraction;
	}

}
