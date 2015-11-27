package gui.weapon;

import character.Character;
import element.weaponsManager.Weapon;
import gui.drawer.*;
import javafx.scene.Group;

public abstract class AbstractGuiWeapon implements WeaponGui {

	protected Group launcherRoot;
	protected Group bulletRoot;
	
	protected Character character;
	protected Weapon weapon;

	protected GuiViewfinder viewfinder;
	
	public AbstractGuiWeapon(Weapon weapon) {
		this.weapon = weapon;
		this.character = character;
		launcherRoot = new Group();
		bulletRoot = new Group();
		viewfinder = new GuiViewfinder(launcherRoot);
	}
	@Override
	public void setCurrentCharacter(Character currentCharacter) {
		this.character = currentCharacter;
	}
	
	@Override
	public void updateLauncher() {
		viewfinder.update(character, weapon);
	}

	
}
