package element.weaponsManager.weapons;

import element.weaponsManager.AbstractWeapon;
import element.weaponsManager.ExplosiveObject;
import game.MatchTimer;
import physic.PhysicalObjectManager;
import physic.RemovablePhysicalObject;
import physic.weapon.PhysicalBomb;
import utils.ObjectWithTimer;
import utils.Utils;
import utils.Vector;


public class SimpleBomb extends AbstractWeapon implements ObjectWithTimer {

	private static final float RADIUS = 0.7f;
	public static final int NUMBER_OF_AMMUNITION = 10;
	private static final int NUMBER_OF_HIT = 1;
	private static final long MILLIS_TO_EXPLODE = 3000;

	private static final float BLAST_POWER = 10000f;
	
	private static final float BLAST_RADIUS = 10f;
	
	private static final float MAX_DAMAGE = 60f;

	public SimpleBomb() {
		hit = NUMBER_OF_HIT;
		attacked = false;
	}

	@Override
	public synchronized void attack(Vector position, Vector speed, float angle) {
	    	createPhysicWeapon();
//	    	hit--;
	    	exploded=false;
		physicalWeapon.addToPhisicalWorld();
		physicalWeapon.setActive(true);
		physicalWeapon.setAngularVelocity(0f);
		physicalWeapon.setTransform(position.toVec2(), angle);
		physicalWeapon.setLinearVelocity(speed.toVec2());
//		attacked = true;
		MatchTimer.startTimer(this);

	}

	@Override
	public String getName() {
		return "SimpleBomb";
	}

	
	@Override
	public float getMaxDamage() {
		return MAX_DAMAGE;
	}

	
	@Override
	public double getBlastRadius() {
		return Utils.widthFromJbox2dToJavaFx(BLAST_RADIUS);
	}

	@Override
	public long getSecondToStopTimer() {
		return MILLIS_TO_EXPLODE;
	}

	@Override
	public void afterAttack() {
		if (physicalWeapon != null)
			PhysicalObjectManager.getInstance().removePhysicalWeapon(
					(RemovablePhysicalObject) physicalWeapon);
	}

	@Override
	public double getHeight() {
		return Utils.heightFromJbox2dToJavaFx(RADIUS);
	}

	@Override
	public double getWidth() {
		return Utils.heightFromJbox2dToJavaFx(RADIUS);
	}

	@Override
	public synchronized void reset() {
		attacked = false;
		exploded = false;
		hit = NUMBER_OF_HIT;
		if (physicalWeapon != null)
			PhysicalObjectManager.getInstance().buildPhysicWeapon(
					physicalWeapon);

	}

	@Override
	public  synchronized  void update() {
		if (physicalWeapon != null) {
			physicalWeapon.update();

			x=Utils.xFromJbox2dToJavaFx(physicalWeapon.getX());
			y=Utils.yFromJbox2dToJavaFx(physicalWeapon.getY());
			angle=physicalWeapon.getBody().getAngle();
			
			if (MatchTimer.endObjectTimerIn() <= 0 && !exploded) {
				exploded = true;
				((ExplosiveObject) physicalWeapon).explode();
			}
		}
	}

	@Override
	public double getDistanceFromLauncher() {
		return 1.0;
	}

	@Override
	public void createPhysicWeapon() {
	    if(physicalWeapon==null){
		physicalWeapon = new PhysicalBomb(RADIUS, BLAST_RADIUS, MAX_DAMAGE);
		PhysicalObjectManager.getInstance().buildPhysicWeapon(physicalWeapon);
	    }

	}

}
