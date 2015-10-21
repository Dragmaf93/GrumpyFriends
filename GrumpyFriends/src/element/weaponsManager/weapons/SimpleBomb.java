package element.weaponsManager.weapons;

import element.weaponsManager.AbstractWeapon;
import element.weaponsManager.ExplosiveObject;
import physic.PhysicalObjectManager;
import physic.weapon.PhysicalBomb;
import utils.ObjectWithTimer;
import utils.Timer;
import world.Vector;

public class SimpleBomb extends AbstractWeapon implements ObjectWithTimer{

	private static final float RADIUS = 0.7f;
	public static final int NUMBER_OF_AMMUNITION = 10;
	private static final int NUMBER_OF_HIT = 1;
	private static final long SEC_TO_EXPLODE=4;
	
	public SimpleBomb() {
		physicalWeapon = new PhysicalBomb(RADIUS);
		PhysicalObjectManager.getInstance().buildPhysicWeapon(physicalWeapon);
		hit = NUMBER_OF_HIT;
	}

	@Override
	public void attack(Vector position, Vector speed, float angle) {
		
		physicalWeapon.addToPhisicalWorld();
		physicalWeapon.setActive(true);
		physicalWeapon.setAngularVelocity(0f);
		physicalWeapon.setTransform(position.toVec2(), angle);
		physicalWeapon.setLinearVelocity(speed.toVec2());
		(new Timer(this)).start();
		hit--;

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

	@Override
	public long getSecondToStopTimer() {
		return SEC_TO_EXPLODE;
	}
	@Override
	public void afterAttack() {
		PhysicalObjectManager.getInstance().removePhysicalWeapon(physicalWeapon);
		physicalWeapon=null;
		
	}
	@Override
	public void afterCountDown() {
		((ExplosiveObject) physicalWeapon).explode();
		afterAttack();
	}
}
