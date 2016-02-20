package element.weaponsManager.weapons;

import element.weaponsManager.AbstractWeapon;
import element.weaponsManager.ExplosiveObject;
import physic.PhysicalObjectManager;
import physic.weapon.PhysicalMissile;
import utils.Utils;
import utils.Vector;
import physic.DragForceThread;

public class SimpleMissile extends AbstractWeapon {
	
	private static final float WIDTH = 2.5f;
	private static final float HEIGHT = 1.0f;
	public static final int NUMBER_OF_AMMUNITION = 100;
	private static final int NUMBER_OF_HIT = 1;
	// private static final long SEC_TO_EXPLODE=4;
	private static final float BLAST_POWER = 10000f;
	private static final float BLAST_RADIUS = 10f;
	private static final float MAX_DAMAGE = 70f;
	
	public SimpleMissile() {
		physicalWeapon = new PhysicalMissile(WIDTH, HEIGHT, BLAST_RADIUS, MAX_DAMAGE);
		PhysicalObjectManager.getInstance().buildPhysicWeapon(physicalWeapon);
		hit = NUMBER_OF_HIT;
	}
	
	@Override
	public float getMaxDamage() {
		return MAX_DAMAGE;
	}
	
	@Override
	public void reset() {
		hit= NUMBER_OF_HIT;
		attacked=false;
		PhysicalObjectManager.getInstance().buildPhysicWeapon(physicalWeapon);
		
	}
	
	@Override
	public double getBlastRadius() {
		return Utils.widthFromJbox2dToJavaFx(BLAST_RADIUS);
	}
	
	@Override
	public void attack(Vector position, Vector speed, float angle) {
		attacked = true;
		physicalWeapon.addToPhisicalWorld();
		physicalWeapon.setActive(true);
		physicalWeapon.setAngularVelocity(0f);
		physicalWeapon.setTransform(position.toVec2(), angle);
		physicalWeapon.setLinearVelocity(speed.toVec2());
		// new DragForceThread(this).start();
		hit--;
	}
	
	@Override
	public void afterAttack() {
		PhysicalObjectManager.getInstance().removePhysicalWeapon(physicalWeapon);
//		physicalWeapon = null;
		
	}
	
	@Override
	public String getName() {
		return "SimpleMissile";
	}
	
	@Override
	public boolean finishHit() {
		return hit == 0;
	}
	
	@Override
	public double getX() {
		return Utils.xFromJbox2dToJavaFx(physicalWeapon.getX());
	}
	
	@Override
	public double getY() {
		return Utils.yFromJbox2dToJavaFx(physicalWeapon.getY());
	}
	
	@Override
	public double getHeight() {
		return Utils.heightFromJbox2dToJavaFx(physicalWeapon.getHeight());
	}
	
	@Override
	public double getWidth() {
		return Utils.heightFromJbox2dToJavaFx(physicalWeapon.getWidth());
	}
	
	public boolean isExplosed() {
		return ((ExplosiveObject) physicalWeapon).isExplosed();
	}
	
	public double getAngle() {
		return physicalWeapon.getBody().getAngle();
	}
	
	@Override
	public void update() {
		physicalWeapon.update();
	}
	
	@Override
	public double getDistanceFromLauncher() {
		return 2.5;
	}
}

