package element.character;

import java.util.ArrayList;

import element.Weapon;

public class Chewbacca extends AbstractCharacter{
	
	private final static float HEIGHT = 10f;
	private final static float WIDTH = 5f;

	public Chewbacca(String name,float x, float y, Team team,
			ArrayList<Weapon> weaponList) {
		super(name,x, y, HEIGHT, WIDTH, team, weaponList);

	}
	
	

}
