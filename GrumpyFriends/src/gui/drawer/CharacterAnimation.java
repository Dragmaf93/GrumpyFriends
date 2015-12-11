package gui.drawer;

import element.weaponsManager.Weapon;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public interface CharacterAnimation {

	public Image getCharacterMoveAnimation();
	public Image getCharacterIdleAnimation();
	public Image getCharacterJumpAnimation();
	public Image getCharacterFallAnimation();
	
	
	public Image getCharacterWeaponLauncher(Weapon weapon);
	public Image getCharacterBodyWithWeapon(Weapon weapon);
		
	
	public double getValueX();
	public double getValueY();
	public double getWidth();
	public double getHeight();
}
