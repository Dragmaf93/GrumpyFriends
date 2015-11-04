package element.weaponsManager;

import character.Character;
import physic.PhysicalCharacter;
import physic.PhysicalLauncher;
import physic.PhysicalObjectManager;
import utils.Utils;
import utils.Vector;

public class Launcher {

	public final static float MAX_LAUNCH_POWER=50f;
	
	private PhysicalLauncher physicalLauncher;

	private boolean activated;
	private Weapon loadedWeapon;

	private boolean attacked;
	
	public Launcher(Character character) {
		// this.character = character;

		physicalLauncher = new PhysicalLauncher((PhysicalCharacter) character.getPhysicalObject());
		PhysicalObjectManager.getInstance().buildPhysicObject(physicalLauncher);
		activated = false;
		attacked=false;
		loadedWeapon = null;
	}

	public void startWeaponAttack(float speed) {
		if (loadedWeapon == null || !isActivated())
			return;

		Vector position = physicalLauncher.getPositionAim();
		Vector speedVector = physicalLauncher.getVectorSpeed(speed);
		float angle = physicalLauncher.getAngle();
		loadedWeapon.attack(position, speedVector, angle);
		attacked=true;
//		 if(loadedWeapon.finishHit()){ 
//			 loadedWeapon = null;
//			 disable();
//		 }
	}

	public boolean isActivated() {
		return activated;
	}
	public double getAngle(){
		return (double) physicalLauncher.getAngle();
	}
	public Vector getPositionAim(){
		Vector jboxPosition = physicalLauncher.getPositionAim();
		
		return Utils.vectorToFx(jboxPosition);
	}
	
	public void activate() {
		activated = true;
		attacked=false;
		
		physicalLauncher.expose();
	}
	public void setDirection(int direction){
		physicalLauncher.setLimitAngle(direction);
	}
	public void changeAngle(float val) {
		physicalLauncher.rotate(val);
	}
	public double getX(){
		return physicalLauncher.getX();
	}
	public double getY(){
		return physicalLauncher.getY();
		
	}
	
	public void disable() {
		activated = false;
		physicalLauncher.hide();
	}

	public void loadWeapon(Weapon weapon) {
		if (!isActivated())
			activate();
		loadedWeapon = weapon;
	}
	
	public boolean attacked(){
		return attacked;
	}
}
