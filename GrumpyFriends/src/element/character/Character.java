package element.character;


import org.jbox2d.common.Vec2;

import element.weaponsManager.Weapon;
import physic.PhysicalObject;

public interface Character 
{
	public final static int RIGHT = 1;
	public final static int LEFT = -1;
	
	public final static int STOP = 0;
	public final static int INCREASE =1;
	public final static int DECREASE =-1;
	public final static int MAX_HEIGHT=5;
	
	public abstract boolean isDead();
	
	
	public abstract void move(int direction);
	public abstract void stopToMove();
	public abstract void jump();
	public abstract String getName();
	public abstract int getLifePoints();
	
	public abstract void equipWeapon(String weaponName);
	public abstract void unequipWeapon();
	public abstract void attack(float power);
	public abstract void changeAngle(float direction);
	
	
	public abstract void setGrounded(boolean b);
	public abstract boolean isGrounded();

	public abstract void endTurn();
	abstract public PhysicalObject getPhysicalObject();


	Vec2 getPositionTest();


	public abstract Weapon getEquipWeapon();

	

	
}
