package physic.weapon;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class PhysicalBomb extends AbstractPhysicalWeapon {

	private float radius;

	public PhysicalBomb(float radius) {
		this.radius=radius;
	}
	public PhysicalBomb() {
	
	}
	
	@Override
	public void buildSelf(World world) {
		bodyDef = new BodyDef();
		bodyDef.fixedRotation = true;
		bodyDef.setType(BodyType.DYNAMIC);
		body = world.createBody(bodyDef);

		CircleShape bombShape = new CircleShape();
		bombShape.setRadius(radius);
		fixtureDef = new FixtureDef();
		fixtureDef.setShape(bombShape);
		fixtureDef.setDensity(1.0f);
		fixtureDef.restitution=0.5f;
	}
	
	
	
	
	
}
