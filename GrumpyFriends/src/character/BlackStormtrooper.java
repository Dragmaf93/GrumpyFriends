package character;

import world.World;

public class BlackStormtrooper extends AbstractCharacter{
	private final static float HEIGHT = 5f;
	private final static float WIDTH = 2f;

	public BlackStormtrooper(String name,float x, float y, Team team,World world) {
		super(name,x, y, HEIGHT, WIDTH, team,world);

	}
	
	public BlackStormtrooper(String name,Team team) {
		super(name ,HEIGHT, WIDTH, team);
		
	}

}
