package gui.drawer;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import menu.MenuManager;
import character.Character;
import element.weaponsManager.Weapon;
import gui.Popup;
import gui.animation.CharacterAnimation;
import gui.hud.InventoryItem;
import gui.weapon.WeaponGui;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class WeaponDrawer {

	private final static String packageP = "gui.weapon.";

	private Character currentCharacter;
	private Weapon currentWeapon;

	private WeaponGui currentWeaponGui;

	private Map<String, WeaponGui> inventoryItemsGui;

	private Popup exception;

	public WeaponDrawer() {
		this.inventoryItemsGui = new HashMap<>();
		
		exception = new Popup(400, 180, "Error", null, "Ok");
	}
	
	public Node getLauncherWeapon(Weapon weapon,Character character,CharacterAnimation characterAnimation) {
		
		if (inventoryItemsGui.containsKey(weapon.getName())) {
			currentWeaponGui =inventoryItemsGui.get(weapon.getName());
			currentWeapon = weapon;
			currentCharacter = character;
			currentWeaponGui.setCurrentCharacter(currentCharacter,characterAnimation);
			currentWeaponGui.resetItem();
			return currentWeaponGui.getWeaponLauncher();
		} else {

			try {
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
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException |
					SecurityException | IllegalArgumentException | InvocationTargetException e) {
				
				MenuManager.getInstance().addExceptionPopup(exception);
				exception.getRightButton().setOnMouseReleased(
						new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent event) {
								if (event.getButton() == MouseButton.PRIMARY)
									System.exit(-1);
							}
						});
				
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
		if (currentWeaponGui == null)
			return;
		currentWeaponGui.updateBullet();
	}

	public boolean bulletLaunched() {
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
