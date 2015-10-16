package element.weaponsManager.weapons;

import element.weaponsManager.AbstractWeapon;
import physic.PhysicalObjectCreator;
import physic.weapon.PhysicalBomb;
import world.Vector;

public class SimpleBomb extends AbstractWeapon {

	private static final float RADIUS = 0.7f;
	public static final int NUMBER_OF_AMMUNITION = 10;
	private static final int NUMBER_OF_HIT = 1;
	
	
	public SimpleBomb() {
		physicalWeapon = new PhysicalBomb(RADIUS);
		PhysicalObjectCreator.getInstance().buildPhysicWeapon(physicalWeapon);
		hit = NUMBER_OF_HIT;
	}

	@Override
	public void attack(Vector position, Vector speed, float angle) {

		physicalWeapon.addToPhisicalWorld();
		physicalWeapon.setActive(true);
		physicalWeapon.setAngularVelocity(0f);
		physicalWeapon.setTransform(position.toVec2(), angle);
		physicalWeapon.setLinearVelocity(speed.toVec2());
	}

	@Override
	public boolean finishHit() {
		
		return hit==0;
	}
	@Override
	public String getName() {
		return "SimpleBomb";
	}

	@Override
	public float getMaxDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getMaxPower() {
		// TODO Auto-generated method stub
		return 0;
	}
}
