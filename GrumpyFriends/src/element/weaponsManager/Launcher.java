package element.weaponsManager;

import character.Character;
import physic.PhysicalCharacter;
import physic.PhysicalLauncher;
import physic.PhysicalObjectManager;
import utils.Vector;

public class Launcher {

	private PhysicalLauncher physicalLauncher;

	private boolean activated;
	private Weapon loadedWeapon;

	public Launcher(Character character) {
		// this.character = character;

		physicalLauncher = new PhysicalLauncher((PhysicalCharacter) character.getPhysicalObject());
		PhysicalObjectManager.getInstance().buildPhysicObject(physicalLauncher);
		activated = false;

		loadedWeapon = null;
	}

	public void startWeaponAttack(float speed) {
		if (loadedWeapon == null || !isActivated())
			return;

		Vector position = physicalLauncher.getPositionWeapon();
		Vector speedVector = physicalLauncher.getVectorSpeed(speed);
		float angle = physicalLauncher.getAngle();
		loadedWeapon.attack(position, speedVector, angle);

		// if(loadedWeapon.finishHit()) loadedWeapon = null;
	}

	public boolean isActivated() {
		return activated;
	}

	public void activate() {
		activated = true;
		physicalLauncher.expose();
	}

	public void changeAngle(float val) {
		physicalLauncher.rotate(val);
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
}
