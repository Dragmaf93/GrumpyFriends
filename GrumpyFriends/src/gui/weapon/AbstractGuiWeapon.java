package gui.weapon;

import character.Character;
import element.weaponsManager.Weapon;
import gui.animation.CharacterAnimation;
import gui.drawer.*;
import javafx.scene.Group;

public abstract class AbstractGuiWeapon implements WeaponGui {

	protected Group launcherRoot;
	protected Group bulletRoot;
	
	protected Character currentCharacter;
	protected CharacterAnimation characterAnimation;
	protected CharacterAnimation characterAnimationBlack;
	
	protected Weapon weapon;

	protected GuiViewfinder viewfinder;
	
	public AbstractGuiWeapon(Weapon weapon) {
		this.weapon = weapon;
		launcherRoot = new Group();
		bulletRoot = new Group();
		viewfinder = new GuiViewfinder(launcherRoot);
	}
	
	@Override
	public void setCurrentCharacter(Character currentCharacter, CharacterAnimation animation) {
		this.currentCharacter = currentCharacter;
		this.characterAnimation = animation;
	}
	
	@Override
	public void updateLauncher() {
		viewfinder.update(currentCharacter, weapon);
	}

	
}
