package character;



import org.jbox2d.common.Vec2;

import element.Element;
import element.weaponsManager.Launcher;
import element.weaponsManager.Weapon;
import element.weaponsManager.WeaponsManager;
import physic.PhysicalObject;
import utils.Vector;

public interface Character extends Element
{
	public final static int RIGHT = 1;
	public final static int LEFT = -1;
	
	public final static int STOP = 0;
	public final static int INCREASE =1;
	public final static int DECREASE =-1;
	public final static int MAX_HEIGHT=5;
	
	public abstract boolean isDead();
	
	
	public abstract void move(int direction);
	public abstract int getCurrentDirection();
	public abstract void stopToMove();
	public abstract void jump();
	public abstract String getName();
	public abstract int getLifePoints();
	
	public abstract Launcher getLauncher();
	public abstract void equipWeapon(String weaponName);
	public abstract void unequipWeapon();
	public abstract void attack(float power);
	public abstract void changeAim(float direction);
	public abstract Vector getAim();
	
	public abstract String getLastEquippedWeapon();
	
	public abstract void setGrounded(boolean b);
	public abstract boolean isGrounded();

	public abstract boolean attacked();
	
	public abstract void prepareForTurn();
	public abstract void decreaseLifePoints(int points);
	
	public abstract boolean sufferedDamage();
	public abstract void endTurn();
	abstract public PhysicalObject getPhysicalObject();

	public abstract Weapon getEquipWeapon();

	public abstract boolean finishedTurn();

	public abstract boolean isSleeping();

	public abstract int getLastDamagePoints();

	public abstract WeaponsManager getInventoryManager();

	public abstract Team getTeam();
	
	public abstract void afterDeath();

	
}
