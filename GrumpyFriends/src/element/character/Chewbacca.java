package element.character;

import java.util.ArrayList;

import physicEngine.Time;

import com.sun.javafx.scene.traversal.WeightedClosestCorner;

import world.AbstractWorld;
import world.Vector;
import world.World;
import element.Weapon;

public class Chewbacca extends AbstractCharacter {
	
	private static final int HEIGHT= 100;
	private static final int WIDTH = 20;
	private static final int POWER_JUMP = 40;
	
	public Chewbacca(int x, int y, int lifePoints, Team team,
			ArrayList<Weapon> weaponList) {
		super(x, y, lifePoints, team, weaponList);
		height=HEIGHT;
		width= WIDTH;
		powerJump = POWER_JUMP;
		vectorJump = new Vector(0, -POWER_JUMP);
		step = width;
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
