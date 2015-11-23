package element.weaponsManager.weapons;

import element.weaponsManager.AbstractWeapon;
import element.weaponsManager.ExplosiveObject;
import game.MatchTimer;
import physic.PhysicalObjectManager;
import physic.weapon.PhysicalBomb;
import utils.ObjectWithTimer;
import utils.Utils;
import utils.Vector;

public class SimpleBomb extends AbstractWeapon implements ObjectWithTimer{

	private static final float RADIUS = 0.7f;
	public static final int NUMBER_OF_AMMUNITION = 10;
	private static final int NUMBER_OF_HIT = 1;
	private static final long MILLIS_TO_EXPLODE=8000;
	
	private static final float BLAST_POWER = 10000f;
	private static final float BLAST_RADIUS = 10f;
	private static final float MAX_DAMAGE = 60f;

	
	
	private boolean exploded;
	
	public SimpleBomb() {
		physicalWeapon = new PhysicalBomb(RADIUS,BLAST_RADIUS,MAX_DAMAGE);
		PhysicalObjectManager.getInstance().buildPhysicWeapon(physicalWeapon);
		hit = NUMBER_OF_HIT;
		attacked=false;
	}

	@Override
	public void attack(Vector position, Vector speed, float angle) {
		
		hit--;		
		physicalWeapon.addToPhisicalWorld();
		physicalWeapon.setActive(true);
		physicalWeapon.setAngularVelocity(0f);
		physicalWeapon.setTransform(position.toVec2(), angle);
		physicalWeapon.setLinearVelocity(speed.toVec2());
		attacked=true;
		MatchTimer.startTimer(this);

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
	public double getBlastRadius() {
		return Utils.widthFromJbox2dToJavaFx(BLAST_RADIUS);
	}

	@Override
	public long getSecondToStopTimer() {
		return MILLIS_TO_EXPLODE;
	}
	@Override
	public void afterAttack() {
		PhysicalObjectManager.getInstance().removePhysicalWeapon(physicalWeapon);
		physicalWeapon=null;
	}
	public boolean isExploded(){
		return exploded;
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
	public void update() {
		physicalWeapon.update();
		
		if(MatchTimer.endObjectTimerIn()<=0 && !exploded){
			exploded=true;
			((ExplosiveObject) physicalWeapon).explode();
		}
	}
	
	@Override
	public double getDistanceFromLauncher() {
		return 1.0;
	}
	
	
}
