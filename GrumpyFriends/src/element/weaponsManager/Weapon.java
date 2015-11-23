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
	
	public abstract double getDistanceFromLauncher();
	public abstract boolean finishHit();
	public abstract double getX();
	public abstract double getY();
	public abstract double getHeight();
	public abstract double getWidth();
	public abstract boolean attacked();
	
	public abstract void update();
}
