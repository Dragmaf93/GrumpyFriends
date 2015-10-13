package element.character;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

import element.Weapon;
import world.AbstractDynamicElement;
import world.AbstractWorld;
import world.Utils;

public abstract class AbstractCharacter extends AbstractDynamicElement implements Character, Runnable {
	public final static int RIGHT = 1;
	public final static int LEFT = -1;

	protected String name;
	protected float height;
	protected float width;

	protected float powerJump;
	protected int lifePoints;
	protected World world;
	protected Team team;
	protected ArrayList<Weapon> weaponList;
	protected Weapon equippedWeapon;

	protected boolean inFall;
	protected boolean inMovement;
	protected boolean inFluttering;
	protected boolean inJump;
	protected boolean grounded;
	protected int currentDirection;

	protected Fixture bodyFixture;
	protected Fixture footFixture;

	public AbstractCharacter(String name, float x, float y, float height, float width, Team team,
			ArrayList<Weapon> weaponList) {
		super(x, y);

		this.name = name;
		this.team = team;
		this.weaponList = weaponList;
		this.height = height;
		this.width = width;
		
		equippedWeapon = null;
		lifePoints = 100;
		inFall = false;
		inMovement = false;
		inJump = false;

		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(width, height);
		bodyFixture = body.createFixture(polygonShape, 0);

		CircleShape circleShape = new CircleShape();
		circleShape.m_radius = width;
		circleShape.m_p.x = 0;
		circleShape.m_p.y = -height;
		footFixture = body.createFixture(circleShape, 1);
		footFixture.setUserData(name);

		body.setBullet(true);
		
	}

	@Override
	public void run() {
		while (!isDead()) {
		}
	}

	@Override
	public boolean isDead() {

		return lifePoints == 0;
	}

	@Override
	public boolean isGrounded() {
		return grounded;
	}
	
	@Override
	public void setGrounded(boolean b) {
		this.grounded=b;
	}
	
	@Override
	public boolean equipWeapon(Weapon weapon) {
		for (Weapon wea : weaponList)
			if (wea.equals(weapon)) {
				equippedWeapon = weapon;
				return true;
			}
		return false;
	}

	public void setBoolean() {
		Vec2 vec = body.getLinearVelocity();
		printState();
		if (vec.x == 0f)
			inMovement = false;
		else
			inMovement = true;

		if (vec.y == 0f)
			inJump = false;
		else
			inJump = true;
	}

//	private boolean isGrounded() {
//		Contact contact = world.getContactList();
//
//		while (contact != null) {
//
//			if (contact.isTouching()
//					&& (contact.getFixtureA() == footFixture || contact.getFixtureB() == footFixture)) {
//				Vec2 position = body.getPosition();
//				Manifold manifold = contact.getManifold();
//				boolean below = true;
//				for (int i = 0; i < manifold.pointCount; i++)
//					below &= (manifold.points[i].localPoint.y < position.y - 1.5f);
//
//				return below;
//
//			}
//			contact = contact.getNext();
//		}
//		return false;
//		return ground
//	}

	@Override
	public void move(int direction) {
//		setBoolean();
//		if ((!inJump) || (!inJump && inMovement && currentDirection != direction)) {
			currentDirection = direction;
			inMovement = true;
			body.setLinearVelocity(new Vec2(7f * currentDirection, body.getLinearVelocity().y));
		
	}

	public void printState() {
		System.out.println("Grounded : " + grounded);
		System.out.println("MOVING : " + inMovement);
		System.out.println("Is Sleeping : " + body.isActive());
		System.out.println("SPEED : " + body.getLinearVelocity());
		System.out.println();
	}

	@Override
	public void stopToMove() {
		setBoolean();
//		if (inMovement && !inJump) {
			currentDirection = 0;
			inMovement = false;
			body.getLinearVelocity().x = 0f;
//		}
	}

	@Override
	public void jump() {
		System.out.println("INIZIO JUMP");
		setBoolean();

		if (grounded) {
//			inJump = true;
			body.setLinearVelocity(new Vec2(body.getLinearVelocity().x, 15f));
		}
//		printState();
		System.out.println("FINE JUMP");
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
		return Utils.toPixelHeight(height) * 2;
	}

	@Override
	public float getWidth() {
		return Utils.toPixelWidth(width) * 2;
	}

	public ArrayList<Weapon> getWeaponList() {

		return weaponList;
	}

	public void setWeaponList(ArrayList<Weapon> weaponList) {
		this.weaponList = weaponList;
	}

}
