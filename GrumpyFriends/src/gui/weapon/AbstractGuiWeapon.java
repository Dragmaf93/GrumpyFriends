package gui.weapon;

import character.Character;
import element.weaponsManager.Weapon;
import gui.drawer.GuiViewfinder;
import javafx.scene.Group;

public abstract class AbstractGuiWeapon implements WeaponGui {

	protected Group launcherRoot;
	protected Group bulletRoot;
	
	protected Character character;
	protected Weapon weapon;

	protected GuiViewfinder viewfinder;
	
	public AbstractGuiWeapon(Weapon weapon, Character character) {
		this.weapon = weapon;
		this.character = character;
		launcherRoot = new Group();
		bulletRoot = new Group();
		viewfinder = new GuiViewfinder(launcherRoot);
	}
	
	@Override
	public void updateLauncher() {
		viewfinder.update(character, weapon);
	}

}
