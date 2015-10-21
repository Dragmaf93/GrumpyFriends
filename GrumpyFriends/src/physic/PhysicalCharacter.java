package physic;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.WheelJoint;
import org.jbox2d.dynamics.joints.WheelJointDef;

public class PhysicalCharacter extends AbstractPhysicalObject{
	
	private final static float FEET_FRICTION=5.0f;
	private final static float MOTOR_TORQUE=20f;
	
	private Body feet;
	private Fixture bodyFixture;
	private Fixture feetFixture;
	private WheelJoint joint;
	

	private String nameCharacter;
	
	public PhysicalCharacter(float x, float y, float width, float height,String nameCharacter) {
		super(x, y, width, height);		
		this.nameCharacter=nameCharacter;
		System.out.println(body +"     "+feet);
	}

	@Override
	public void buildSelf(World world) {

		bodyDef.setType(BodyType.DYNAMIC);
		bodyDef.setFixedRotation(true);
		body = world.createBody(bodyDef);
		body.setBullet(true);
		
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(start_width, start_height);
		
		FixtureDef bodyFixtureDef = new FixtureDef();
		bodyFixtureDef.setShape(polygonShape);
		bodyFixtureDef.setDensity(1.0f);

		bodyFixture=body.createFixture(bodyFixtureDef);

		bodyDef.setPosition(new Vec2(start_x,start_y- start_height));
		bodyDef.setFixedRotation(false);
		feet=world.createBody(bodyDef);
		
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(start_width);
		
		FixtureDef feetFixtureDef = new FixtureDef();
		feetFixtureDef.setShape(circleShape);
		feetFixtureDef.setDensity(1.0f);
	
		feetFixtureDef.friction=FEET_FRICTION;
		feetFixture = feet.createFixture(feetFixtureDef);
		
		feetFixture.setUserData(nameCharacter);		
		
		WheelJointDef wheelJointDef = new WheelJointDef();
	    Vec2 axis = new Vec2(1.0f, 0.0f);
	    
	    wheelJointDef.initialize(body, feet, feet.getPosition(), axis);
		
		wheelJointDef.maxMotorTorque=MAX_MOTOR_TORQUE;
		wheelJointDef.collideConnected=false;
		wheelJointDef.enableMotor=true;
		wheelJointDef.dampingRatio=20f;
		wheelJointDef.frequencyHz=20f;
		joint =  (WheelJoint) world.createJoint(wheelJointDef);		
	}
	
	public void unblockWheelJoint(){
		joint.setMaxMotorTorque(MOTOR_TORQUE);
	}
	public void blockWheelJoint(){
		joint.setMaxMotorTorque(MAX_MOTOR_TORQUE);
	}
	
}
