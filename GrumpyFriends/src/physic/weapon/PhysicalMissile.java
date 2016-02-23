package physic.weapon;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import element.weaponsManager.ExplosiveObject;
import physic.PhysicalObjectManager;
import utils.Vector;


public class PhysicalMissile extends AbstractPhysicalWeapon implements
		ExplosiveObject {

	
	private float blastPower;
	
	private float blastRadius;
	
	private float width;
	
	private float height;

	private boolean exploded;
	private Vec2 centreExplosion;

	public PhysicalMissile() {
	}

	public PhysicalMissile(float width, float height, float blastRadius,
			float maxDamage) {
		this.width = width;
		this.height = height;
		this.blastPower = 100 * blastRadius;
		this.blastRadius = blastRadius;
		this.maxDamage = maxDamage;
		centreExplosion = new Vec2(width, 0);
		bodiesToRemove = new Body[1];
	}

	@Override
	public void buildSelf(World world) {
		bodyDef = new BodyDef();

		// bodyDef.fixedRotation = true;
		bodyDef.bullet = true;
		bodyDef.setType(BodyType.DYNAMIC);
		body = world.createBody(bodyDef);

		bodiesToRemove[0] = body;
		PolygonShape missileShape = new PolygonShape();
		missileShape.setAsBox(width / 2, height / 2);
		// int count = 4;
		// Vec2[] vertices = new Vec2[count];
		// vertices[0] = new Vec2(0, 0);
		// vertices[1] = new Vec2(width * 0.7f, -height);
		// vertices[2] = new Vec2(width, 0);
		// vertices[3] = new Vec2(width * 0.7f, height);
		// missileShape.set(vertices, count);
		fixtureDef = new FixtureDef();
		fixtureDef.setShape(missileShape);
		// fixtureDef.isSensor = true;
		fixtureDef.setDensity(1.0f);
		fixtureDef.setUserData(this);
		fixtureDef.restitution = 0.0f;
		exploded = false;
	}

	@Override
	public Vec2 getCenter() {
		return body.getWorldCenter();
	}

	
	@Override
	public float getBlastPower() {

		return blastPower;
	}

	@Override
	public boolean isExplosed() {
		return exploded;
	}

	@Override
	public void explode() {
		PhysicalObjectManager.getInstance().makeAnExplosion(this);
		exploded = true;
	}

	
	@Override
	public float getBlastRadius() {
		return blastRadius;
	}

	@Override
	public float getX() {
		return body.getPosition().x - width * 0.5f;
	}

	@Override
	public float getY() {
		return body.getPosition().y + height * 0.5f;
	}

	
	@Override
	public float getHeight() {
		return height;
	}

	
	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public void update() {
		if (!exploded) {
			Vec2 flightDirection = body.getLinearVelocity();
			float angleDirection = (float) Math.atan2(flightDirection.y,
					flightDirection.x);
			body.setTransform(body.getPosition(), angleDirection);
		} else {
			body.destroyFixture(fixture);
		}
	}

	@Override
	public Body[] getBodiesToRemove() {
		return bodiesToRemove;
	}

}
