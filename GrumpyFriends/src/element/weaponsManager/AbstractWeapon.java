package element.weaponsManager;

import physic.weapon.PhysicalWeapon;

public abstract class AbstractWeapon implements Weapon {

	protected PhysicalWeapon physicalWeapon;
	protected int hit;

	protected boolean attacked;
	
	@Override
	public PhysicalWeapon getPhysicalWeapon() {
		return  physicalWeapon;
	}
	
	@Override
	public boolean attacked() {
		return attacked;
	}
	
	
}
