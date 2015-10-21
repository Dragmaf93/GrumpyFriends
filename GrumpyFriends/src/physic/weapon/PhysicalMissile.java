package physic.weapon;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import element.weaponsManager.ExplosiveObject;
import physic.PhysicalObjectManager;

public class PhysicalMissile extends AbstractPhysicalWeapon implements ExplosiveObject {

	private float blastPower;
	private float blastRadius;
	private float width;
	private float height;
	
	private boolean exploded;

	public PhysicalMissile() {
	}

	public PhysicalMissile(float width, float height, float blastPower, float blastRadius) {
		this.width = width;
		this.height = height;
		this.blastPower = blastPower;
		this.blastRadius = blastRadius;
	}

	@Override
	public void buildSelf(World world) {
		bodyDef = new BodyDef();
		// bodyDef.fixedRotation = true;
		bodyDef.bullet = true;
		bodyDef.setType(BodyType.DYNAMIC);
		body = world.createBody(bodyDef);
		PolygonShape missileShape = new PolygonShape();
		// missileShape.setAsBox(width,height
		int count = 4;
		Vec2[] vertices = new Vec2[count];
		vertices[0] = new Vec2(0, 0);
		vertices[1] = new Vec2(width * 0.7f, -height);
		vertices[2] = new Vec2(width, 0);
		vertices[3] = new Vec2(width * 0.7f, height);
		missileShape.set(vertices, count);

		fixtureDef = new FixtureDef();
		fixtureDef.setShape(missileShape);
		fixtureDef.setDensity(1.0f);
		fixtureDef.setUserData(this);
		fixtureDef.restitution = 0.0f;
	}

	@Override
	public Vec2 getCenter() {
		return body.getPosition();
	}

	@Override
	public float getBlastPower() {

		return blastPower;
	}

	@Override
	public boolean isExplosed() {
		// TODO Auto-generated method stub
		return exploded;
	}

	@Override
	public void explode() {
		PhysicalObjectManager.getInstance().makeAnExplosion(this);
		exploded=true;
	}
	
	@Override
	public float getBlastRadius() {
		return blastRadius;
	}

}
