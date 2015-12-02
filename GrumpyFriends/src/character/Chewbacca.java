package character;

import world.World;

public class Chewbacca extends AbstractCharacter{
	
	private final static float HEIGHT = 5f;
	private final static float WIDTH = 2f;

	public Chewbacca(String name,float x, float y, Team team,World world) {
		super(name,x, y, HEIGHT, WIDTH, team,world);

	}


}
