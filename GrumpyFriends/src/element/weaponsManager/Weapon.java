package element.weaponsManager;

import physic.weapon.PhysicalWeapon;
import world.Vector;

public interface Weapon 
{
	public abstract float getMaxDamage();
	public abstract float getMaxPower();
	public abstract PhysicalWeapon getPhysicalWeapon();
	public abstract void attack(Vector position, Vector speed, float angle);
	public abstract String getName();
	public abstract boolean finishHit();
}
