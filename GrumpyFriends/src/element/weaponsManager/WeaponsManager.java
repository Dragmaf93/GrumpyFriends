package element.weaponsManager;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;



public class WeaponsManager {

	private final static String rootPath="./src/";
	private final static String packagePath="element/weaponsManager/weapons";
	private final static String packageP="element.weaponsManager.weapons.";
	private final static String staticField ="NUMBER_OF_AMMUNITION";
	
	
	private HashMap<String, Integer> inventary;
	private static String[] classList;
	
	public WeaponsManager() {
		
		File directory = new File(WeaponsManager.rootPath+WeaponsManager.packagePath);
		classList=directory.list();
	
		inventary = new HashMap<>();
		
		for (String classWeapon : classList) {
			try {
				String className = classWeapon.substring(0, classWeapon.length()-5);
				Class<?> factoryClass = Class.forName(packageP+className);
				
				Field field = factoryClass.getField(staticField);
				System.out.println(field.getInt(null));
				inventary.put(className, field.getInt(null));
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Weapon getWeapon(String weaponName){
		try {
			Class<?> classDefinition = Class.forName(packageP+weaponName);
			return (Weapon) classDefinition.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	public void addAmmunition(String weaponName, int number){
		int n= inventary.get(weaponName);
		inventary.put(weaponName,new Integer(n+number));
	}
	
	public void removeOneAmmunition(String weaponName){
		int n= inventary.get(weaponName);
		inventary.put(weaponName,new Integer(n-1));
	}
	public int getNumberOfAmmunition(String weapon){
		return inventary.get(weapon);
	}
	
	public boolean	isAvailable(String weaponName){
		if(inventary.get(weaponName)==0)
			return false;
		return true;
	}
	
	public static void main(String[] args) {
		WeaponsManager w = new WeaponsManager();
		System.out.println(w.getWeapon("SimpleBomb"));
	}
	
}
