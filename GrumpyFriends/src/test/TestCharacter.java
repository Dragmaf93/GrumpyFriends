package test;

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

import org.jbox2d.dynamics.joints.WheelJoint;
import org.jbox2d.dynamics.joints.WheelJointDef;

import element.Weapon;
import element.character.Character;

public class TestCharacter implements Character {
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
	
	
	private WheelJoint joint;
	
	public TestCharacter(String name,float x, float y) {
		this.name = name;
		this.world = TestGrumpyFriends.getInstance().getWorld();
		
		bodyDef = new BodyDef();
		bodyDef.setType(BodyType.DYNAMIC);
		bodyDef.setFixedRotation(true);
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(width, height);
		
		FixtureDef bodyFixtureDef = new FixtureDef();
		bodyFixtureDef.setShape(polygonShape);
		bodyFixtureDef.setDensity(1.0f);

		bodyDef.setPosition(new Vec2(x, y));
		bodyFixtureDef.density=1.0f;
		body = world.createBody(bodyDef);
		bodyFixture=body.createFixture(bodyFixtureDef);
	
		body.setBullet(true);
		
		feetDef = new BodyDef();
		feetDef.setType(BodyType.DYNAMIC);
		feetDef.setPosition(new Vec2(x,y-height));
		feet=world.createBody(feetDef);
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(width);
		
		FixtureDef feetFixtureDef = new FixtureDef();
		feetFixtureDef.setShape(circleShape);
		feetFixtureDef.setDensity(1.0f);
	
		feetFixtureDef.friction=FRICTION;
		feetFixture = feet.createFixture(feetFixtureDef);
		
		feetFixture.setUserData(name);		
		
		
		WheelJointDef wheelJointDef = new WheelJointDef();
	    Vec2 axis = new Vec2(1.0f, 0.0f);
	    
	    wheelJointDef.initialize(body, feet, feet.getPosition(), axis);
		
//	    wheelJointDef.motorSpeed=-10f;
		wheelJointDef.maxMotorTorque=MAX_MOTOR_TORQUE;
		wheelJointDef.collideConnected=false;
		wheelJointDef.enableMotor=true;
		wheelJointDef.dampingRatio=20f;
		wheelJointDef.frequencyHz=20f;
		joint = (WheelJoint) world.createJoint(wheelJointDef);		
		
		
		
		//bomb
		
		bombDef = new BodyDef();
		bombDef.setType(BodyType.DYNAMIC);
		bombDef.setPosition(new Vec2(x, y+5));
		CircleShape bombShape = new CircleShape();
		bombShape.setRadius(0.5f);
		bombFixtureDef = new FixtureDef();
		bombFixtureDef.setShape(bombShape);
		bombFixtureDef.setDensity(1.0f);
		
	}

	public void takeBomb(){
		if(bomb==null){
			bomb=world.createBody(bombDef);
			bomb.createFixture(bombFixtureDef);
		}
		bomb.getPosition().set(body.getPosition().x, body.getPosition().y+5);
		bomb.setAwake(false);
		
	}
	
	public void throwBomb(float speed,float angle){
		 bomb.setAwake(true);
//         bomb.setGravityScale(1);
         bomb.setAngularVelocity(0);
         bomb.setTransform( body.getWorldPoint( new Vec2(3,0) ),angle);
		bomb.setLinearVelocity(body.getWorldVector(new Vec2(speed,0) ) );
	}
	@Override
	public boolean isDead() {
		return false;
	}

	@Override
	public void move(int direction) {
		Vec2 speed = body.getLinearVelocity();
		
		force=0;
		switch (direction) {
		case RIGHT:
			if(speed.x < MAX_SPEED) 
				force = _FORCE;
			break;
		case LEFT:
			if(speed.x > -MAX_SPEED) 
				force = -_FORCE;
			break;
		case STOP:
			force = speed.x * -10;
			break;
		}
		if(force!=0)
			joint.setMaxMotorTorque(MOTOR_TORQUE);
	    body.applyForce( new Vec2(force,0), body.getWorldCenter());
	}

	@Override
	public void stopToMove() {
		joint.setMaxMotorTorque(MAX_MOTOR_TORQUE);
		currentDirection = 0;
		body.getLinearVelocity().x = 0f;
	}

	@Override
	public void jump() {
		if(grounded){
			float impulse = body.getMass()*JUMP;
		    body.applyLinearImpulse(new Vec2(0,impulse), body.getWorldCenter(), true);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getLifePoints() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean equipWeapon(Weapon weapon) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setGrounded(boolean b) {
		grounded=b;
	}

	@Override
	public boolean isGrounded() {
		return grounded;
	}
	
	public Vec2 getPosition(){
		return body.getPosition();
	}
}
