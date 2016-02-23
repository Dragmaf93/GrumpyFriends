package element.weaponsManager;

import physic.weapon.PhysicalWeapon;
import utils.Vector;


public interface Weapon 
{
	
	public abstract float getMaxDamage();
	public abstract double getBlastRadius();
	
	public abstract PhysicalWeapon getPhysicalWeapon();
	public abstract void attack(Vector position, Vector speed, float angle);
	public abstract void afterAttack();
	
	public abstract String getName();
	
	public abstract void createPhysicWeapon();
	
	public abstract double getDistanceFromLauncher();
	public abstract boolean finishHit();
	
	public abstract double getX();
	
	public abstract double getY();
	public abstract double getHeight();
	public abstract double getWidth();
	public abstract boolean attacked();
	
	public abstract void update();
	public abstract void reset();
	
	
	public abstract double getAngle();
	public abstract void setAttack(boolean b);
	public abstract void decreaseHit();
	
	public abstract void setX(double x);
	
	public abstract void setY(double y);
	
	public abstract void setAngle(double angle);
	
	public abstract boolean isExploded();
	
	public abstract void setExploded(boolean bool);
}
