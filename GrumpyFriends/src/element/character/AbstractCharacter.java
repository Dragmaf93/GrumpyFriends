package element.character;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import element.Element;
import element.weaponsManager.Launcher;
import element.weaponsManager.Weapon;
import element.weaponsManager.WeaponsManager;
import physic.PhysicalCharacter;
import physic.PhysicalObject;
import physic.PhysicalObjectCreator;

public abstract class AbstractCharacter implements Character, Element {

	private final static float MAX_SPEED = 10f;
	private final static float _FORCE = 500f;
	private final static float JUMP = 15f;

	protected String name;
	protected float height;
	protected float width;

	protected float powerJump;
	protected int lifePoints;
	protected float force;
	protected World world;
	protected Team team;
	protected Weapon equippedWeapon;
	
	protected WeaponsManager weaponsManager;

	protected boolean grounded;
	protected int currentDirection;

	protected PhysicalObject physicBody;
	
	protected Launcher launcher;

	public AbstractCharacter(String name, float x, float y, float height, float width, Team team) {

		this.name = name;
		this.team = team;
		this.height = height;
		this.width = width;
		
		weaponsManager = new WeaponsManager();

		equippedWeapon = null;
		grounded = true;
		lifePoints = 100;

		physicBody = new PhysicalCharacter(x, y, width, height, name);
		PhysicalObjectCreator.getInstance().buildPhysicObject(physicBody);
	}

	@Override
	public boolean isDead() {

		return lifePoints == 0;
	}

	@Override
	public PhysicalObject getPhysicalObject() {
		return physicBody;
	}

	@Override
	public boolean isGrounded() {
		return grounded;
	}

	@Override
	public void setGrounded(boolean b) {
		this.grounded = b;
	}

	@Override
	public void equipWeapon(String weaponName) {
		if(launcher==null)
			launcher = new Launcher(this);
		
		if(weaponsManager.isAvailable(weaponName)){
			
			equippedWeapon=weaponsManager.getWeapon(weaponName);
			launcher.loadWeapon(equippedWeapon);
		}
	}
	@Override
	public void attack(float power) {
		if(launcher==null || equippedWeapon==null) return;
		
		launcher.startWeaponAttack(power);
		weaponsManager.removeOneAmmunition(equippedWeapon.getName());
	}
	
	@Override
	public void changeAngle(float direction) {
		if(launcher==null || !launcher.isActivated())
			return;
		
		float angle = 2.0f;
		launcher.changeAngle(angle*direction);
	}
	
	@Override
	public void unequipWeapon() {
		if(launcher==null) return;
		launcher.disable();
	}
	
	@Override
	public void move(int direction) {
		Body body = physicBody.getBody();
		Vec2 speed = body.getLinearVelocity();
		force = 0;
		switch (direction) {
		case RIGHT:
			if (speed.x < MAX_SPEED)
				force = _FORCE;
			break;
		case LEFT:
			if (speed.x > -MAX_SPEED)
				force = -_FORCE;
			break;
		case STOP:
			force = speed.x * -10;
			break;
		}
		if (force != 0)
			((PhysicalCharacter) physicBody).unblockWheelJoint();
		body.applyForce(new Vec2(force, 0), body.getWorldCenter());

	}

	@Override
	public void stopToMove() {
		Body body = physicBody.getBody();

		((PhysicalCharacter) physicBody).blockWheelJoint();
		currentDirection = 0;
		body.getLinearVelocity().x = 0f;

	}

	@Override
	public void jump() {
		Body body = physicBody.getBody();
		if (grounded) {
			float impulse = body.getMass() * JUMP;
			body.applyLinearImpulse(new Vec2(0, impulse), body.getWorldCenter(), true);
		}
	}

	@Override
	public int getLifePoints() {
		return lifePoints;
	}

	public World getWorld() {

		return world;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public float getHeight() {
		return 0f;
	}

	@Override
	public float getWidth() {
		return 0f;
	}

	@Override
	public Vec2 getPositionTest() {
		
		return new Vec2(physicBody.getX(),physicBody.getY());
	}
	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PhysicalObject getPhysicObject() {
		return physicBody;
	}

}
