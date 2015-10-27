package element.weaponsManager.weapons;

import element.weaponsManager.AbstractWeapon;
import physic.DragForceThread;
import physic.PhysicalObjectManager;
import physic.weapon.PhysicalMissile;
import utils.Vector;

public class SimpleMissile extends AbstractWeapon{
	
	private static final float WIDTH = 4.0f;
	private static final float HEIGHT = 1.0f;
	public static final int NUMBER_OF_AMMUNITION = 100;
	private static final int NUMBER_OF_HIT = 1;
//	private static final long SEC_TO_EXPLODE=4;
	private static final float BLAST_POWER = 10000f;
	private static final float BLAST_RADIUS = 10f;
	
	public SimpleMissile() {
		physicalWeapon=new PhysicalMissile(WIDTH, HEIGHT, BLAST_RADIUS);
		PhysicalObjectManager.getInstance().buildPhysicWeapon(physicalWeapon);
		hit=NUMBER_OF_HIT;
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
	public void attack(Vector position, Vector speed, float angle) {
		
		physicalWeapon.addToPhisicalWorld();
		physicalWeapon.setActive(true);
		physicalWeapon.setAngularVelocity(0f);
		physicalWeapon.setTransform(position.toVec2(), angle);
		physicalWeapon.setLinearVelocity(speed.toVec2());	
		new DragForceThread(this).start();
		hit--;
	}
	
	@Override
	public void afterAttack() {
		PhysicalObjectManager.getInstance().removePhysicalWeapon(physicalWeapon);
		physicalWeapon=null;
		
	}
	@Override
	public String getName() {
		return "SimpleMissile";
	}

	@Override
	public boolean finishHit() {
		return hit==0;
	}
}
