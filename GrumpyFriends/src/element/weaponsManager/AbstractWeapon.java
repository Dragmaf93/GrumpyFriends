package element.weaponsManager;

import physic.weapon.PhysicalWeapon;

public abstract class AbstractWeapon implements Weapon {

	protected PhysicalWeapon physicalWeapon;
	protected int hit;

	@Override
	public PhysicalWeapon getPhysicalWeapon() {
		return  physicalWeapon;
	}
}
