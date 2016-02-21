package character;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import element.weaponsManager.Launcher;
import element.weaponsManager.Weapon;
import element.weaponsManager.WeaponsManager;
import physic.PhysicalCharacter;
import physic.PhysicalObject;
import physic.PhysicalObjectManager;
import physic.RemovablePhysicalObject;
import utils.Utils;
import utils.Vector;
import world.World;

public abstract class AbstractCharacter implements Character {

	private final static float MAX_SPEED = 10f;
	private final static float _SPEED = 15f;
	private final static float _FORCE = 7500f;
	private final static float JUMP = 25f;

	protected String name;

	protected float height;
	protected float width;

	protected float powerJump;
	protected int lifePoints;
	protected float force;
	protected World world;
	protected Team team;

	protected boolean died;
	protected boolean isOutWorld;

	protected Weapon equippedWeapon;
	protected String lastEquippedWeaponName;

	protected WeaponsManager weaponsManager;

	protected boolean grounded;
	protected boolean moving;
	protected boolean activeLauncher;

	protected int currentDirection;

	protected PhysicalObject physicBody;

	protected Launcher launcher;

	protected boolean readyToEquipWeapon;
	protected boolean attacked;
	private boolean suffereDamage;
	private boolean endTurn;
	private int lastDamagePoints;
	private boolean jumping;
	private float startY;
	private float startX;

	private double x, y;
	private Vector speedVector;
	private boolean falling;

	public AbstractCharacter(String name, float x, float y, float height,
			float width, Team team, World world) {

		this.name = name;
		this.team = team;
		this.height = height;
		this.width = width;
		this.world = world;
		weaponsManager = new WeaponsManager();

		equippedWeapon = null;
		grounded = true;
		lifePoints = 100;

		physicBody = new PhysicalCharacter(x, y, width, height, name);
		PhysicalObjectManager.getInstance().buildPhysicObject(physicBody);

		launcher = new Launcher(this);

		speedVector = new Vector();
		readyToEquipWeapon = true;
		currentDirection = RIGHT;
	}

	public AbstractCharacter(String name, float height, float width, Team team,
			World world) {

		this.name = name;
		this.team = team;
		this.height = height;
		this.width = width;
		this.world = world;
		weaponsManager = new WeaponsManager();

		equippedWeapon = null;
		grounded = true;
		lifePoints = 100;

		launcher = new Launcher(this);
		speedVector = new Vector();

		readyToEquipWeapon = true;
		currentDirection = RIGHT;
	}

	public AbstractCharacter(String name, float height, float width, Team team) {

		this.name = name;
		this.team = team;
		this.height = height;
		this.width = width;

		weaponsManager = new WeaponsManager();

		equippedWeapon = null;
		grounded = true;
		lifePoints = 100;

		launcher = new Launcher(this);

		readyToEquipWeapon = true;
		currentDirection = RIGHT;

		speedVector = new Vector();
	}

	@Override
	public void setWorld(World battlefield) {
		world = battlefield;
	}

	@Override
	public void reset() {
		weaponsManager = new WeaponsManager();

		equippedWeapon = null;
		lastEquippedWeaponName = null;
		grounded = true;
		attacked = activeLauncher = died = jumping = suffereDamage = moving = isOutWorld = endTurn = false;
		lifePoints = 100;

		launcher = new Launcher(this);

		readyToEquipWeapon = true;
		currentDirection = RIGHT;

		if (physicBody != null) {
			PhysicalObjectManager.getInstance().removePhysicalObject(
					(RemovablePhysicalObject) physicBody);

			physicBody = new PhysicalCharacter(startX, startY, width, height,
					name);
			PhysicalObjectManager.getInstance().buildPhysicObject(physicBody);
		}
	}

	@Override
	public void setStartedPosition(float x, float y) {
		startX = x;
		startY = y;
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
		jumping = false;
		if (physicBody != null)
			((PhysicalCharacter) physicBody).blockWheelJoint();

	}

	@Override
	public boolean isSleeping() {
		if (!grounded)
			return false;

		if (speedVector.x < -0.5 || speedVector.x > 0.5)
			return false;
		if (speedVector.y < -0.5 || speedVector.y > 0.5)
			return false;

		return true;
	}

	@Override
	public int getCurrentDirection() {
		return currentDirection;
	}

	@Override
	public void equipWeapon(String weaponName) {
		if (!grounded || moving)
			return;

		if (launcher == null)
			launcher = new Launcher(this);

		if (!readyToEquipWeapon)
			return;

		if (equippedWeapon != null
				&& equippedWeapon.getName().equals(weaponName)) {
			launcher.loadWeapon(equippedWeapon);
			activeLauncher = true;
			return;

		}
		if (weaponsManager.isAvailable(weaponName)) {
			equippedWeapon = weaponsManager.getWeapon(weaponName);
			lastEquippedWeaponName = equippedWeapon.getName();
			launcher.loadWeapon(equippedWeapon);
			activeLauncher = true;
		}
	}

	@Override
	public void afterDeath() {
		if (physicBody != null)
			PhysicalObjectManager.getInstance().removePhysicalObject(
					(RemovablePhysicalObject) physicBody);
	}

	@Override
	public Launcher getLauncher() {
		return launcher;
	}

	@Override
	public String getLastEquippedWeapon() {
		return lastEquippedWeaponName;
	}

	@Override
	public void attack(float power) {
		if (launcher == null || equippedWeapon == null || !grounded || moving)
			return;

		launcher.startWeaponAttack(power);
		readyToEquipWeapon = false;

		if (equippedWeapon.finishHit()) {
			weaponsManager.removeOneAmmunition(equippedWeapon.getName());
			equippedWeapon = null;
			attacked = true;
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

		float angle = (float) Math.toRadians(2.0);
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

		if (currentDirection != direction && launcher.isActivated()) {
			launcher.disable();
			currentDirection = direction;
			launcher.setDirection(direction);
		} else {

			if (launcher.isActivated())
				launcher.disable();

			Body body = physicBody.getBody();
			// Vec2 speed = body.getLinearVelocity();
			// force = 0;
			// currentDirection = direction;
			// switch (direction) {
			// case RIGHT:
			// // if (speed.x < MAX_SPEED)
			// force = _SPEED;
			// break;
			// case LEFT:
			// // if (speed.x > -MAX_SPEED)
			// force = -_SPEED;
			// break;
			// case STOP:
			// force = speed.x * -10;
			// break;
			// }

			Vec2 vel = body.getLinearVelocity();
			float force = 0;
			moving = true;
			switch (direction) {
			case LEFT:
				if (vel.x > -10)
					force = -_FORCE;
				break;
			case STOP:
				force = vel.x * -10;
				break;
			case RIGHT:
				if (vel.x < 10)
					force = _FORCE;
				break;
			}
			body.applyForce(new Vec2(force, 0), body.getWorldCenter());
			if (force != 0 && body.getLinearVelocity().y > 0)
				((PhysicalCharacter) physicBody).unblockWheelJoint();
			// // body.applyForce(new Vec2(force, 0), body.getWorldCenter());
			// body.setLinearVelocity(new Vec2(force, speed.y));
			moving = true;
			currentDirection = direction;
			launcher.setDirection(direction);
		}

	}

	@Override
	public void stopToMove() {
		if (!grounded)
			return;
		Body body = physicBody.getBody();
		moving = false;
		((PhysicalCharacter) physicBody).blockWheelJoint();
		body.getLinearVelocity().x = 0f;

		if (activeLauncher)
			launcher.activate();

	}

	@Override
	public void createPhysicObject() {
		physicBody = new PhysicalCharacter(startX, startY, width, height, name);
		PhysicalObjectManager.getInstance().buildPhysicObject(physicBody);
	}

	@Override
	public void update() {
		if (physicBody != null) {
			x = Utils.xFromJbox2dToJavaFx(physicBody.getX());
			y = Utils.yFromJbox2dToJavaFx(physicBody.getY());
			speedVector.set(physicBody.getBody().getLinearVelocity());
		}

		if (speedVector.y < 0 && !isGrounded())
			falling = true;
		else
			falling = false;

		if (x > world.getWidth() + World.DISTANCE_WORLDS_BORDER
				|| x < -World.DISTANCE_WORLDS_BORDER
				|| y > world.getHeight() + World.DISTANCE_WORLDS_BORDER / 3)
			isOutWorld = true;

	}

	@Override
	public void jump() {
		Body body = physicBody.getBody();
		if (grounded) {
			jumping = true;
			if (launcher.isActivated())
				activeLauncher = true;

			launcher.disable();
			float impulse = body.getMass() * JUMP;
			body.applyLinearImpulse(new Vec2(0, impulse),
					body.getWorldCenter(), true);
		}
	}

	@Override
	public WeaponsManager getInventoryManager() {
		return weaponsManager;
	}

	@Override
	public void endTurn() {
		endTurn = true;
		launcher.disable();
		if (physicBody != null)
			((PhysicalCharacter) physicBody).blockWheelJoint();

	}

	@Override
	public boolean finishedTurn() {
		return endTurn;
	}

	@Override
	public int getLifePoints() {
		return lifePoints;
	}

	public World getWorld() {

		return world;
	}

	@Override
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
		return Utils.heightFromJbox2dToJavaFx(height);
		
	}

	@Override
	public double getWidth() {
		return Utils.widthFromJbox2dToJavaFx(width);
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double getX() {
		return x;
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
		endTurn = false;
		launcher.restartLauncher();

	}

	@Override
	public int getLastDamagePoints() {
		return lastDamagePoints;
	}

	@Override
	public void decreaseLifePoints(int points) {
		lastDamagePoints = points;
		lifePoints -= points;
		if (lifePoints < 0)
			lifePoints = 0;
		suffereDamage = true;
	}

	@Override
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean sufferedDamage() {
		return suffereDamage;
	}

	@Override
	public void setDied(boolean bool) {
		isOutWorld = bool;
		lifePoints = 0;
	}

	@Override
	public boolean isOutWorld() {
		return isOutWorld;
	}

	@Override
	public boolean isMoving() {
		return moving;
	}

	@Override
	public boolean isJumping() {
		return jumping;
	}

	@Override
	public boolean isFalling() {
		return falling;
	}
	
	@Override
	public boolean isActiveLauncher() {
		return activeLauncher;
	}
	
	@Override
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	@Override
	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}
	
	@Override
	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	@Override
	public void setOutWorld(boolean out) {
		this.isOutWorld = out;
	}
	
	public void setEndTurn(boolean endTurn) {
		this.endTurn = endTurn;
	};

	@Override
	public void setActiveLauncher(boolean activeLauncher) {
		this.activeLauncher = activeLauncher;
	}
}
