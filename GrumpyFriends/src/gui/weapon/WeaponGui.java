package gui.weapon;

import character.Character;
import element.weaponsManager.Weapon;
import gui.drawer.CharacterAnimation;
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
	public abstract void setCurrentCharacter(Character currentCharacter,CharacterAnimation characterAnimation);
	
	abstract public void resetItem();
}
