package element.weaponsManager.weapons;

import element.weaponsManager.AbstractWeapon;
import element.weaponsManager.ExplosiveObject;
import physic.PhysicalObjectManager;
import physic.weapon.PhysicalBomb;
import utils.ObjectWithTimer;
import utils.Timer;
import utils.Utils;
import utils.Vector;

public class SimpleBomb extends AbstractWeapon implements ObjectWithTimer{

	private static final float RADIUS = 0.7f;
	public static final int NUMBER_OF_AMMUNITION = 10;
	private static final int NUMBER_OF_HIT = 1;
	private static final long SEC_TO_EXPLODE=4;
	
	private static final float BLAST_POWER = 10000f;
	private static final float BLAST_RADIUS = 10f;
	
	private Timer timer;
	public SimpleBomb() {
		physicalWeapon = new PhysicalBomb(RADIUS,BLAST_RADIUS);
		PhysicalObjectManager.getInstance().buildPhysicWeapon(physicalWeapon);
		hit = NUMBER_OF_HIT;
		attacked=false;
	}

	@Override
	public void attack(Vector position, Vector speed, float angle) {
		
		physicalWeapon.addToPhisicalWorld();
		physicalWeapon.setActive(true);
		physicalWeapon.setAngularVelocity(0f);
		physicalWeapon.setTransform(position.toVec2(), angle);
		physicalWeapon.setLinearVelocity(speed.toVec2());
		attacked=true;
		timer = new Timer(this);

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
		hit--;		
	}
	
	@Override
	public void afterCountDown() {
		((ExplosiveObject) physicalWeapon).explode();
		afterAttack();
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

	@Override
	public Timer getTimer() {
		return timer;
	}
	
	
}
