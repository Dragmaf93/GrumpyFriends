package physic.weapon;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
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
	
	public PhysicalBomb(float radius,float blastRadius,float maxDamage){
		this.radius=radius;
		this.blastPower=blastRadius*100;
		this.blastRadius=blastRadius;
		this.maxDamage=maxDamage;
		bodiesToRemove = new Body[1];
	}
	
	@Override
	public void buildSelf(World world) {
		bodyDef = new BodyDef();
		bodyDef.fixedRotation = true;
		bodyDef.setType(BodyType.DYNAMIC);
		
		body = world.createBody(bodyDef);
		bodiesToRemove[0]=body;
		body.setUserData("BOMBA");
		CircleShape bombShape = new CircleShape();
		bombShape.setRadius(radius);
		fixtureDef = new FixtureDef();
		fixtureDef.setShape(bombShape);
		fixtureDef.setDensity(1.0f);
		fixtureDef.restitution=0.5f;
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
		exploded=true;
	}
	
	@Override
	public float getBlastRadius() {
		return blastRadius;
	}
	@Override
	public float getX() {
		return body.getPosition().x-radius;
	}
	@Override
	public float getY() {
		return body.getPosition().y+radius;
	}
	@Override
	public float getHeight() {
		return radius;
	}
	@Override
	public float getWidth() {
		return radius;
	}
	@Override
	public void update() {
		
	}
	
	@Override
	public Body[] getBodiesToRemove() {
		return bodiesToRemove;
	}
	
	
	
}
