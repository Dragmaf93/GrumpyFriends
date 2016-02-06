package character;

import world.World;

public class WhiteStormtrooper extends AbstractCharacter{
	
	private final static float HEIGHT = 5f;
	private final static float WIDTH = 2f;

	public WhiteStormtrooper(String name,float x, float y, Team team,World world) {
		super(name,x, y, HEIGHT, WIDTH, team,world);

	}
	
	public WhiteStormtrooper(String name,Team team) {
		super(name ,HEIGHT, WIDTH, team);
		
	}


}
