//package element.weaponsManager;
//
//import character.Character;
//import physic.PhysicalCharacter;
//import physic.PhysicalLauncher;
//import physic.PhysicalObjectManager;
//import utils.Utils;
//import utils.Vector;
//
//public class Launcher {
//
//	public final static float MAX_LAUNCH_POWER=50f;
//	
//	private PhysicalLauncher physicalLauncher;
//
//	private boolean activated;
//	private Weapon loadedWeapon;
//
//	private boolean attacked;
//	
////	private float currentAim;
////	private Vector position;
////	private Vector character;
//	
//	
//	public Launcher(Character character) {
//		// this.character = character;
//
//		physicalLauncher = new PhysicalLauncher((PhysicalCharacter) character.getPhysicalObject());
//		PhysicalObjectManager.getInstance().buildPhysicObject(physicalLauncher);
//		activated = false;
//		attacked=false;
//		loadedWeapon = null;
////		currentAim = 0;
////		position = new pos
//	}
//
//	public void startWeaponAttack(float speed) {
//		if (loadedWeapon == null || !isActivated())
//			return;
//
//		Vector position = physicalLauncher.getPositionAim();
//		Vector speedVector = physicalLauncher.getVectorSpeed(speed);
//		float angle = physicalLauncher.getAngle();
//		loadedWeapon.attack(position, speedVector, angle);
//		attacked=true;
////		 if(loadedWeapon.finishHit()){ 
////			 loadedWeapon = null;
////			 disable();
////		 }
//	}
//
//	public boolean isActivated() {
//		return activated;
//	}
//	public double getAngle(){
//		return (double) physicalLauncher.getAngle();
//	}
//	public Vector getPositionAim(){
//		Vector jboxPosition = physicalLauncher.getPositionAim();
//		
//		return Utils.vectorToFx(jboxPosition);
//	}
//	
//	public void activate() {
//		activated = true;
//		attacked=false;
//		
//		physicalLauncher.expose();
//	}
//	public void setDirection(int direction){
//		physicalLauncher.setLimitAngle(direction);
//	}
//	public void changeAngle(float val) {
//		physicalLauncher.rotate(val);
//	}
//	public double getX(){
//		return physicalLauncher.getX();
//	}
//	public double getY(){
//		return physicalLauncher.getY();
//		
//	}
//	
//	public void disable() {
//		physicalLauncher.rotate(0);
//		activated = false;
//		physicalLauncher.hide();
//	}
//
//	public void loadWeapon(Weapon weapon) {
//		if (!isActivated())
//			activate();
//		else{
//			disable();
//			activate();
//		}
//			loadedWeapon = weapon;
//	}
//	
//	public boolean attacked(){
//		return attacked;
//	}
//}

package element.weaponsManager;

import character.Character;
import physic.PhysicalCharacter;
import physic.PhysicalLauncher;
import physic.PhysicalObjectManager;
import sun.rmi.transport.proxy.CGIHandler;
import utils.Utils;
import utils.Vector;


public class Launcher {

	public final static float MAX_LAUNCH_POWER = 80f;
	
	private Character character;
	// private PhysicalLauncher physicalLauncher;

	
	private boolean activated;
	
	private Weapon loadedWeapon;

	private boolean attacked;

	private int currentDirection;

	
	private float angle;
	
	private Vector position;
	
	private Vector speedVector;
	
	private double y;
	
	private double x;

	public Launcher(Character character) {
		// this.character = character;
		//
		// physicalLauncher = new PhysicalLauncher((PhysicalCharacter)
		// character.getPhysicalObject());
		// PhysicalObjectManager.getInstance().buildPhysicObject(physicalLauncher);
		activated = false;
		attacked = false;
		loadedWeapon = null;
		angle = 0;
		position = new Vector();
		speedVector = new Vector();
		this.character = character;
		currentDirection = Character.RIGHT;
	}

	public void startWeaponAttack(float speed) {
		if (loadedWeapon == null || !isActivated())
			return;

		// Vector position = physicalLauncher.getPositionAim();
		// Vector speedVector = physicalLauncher.getVectorSpeed(speed);
		// float angle = physicalLauncher.getAngle();
		position.set(
				(float) (character.getPhysicalObject().getBody().getPosition().x + currentDirection
						* loadedWeapon.getDistanceFromLauncher()), character
						.getPhysicalObject().getBody().getPosition().y
						+ character.getPhysicalObject().getHeight() / 2);
		// speedVector.set((float)(position.x +Math.cos(angle)*speed) ,(float)
		// (position.y+Math.sin(angle)));
		float y = (float) (Math.sin(angle) * speed);
		float x = (float) (Math.cos(angle) * speed);
		speedVector.set(x, y);
		loadedWeapon.attack(position, speedVector, angle);
		// attacked = true;
		// loadedWeapon = null;
		// if(loadedWeapon.finishHit()){
		// loadedWeapon = null;
		// disable();
		// }
	}

	
	public boolean isActivated() {
		if (loadedWeapon == null)
			return false;
		return activated;
	}

	
	public double getAngle() {
		// return (double) physicalLauncher.getAngle();
		return angle;
	}

	public Vector getPositionAim() {
		// Vector jboxPosition = physicalLauncher.getPositionAim();

		return Utils.vectorToFx(position);
	}

	public void restartLauncher() {
		attacked = false;
	}

	public void activate() {
		if (loadedWeapon == null)
			return;
		activated = true;
		// attacked = false;

		// physicalLauncher.expose();
	}

	public void setDirection(int direction) {
		if (currentDirection != direction && direction == Character.LEFT) {
			angle = (float) Math.toRadians(180);
			currentDirection = direction;
		} else if (currentDirection != direction
				&& direction == Character.RIGHT) {
			angle = 0;
			currentDirection = direction;
		}
		// physicalLauncher.setLimitAngle(direction);
	}

	public void changeAngle(float val) {

		// physicalLauncher.rotate(val);
		if (currentDirection == Character.RIGHT && val > 0
				&& Math.toDegrees(angle + val) < 90)
			angle += val;
		else if (currentDirection == Character.RIGHT && val < 0
				&& Math.toDegrees(angle + val) > -90)
			angle += val;
		else if (currentDirection == Character.LEFT && val > 0
				&& Math.toDegrees(angle - val) > 90)
			angle -= val;

		else if (currentDirection == Character.LEFT && val < 0
				&& Math.toDegrees(angle - val) < 270)
			angle -= val;
	}

	
	public double getX() {
		// return physicalLauncher.getX();
		// return
		// Utils.xFromJbox2dToJavaFx(character.getPhysicalObject().getBody().getPosition().x);
		return character.getX();
	}

	
	public double getY() {
		// return physicalLauncher.getY();
		// return
		// Utils.yFromJbox2dToJavaFx(character.getPhysicalObject().getBody().getPosition().y
		// );
		return character.getY();
	}

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void disable() {
		// physicalLauncher.rotate(0);
		activated = false;
		// physicalLauncher.hide();
	}

	public void loadWeapon(Weapon weapon) {
		if (weapon != null) {
			if (!isActivated()) {
				loadedWeapon = weapon;
				activate();
			}
		} else
			loadedWeapon = null;

	}

	
	public void setAttacked(boolean b) {
		attacked = b;
	}

	public boolean attacked() {
		return attacked;
	}

	
	public Weapon getLoadedWeapon() {
		return loadedWeapon;
	}

	
	public Vector getSpeedVector() {
		return speedVector;
	}

	public void setSpeedvector(float x, float y) {
		speedVector.set(x, y);
	}

}
