package physic.weapon;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import element.weaponsManager.ExplosiveObject;
import physic.PhysicalObjectManager;

public class PhysicalBomb extends AbstractPhysicalWeapon  implements ExplosiveObject{

	private float radius;
	private float blastPower;
	private float blastRadius;
	private boolean exploded;
	
	public PhysicalBomb(float radius) {
		this.radius=radius;
	}
	public PhysicalBomb() {
	
	}
	
	public PhysicalBomb(float radius, float blastPower, float blastRadius){
		this.radius=radius;
		this.blastPower=blastPower;
		this.blastRadius=blastRadius;
		
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
