package element.character;

import java.util.ArrayList;

import element.weaponsManager.Weapon;
import physic.PhysicalObject;

public class Chewbacca extends AbstractCharacter{
	
	private final static float HEIGHT = 3f;
	private final static float WIDTH = 1f;

	public Chewbacca(String name,float x, float y, Team team) {
		super(name,x, y, HEIGHT, WIDTH, team);

	}


}
