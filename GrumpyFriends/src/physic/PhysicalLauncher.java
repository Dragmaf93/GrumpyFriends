package physic;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import physic.weapon.PhysicalWeapon;
import sun.font.CreatedFontTracker;
import world.Vector;

public class PhysicalLauncher extends PhysicalDinamicObject {

	private final static float HEIGHT=0.2f;
	private final static float WIDTH=1.0f;
	private static final Vec2 DISTANCE_TO_LAUNCHER = new Vec2(1.2f,0);
	
	
	private RevoluteJoint joint;
	private PhysicalCharacter physicalCharacter;
	
	private Fixture fixture;
	private FixtureDef fixtureDef;
	private RevoluteJointDef revoluteJointDef;
	
	public PhysicalLauncher(PhysicalCharacter character) {
		super(character.getX(),character.getY()+character.getHeight()/2,WIDTH,HEIGHT);
		this.physicalCharacter=character;
	}

	@Override
	public void buildSelf(World world) {
		body = world.createBody(bodyDef);
		
		PolygonShape launcherShape = new PolygonShape();
		launcherShape.setAsBox(WIDTH, HEIGHT);
		
		fixtureDef = new FixtureDef();
		fixtureDef.shape=launcherShape;
		fixtureDef.density=1.0f;

//		body.createFixture(fixtureDef);	
		
		revoluteJointDef = new RevoluteJointDef();
		revoluteJointDef.bodyA=physicalCharacter.getBody();
		revoluteJointDef.bodyB=body;
		revoluteJointDef.localAnchorA.set(0,physicalCharacter.getHeight()-WIDTH*0.8f);
		revoluteJointDef.localAnchorB.set(-WIDTH,0);
		revoluteJointDef.collideConnected=false;

		revoluteJointDef.enableMotor=true;
//		revoluteJointDef.motorSpeed=2.0f;
		revoluteJointDef.maxMotorTorque=500000000000000000.0f;
	
		revoluteJointDef.referenceAngle=(float) Math.toRadians(0);
		revoluteJointDef.enableLimit = true;
		revoluteJointDef.lowerAngle = (float) Math.toRadians(-70);
		revoluteJointDef.upperAngle =  (float) Math.toRadians(250);
		joint=(RevoluteJoint) world.createJoint(revoluteJointDef);
		
		
	}
	
	public void hide(){
		if(fixture!=null)
			body.destroyFixture(fixture);
		fixture=null;
	}
	
	public void expose(){
//		if(fixture==null)
			fixture=body.createFixture(fixtureDef);		
	}
	
	public Vector getPositionWeapon(){
		Vec2 v=body.getWorldPoint(DISTANCE_TO_LAUNCHER);
		return new Vector(v.x, v.y);
	}
	public RevoluteJoint getJoint() {
		return joint;
	}

	public Vector getVectorSpeed(float speed) {
		Vec2 v=body.getWorldVector(new Vec2(speed, 0));
		return new Vector(v.x, v.y);
	}

	public float getAngle() {
		return body.getAngle();
	}

	public void rotate(float speed) {
		joint.setMotorSpeed(speed);
	}

}
