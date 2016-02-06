package element.weaponsManager;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;

public class WeaponsManager {

	private final static String rootPath = "./src/";
	private final static String packagePath = "element/weaponsManager/weapons";
	private final static String packageP = "element.weaponsManager.weapons.";
	private final static String staticField = "NUMBER_OF_AMMUNITION";

	private HashMap<String, Integer> inventary;
	private static String[] classList;

	private static HashMap<String, Weapon> instantiatedWeapons;

	public WeaponsManager() {

		File directory = new File(WeaponsManager.rootPath + WeaponsManager.packagePath);
		classList = directory.list();

		inventary = new HashMap<>();

		if (instantiatedWeapons == null)
			instantiatedWeapons = new HashMap<String, Weapon>();

		for (String classWeapon : classList) {
			try {
				String className = classWeapon.substring(0, classWeapon.length() - 5);
				Class<?> factoryClass = Class.forName(packageP + className);

				Field field = factoryClass.getField(staticField);
//				System.out.println(field.getInt(null));
				inventary.put(className, field.getInt(null));

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public Weapon getWeapon(String weaponName) {

		if (instantiatedWeapons.containsKey(weaponName)) {
			Weapon weapon = instantiatedWeapons.get(weaponName);
			weapon.reset();
			return weapon;
		} else {
			try {
				Class<?> classDefinition = Class.forName(packageP + weaponName);
				Weapon weapon = (Weapon) classDefinition.newInstance();
				instantiatedWeapons.put(weaponName, weapon);
				return weapon;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void addAmmunition(String weaponName, int number) {
		int n = inventary.get(weaponName);
		inventary.put(weaponName, new Integer(n + number));
	}

	public void removeOneAmmunition(String weaponName) {
		int n = inventary.get(weaponName);
		inventary.put(weaponName, new Integer(n - 1));
	}

	public int getNumberOfAmmunition(String weapon) {
		return inventary.get(weapon);
	}

	public boolean isAvailable(String weaponName) {
		if (inventary.get(weaponName) == 0)
			return false;
		return true;
	}

	public static String[] getWeaponList() {
		return classList;
	}

	// public static void main(String[] args) {
	// WeaponsManager w = new WeaponsManager();
	// System.out.println(w.getWeapon("SimpleBomb"));
	// }

}
