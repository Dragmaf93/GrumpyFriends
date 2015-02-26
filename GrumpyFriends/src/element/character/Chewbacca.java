package element.character;

import java.util.ArrayList;

import world.AbstractWorld;
import world.World;
import element.Position;
import element.Weapon;

public class Chewbacca extends AbstractCharacter {
	
	private static final int HEIGHT= 5;
	private static final int WIDTH = 2;
	private static final int POWER_JUMP = 2;
	
	public Chewbacca(int x, int y, int lifePoints, Team team,
			ArrayList<Weapon> weaponList) {
		super(x, y, lifePoints, team, weaponList);
		height=HEIGHT;
		width= WIDTH;
		powerJump = POWER_JUMP;
	}

	@Override
	public Position getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() 
	{
		return "CHEW";
	}

	public void setWorld() 
	{
		world = AbstractWorld.getInstance();
	}
	
}
