package gui.weapon;

import javafx.scene.Node;

public interface WeaponGui {
	
	abstract public Node getWeaponLauncher();
	abstract public Node getWeaponBullet();
	
	abstract public void updateLauncher();
	abstract public void updateBullet();
	
	abstract public void afterAttack();
}
