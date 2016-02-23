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


public class PhysicalCharacter extends AbstractPhysicalObject implements RemovablePhysicalObject{
	
	private final static float FEET_FRICTION=100.0f;
	private final static float MOTOR_TORQUE=20f;
	private final static float DENSITY = 10f;

	private Body feet;
	private Fixture bodyFixture;
	private Fixture feetFixture;
	private WheelJoint joint;
	
	
	private Body[] bodiesToRemove;

	private String nameCharacter;
	private Vec2 vec;
	
	public PhysicalCharacter(float x, float y, float width, float height,String nameCharacter) {
		super(x, y, width, height);		
		this.nameCharacter=nameCharacter;
		vec = new Vec2(-width/2,(height-width/2)/2); 
		bodiesToRemove= new Body[2];
	}

	@Override
	public void buildSelf(World world) {

		float bodyHeight = start_height-start_width/2;
		
		bodyDef.setType(BodyType.DYNAMIC);
		bodyDef.setFixedRotation(true);
		body = world.createBody(bodyDef);
		body.setBullet(true);
		
		PolygonShape polygonShape = new PolygonShape();
		int count = 4;

		polygonShape.setAsBox(start_width/2, bodyHeight/2);

		FixtureDef bodyFixtureDef = new FixtureDef();
		bodyFixtureDef.setShape(polygonShape);
		bodyFixtureDef.setDensity(DENSITY);

		bodyFixtureDef.setUserData(nameCharacter+"Body");
		bodyFixture=body.createFixture(bodyFixtureDef);
		bodyDef.setPosition(new Vec2(start_x,start_y-bodyHeight/2));
		bodyDef.setFixedRotation(false);
		feet=world.createBody(bodyDef);
		
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(start_width/2);
		
		FixtureDef feetFixtureDef = new FixtureDef();
		feetFixtureDef.setShape(circleShape);
		feetFixtureDef.setDensity(DENSITY);
	
		bodiesToRemove[0]=body;
		bodiesToRemove[1]=feet;
		
		feetFixtureDef.friction=FEET_FRICTION;
		feetFixture = feet.createFixture(feetFixtureDef);
		feetFixture.setUserData(nameCharacter);		
		
		body.setUserData(nameCharacter);
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
	
	@Override
	public float getX() {
		return body.getWorldPoint(vec).x;
	}
	@Override
	public float getY() {
		return body.getWorldPoint(vec).y;
	}
	public void unblockWheelJoint(){
		joint.setMaxMotorTorque(MOTOR_TORQUE);
	}
	public void blockWheelJoint(){
		joint.setMaxMotorTorque(MAX_MOTOR_TORQUE);
	}

	public boolean isSleeping() {
		return !body.isAwake();
	}

	
	@Override
	public Body[] getBodiesToRemove() {
		return bodiesToRemove;
	}
	
}
