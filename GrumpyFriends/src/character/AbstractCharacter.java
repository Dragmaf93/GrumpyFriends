package character;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import element.weaponsManager.Launcher;
import element.weaponsManager.Weapon;
import element.weaponsManager.WeaponsManager;
import physic.PhysicalCharacter;
import physic.PhysicalObject;
import physic.PhysicalObjectManager;
import utils.ObjectWithTimer;
import utils.Utils;
import utils.Vector;

public abstract class AbstractCharacter implements Character {

	private final static float MAX_SPEED = 10f;
	private final static float _SPEED = 15f;
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
	protected boolean moving;
	protected int currentDirection;

	protected PhysicalObject physicBody;

	protected Launcher launcher;

	protected boolean readyToEquipWeapon;
	protected boolean attacked;
	private boolean suffereDamage;

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
		PhysicalObjectManager.getInstance().buildPhysicObject(physicBody);

		launcher = new Launcher(this);

		readyToEquipWeapon = true;
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
	public boolean isSleeping() {
		return ((PhysicalCharacter) physicBody).isSleeping();
	}

	@Override
	public void equipWeapon(String weaponName) {
		if (!grounded || moving)
			return;

		if (launcher == null)
			launcher = new Launcher(this);

		if (!readyToEquipWeapon)
			return;

		if (equippedWeapon != null && equippedWeapon.getName().equals(weaponName)) {
			launcher.loadWeapon(equippedWeapon);

		} else if (weaponsManager.isAvailable(weaponName)) {

			equippedWeapon = weaponsManager.getWeapon(weaponName);
			launcher.loadWeapon(equippedWeapon);
		}
	}

	@Override
	public Launcher getLauncher() {
		return launcher;
	}

	@Override
	public void attack(float power) {
		if (launcher == null || equippedWeapon == null || !grounded || moving)
			return;

		launcher.startWeaponAttack(power);
		readyToEquipWeapon = false;

		if (equippedWeapon.finishHit()) {
			// System.out.println(equippedWeapon);
			weaponsManager.removeOneAmmunition(equippedWeapon.getName());
			equippedWeapon = null;
			attacked = true;
			// new Timer(this).start();
		}
	}

	@Override
	public Weapon getEquipWeapon() {
		return equippedWeapon;
	}

	@Override
	public void changeAim(float direction) {
		if (launcher == null || !launcher.isActivated())
			return;

		float angle = 2.0f;
		launcher.changeAngle(angle * direction);
	}

	@Override
	public Vector getAim() {
		return launcher.getPositionAim();
	}

	@Override
	public void unequipWeapon() {
		if (launcher == null)
			return;
		launcher.disable();
	}

	@Override
	public void move(int direction) {
		if (!grounded)
			return;

		if (launcher != null)
			launcher.disable();

		Body body = physicBody.getBody();
		Vec2 speed = body.getLinearVelocity();
		force = 0;
		moving = true;
		currentDirection = direction;
		switch (direction) {
		case RIGHT:
			// if (speed.x < MAX_SPEED)
			force = _SPEED;
			break;
		case LEFT:
			// if (speed.x > -MAX_SPEED)
			force = -_SPEED;
			break;
		case STOP:
			force = speed.x * -10;
			break;
		}
		if (force != 0)
			((PhysicalCharacter) physicBody).unblockWheelJoint();
		// body.applyForce(new Vec2(force, 0), body.getWorldCenter());
		body.setLinearVelocity(new Vec2(force, speed.y));
		launcher.setDirection(direction);

	}

	@Override
	public void stopToMove() {
		if (!grounded)
			return;
		Body body = physicBody.getBody();
		moving = false;
		((PhysicalCharacter) physicBody).blockWheelJoint();
		body.getLinearVelocity().x = 0f;

	}

	@Override
	public void jump() {
		Body body = physicBody.getBody();
		if (grounded) {
			if (launcher != null)
				launcher.disable();
			float impulse = body.getMass() * JUMP;
			body.applyLinearImpulse(new Vec2(0, impulse), body.getWorldCenter(), true);
		}
	}

	@Override
	public void endTurn() {
		((PhysicalCharacter) physicBody).blockWheelJoint();

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
	public double getHeight() {
		return Utils.heightFromJbox2dToJavaFx(physicBody.getHeight());
	}

	@Override
	public double getWidth() {
		return Utils.widthFromJbox2dToJavaFx(physicBody.getWidth());
	}

	@Override
	public double getY() {
		return Utils.yFromJbox2dToJavaFx(physicBody.getY());
	}

	@Override
	public double getX() {
		return Utils.xFromJbox2dToJavaFx(physicBody.getX());
	}

	@Override
	public boolean attacked() {
		return attacked;
	}

	@Override
	public PhysicalObject getPhysicObject() {
		return physicBody;
	}

	@Override
	public void prepareForTurn() {
		readyToEquipWeapon = true;
		attacked = false;
		suffereDamage = false;
	}

	@Override
	public boolean sufferedDamage() {
		return suffereDamage;
	}

}
