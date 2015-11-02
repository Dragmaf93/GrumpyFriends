package test;

import java.util.Vector;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.jbox2d.dynamics.joints.WheelJoint;
import org.jbox2d.dynamics.joints.WheelJointDef;

import com.sun.org.apache.bcel.internal.generic.LUSHR;

import character.Character;
import element.weaponsManager.Weapon;
import physic.PhysicalObject;

public class TestCharacter {
	public final static int RIGHT = 1;
	public final static int LEFT = -1;
	public final static int STOP = 0;

	private final static float MAX_SPEED=10f;
	private final static float MAX_MOTOR_TORQUE=200f;
	private final static float MOTOR_TORQUE=20f;
	private final static float JUMP =15f;
	private final static float FRICTION=5f;
	
	private Body body;
	private BodyDef bodyDef;
	private Fixture bodyFixture;
	private Fixture feetFixture;
	private Fixture bombFixture;
	
	public RevoluteJoint launcherJoint;
	
	Body launcher;
	
	Body feet;
	private BodyDef feetDef;
	
	private World world;
	private String name;
	private float width=1f;
	private float height=3f;
	
	private final static float _FORCE = 500f; 
	private float force;
	private boolean grounded=true;
	private int currentDirection;
	
	private BodyDef bombDef;
	private FixtureDef bombFixtureDef;
	
	private Body bomb;
	
	
//	private WheelJoint joint;
//	@Override
//	public void endTurn() {
//		// TODO Auto-generated method stub
//		
//	}
//	public TestCharacter(String name,float x, float y) {
//		this.name = name;
//		this.world = TestGrumpyFriends.getInstance().getWorld();
//		
//		bodyDef = new BodyDef();
//		bodyDef.setType(BodyType.DYNAMIC);
//		bodyDef.setFixedRotation(true);
//		PolygonShape polygonShape = new PolygonShape();
//		polygonShape.setAsBox(width, height);
//		
//		FixtureDef bodyFixtureDef = new FixtureDef();
//		bodyFixtureDef.setShape(polygonShape);
//		bodyFixtureDef.setDensity(1.0f);
//
//		bodyDef.setPosition(new Vec2(x, y));
//		bodyFixtureDef.density=1.0f;
//		body = world.createBody(bodyDef);
//		bodyFixture=body.createFixture(bodyFixtureDef);
//	
//		body.setBullet(true);
//		
//		feetDef = new BodyDef();
//		feetDef.setType(BodyType.DYNAMIC);
//		feetDef.setPosition(new Vec2(x,y-height));
//		feet=world.createBody(feetDef);
//		CircleShape circleShape = new CircleShape();
//		circleShape.setRadius(width);
//		
//		FixtureDef feetFixtureDef = new FixtureDef();
//		feetFixtureDef.setShape(circleShape);
//		feetFixtureDef.setDensity(1.0f);
//	
//		feetFixtureDef.friction=FRICTION;
//		feetFixture = feet.createFixture(feetFixtureDef);
//		
//		feetFixture.setUserData(name);		
//		
//		
//		WheelJointDef wheelJointDef = new WheelJointDef();
//	    Vec2 axis = new Vec2(1.0f, 0.0f);
//	    
//	    wheelJointDef.initialize(body, feet, feet.getPosition(), axis);
//		
////	    wheelJointDef.motorSpeed=-10f;
//		wheelJointDef.maxMotorTorque=MAX_MOTOR_TORQUE;
//		wheelJointDef.collideConnected=false;
//		wheelJointDef.enableMotor=true;
//		wheelJointDef.dampingRatio=20f;
//		wheelJointDef.frequencyHz=20f;
//		joint = (WheelJoint) world.createJoint(wheelJointDef);		
//		
//		BodyDef launcherDef = new BodyDef();
//		launcherDef.setPosition(new Vec2(x,y+height/2));
//		launcherDef.setType(BodyType.DYNAMIC);
//		launcher=world.createBody(launcherDef);
//		
//		PolygonShape launcherShape = new PolygonShape();
//		launcherShape.setAsBox(1.5f, 0.2f);
////		CircleShape launcherShape = new CircleShape();
////		launcherShape.setRadius(width+0.5f);
//		FixtureDef launcherFixuterDef = new FixtureDef();
//		launcherFixuterDef.shape=launcherShape;
//		launcherFixuterDef.density=1.0f;
//		launcher.createFixture(launcherFixuterDef);	
////		
////		Body bodyLauncher=world.createBody(launcherDef);
////		CircleShape circleLauncher = new CircleShape();
////		circleLauncher.setRadius(0.2f);
////		launcherFixuterDef.shape=circleLauncher;
////		bodyLauncher.createFixture(launcherFixuterDef);
//		
//		RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
//		revoluteJointDef.bodyA=body;
//		revoluteJointDef.bodyB=launcher;
//		revoluteJointDef.localAnchorA.set(0,height/2);
//		revoluteJointDef.localAnchorB.set(-1.5f,0);
//		revoluteJointDef.collideConnected=false;
//
//		revoluteJointDef.enableMotor=true;
//		revoluteJointDef.maxMotorTorque=50000000000f;
//	
//		revoluteJointDef.referenceAngle=(float) Math.toRadians(0);
//		revoluteJointDef.enableLimit = true;
//		revoluteJointDef.lowerAngle = (float) Math.toRadians(-70);
//		revoluteJointDef.upperAngle =  (float) Math.toRadians(250);
//		launcherJoint=(RevoluteJoint) world.createJoint(revoluteJointDef);
//		
////		RevoluteJointDef launcherJointDef=new RevoluteJointDef();
////		launcherJointDef.bodyA=body;
////		launcherJointDef.bodyB=bodyLauncher;
////		launcherJointDef.localAnchorA.set(0,height/2);
////		launcherJointDef.localAnchorB.set(0,0);
////		launcherJointDef.collideConnected=false;
////		
////		world.createJoint(launcherJointDef);
//		//bomb
//		
//		bombDef = new BodyDef();
//		bombDef.fixedRotation=true;
//		bombDef.setType(BodyType.DYNAMIC);
//		bombDef.setPosition(new Vec2(x, y));
//		CircleShape bombShape = new CircleShape();
//		bombShape.setRadius(0.5f);
//		bombFixtureDef = new FixtureDef();
//		bombFixtureDef.setShape(bombShape);
//		bombFixtureDef.setDensity(1.0f);
//		bomb=world.createBody(bombDef);
//		
//	}
//
//	public void takeBomb(){
//
//		
//	}
//	public void changeAngleLauncher(float s){
//		launcherJoint.setMotorSpeed(s);
//	}
//	
//	public void throwBomb(float speed){
//		if(bombFixture!=null)
//			bomb.destroyFixture(bombFixture);
//		
//		bombFixture = bomb.createFixture(bombFixtureDef);
//		System.out.println(bomb.getMass()+" "+bombFixture.getDensity());
//		 bomb.setActive(true);
////         bomb.setGravityScale(1);
//         bomb.setAngularVelocity(0);
//         bomb.setTransform(launcher.getWorldPoint(new Vec2(2,0)),launcher.getAngle());
//		bomb.setLinearVelocity(launcher.getWorldVector(new Vec2(speed,0) ) );
//		
//	}
//	
//	@Override
//	public boolean isDead() {
//		return false;
//	}
//
//	@Override
//	public void move(int direction) {
//		Vec2 speed = body.getLinearVelocity();
//		
//		force=0;
//		switch (direction) {
//		case RIGHT:
//			if(speed.x < MAX_SPEED) 
//				force = _FORCE;
//			break;
//		case LEFT:
//			if(speed.x > -MAX_SPEED) 
//				force = -_FORCE;
//			break;
//		case STOP:
//			force = speed.x * -10;
//			break;
//		}
//		if(force!=0)
//			joint.setMaxMotorTorque(MOTOR_TORQUE);
//	    body.applyForce( new Vec2(force,0), body.getWorldCenter());
//	}
//
//	@Override
//	public void stopToMove() {
//		joint.setMaxMotorTorque(MAX_MOTOR_TORQUE);
//		currentDirection = 0;
//		body.getLinearVelocity().x = 0f;
//	}
//
//	@Override
//	public void jump() {
//		if(grounded){
//			float impulse = body.getMass()*JUMP;
//		    body.applyLinearImpulse(new Vec2(0,impulse), body.getWorldCenter(), true);
//		}
//	}
//
//	@Override
//	public String getName() {
//		return name;
//	}
//
//	@Override
//	public int getLifePoints() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void equipWeapon(String weaponName) {
//		// TODO Auto-generated method stub
//	}
//
//	@Override
//	public void setGrounded(boolean b) {
//		grounded=b;
//	}
//
//	@Override
//	public boolean isGrounded() {
//		return grounded;
//	}
//	
//	public Vec2 getPosition(){
//		return body.getPosition();
//	}
//
//	@Override
//	public PhysicalObject getPhysicalObject() {
//		return null;
//	}
//
//	@Override
//	public void unequipWeapon() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void attack(float power) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void changeAim(float direction) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public Vec2 getPositionTest() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Weapon getEquipWeapon() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public double getX() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//	@Override
//	public double getY() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//	@Override
//	public double getHeight() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//	@Override
//	public double getWidth() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//	@Override
//	public PhysicalObject getPhysicObject() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public utils.Vector getAim() {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
