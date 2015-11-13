package gui.drawer;

import java.lang.reflect.InvocationTargetException;

import character.Character;
import element.weaponsManager.Weapon;
import gui.weapon.WeaponGui;
import javafx.scene.Node;

public class WeaponDrawer {
	
	private final static String packageP = "gui.weapon.";
	
	private Character character;
	private WeaponGui currentWeaponGui;
	private Weapon currentWeapon;
	
	public WeaponDrawer(Character character) {
		this.character = character;
	}
	
	public Node getLauncherWeapon(Weapon weapon) {
		try {
			currentWeapon=weapon;
			String weaponName = weapon.getName();
			String guiWeaponName = "Gui"+weaponName;
			Class<?> classDefinition = Class.forName(packageP+guiWeaponName);
			currentWeaponGui=(WeaponGui) classDefinition.getConstructor(Weapon.class,Character.class).newInstance(weapon,character);
//			currentWeaponGui = (WeaponGui) classDefinition.newInstance();
			return currentWeaponGui.getWeaponLauncher();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void updateLauncherAim() {
		if(currentWeaponGui==null) return;
		
		currentWeaponGui.updateLauncher();
	}
	
	public Node drawAttack(){
		if(currentWeaponGui==null) return null;
		return currentWeaponGui.getWeaponBullet();
	}
	
	public void updateBullet(){
		if(currentWeaponGui==null) return;
		currentWeaponGui.updateBullet();
	}

	public boolean bulletLaunched() {
		if(currentWeapon==null) return false;
		return currentWeapon.attacked();
	}
	
	public boolean attackEnded(){
		if(currentWeaponGui==null) return false;
		return currentWeaponGui.finishAnimation();
	}
	
	public void resetDrawer(){
		currentWeapon=null;
		currentWeaponGui=null;
	}
	

}
