package gui.drawer;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import character.Character;
import element.weaponsManager.Weapon;
import gui.animation.CharacterAnimation;
import gui.hud.InventoryItem;
import gui.weapon.WeaponGui;
import javafx.scene.Node;

public class WeaponDrawer {

	private final static String packageP = "gui.weapon.";

	private Character currentCharacter;
	private Weapon currentWeapon;

	private WeaponGui currentWeaponGui;

	private HashMap<String, WeaponGui> inventoryItemsGui;

	public WeaponDrawer() {
		this.inventoryItemsGui = new HashMap<>();
	}
	
	public Node getLauncherWeapon(Weapon weapon,Character character,CharacterAnimation characterAnimation) {
		
		if (inventoryItemsGui.containsKey(weapon.getName())) {
			System.out.println("DENTRO PRIMO IF");
			currentWeaponGui =inventoryItemsGui.get(weapon.getName());
			currentWeapon = weapon;
			currentCharacter = character;
			currentWeaponGui.setCurrentCharacter(currentCharacter,characterAnimation);
			currentWeaponGui.resetItem();
			return currentWeaponGui.getWeaponLauncher();
		} else {

			try {
				System.out.println("DENTRO ELSE ");
				currentWeapon = weapon;
				String weaponName = weapon.getName();
				String guiWeaponName = "Gui" + weaponName;
				currentCharacter=character;
				Class<?> classDefinition = Class.forName(packageP + guiWeaponName);
				currentWeaponGui = (WeaponGui) classDefinition.getConstructor(Weapon.class)
						.newInstance(weapon);
				inventoryItemsGui.put(weaponName, currentWeaponGui);
				currentWeaponGui.setCurrentCharacter(currentCharacter,characterAnimation);
				return currentWeaponGui.getWeaponLauncher();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void updateLauncherAim() {
		if (currentWeaponGui == null)
			return;

		currentWeaponGui.updateLauncher();
	}

	public Node drawAttack() {
		if (currentWeaponGui == null)
			return null;
		return currentWeaponGui.getWeaponBullet();
	}

	public void updateBullet() {
		System.out.println("WWWWWWWWWWWWWWWWWWWW "+currentWeaponGui);
		if (currentWeaponGui == null)
			return;
		currentWeaponGui.updateBullet();
	}

	public boolean bulletLaunched() {
		System.out.println("CCCC "+currentWeapon);
		if (currentWeapon == null)
			return false;
		return currentWeapon.attacked();
	}

	public boolean attackEnded() {
		if (currentWeaponGui == null)
			return false;
		return currentWeaponGui.finishAnimation();
	}

	public void resetDrawer() {
		currentWeapon = null;
		currentWeaponGui = null;
	}

}
