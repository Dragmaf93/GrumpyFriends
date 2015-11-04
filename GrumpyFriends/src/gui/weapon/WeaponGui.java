package gui.weapon;

import element.weaponsManager.Weapon;
import javafx.scene.Node;

public interface WeaponGui {
	
	abstract public Node getWeaponLauncher();
	abstract public Node getWeaponBullet();
	
	abstract public void updateLauncher();
	abstract public void updateBullet();
	
	abstract public void removeBullet();
	
	abstract public void afterAttack();
	abstract public boolean finishAnimation();
	abstract public Weapon getWeapon();
}
