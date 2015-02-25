package object.character;

import java.util.ArrayList;

import object.Position;
import object.Weapon;
import world.World;

public abstract class AbstractCharacter implements Character
{
	protected Position currentPosition;
	protected int height;
	protected int width;
	protected int lifePoints;
	protected World world;
	protected Team team;
	protected ArrayList<Weapon> weaponList;
	protected Weapon equippedWeapon;
	
	

	public AbstractCharacter(int x, int y, int height, World world,
			Team team, ArrayList<Weapon> weaponList) {
		super();
		this.currentPosition= new Position(x,y);
		this.height = height;
		this.width = 1;
		this.world = world;
		this.team = team;
		this.weaponList = weaponList;
		equippedWeapon = null;
		lifePoints = 100;
	}
	
	
	public AbstractCharacter(int x, int y, int height,
			int lifePoints, World world, Team team, ArrayList<Weapon> weaponList) {
		super();
		this.currentPosition= new Position(x,y);
		this.height = height;
		this.width = 1;
		this.lifePoints = lifePoints;
		this.world = world;
		this.team = team;
		this.weaponList = weaponList;
		equippedWeapon = null;
	}

	public int isAshore() 
	{
		//TODO 
		return-1;
	}

	@Override
	public int getX() {
	
		return currentPosition.getX();
	}

	@Override
	public int getHeight() {
		
		return height;
	}

	@Override
	public int getWidth() {
		 
		return width;
	}

	@Override
	public boolean isDead() {
 
		return lifePoints == 0;
	}
	
	@Override
	public boolean equipWeapon(Weapon weapon) 
	{
		for(Weapon wea: weaponList)
			if(wea.equals(weapon)) 
			{
				equippedWeapon = weapon;
				return true;
			}
		return false;
	}

	@Override
	public void moveLeft()
	{
	
	}

	@Override
	public void moveRight()
	{
		
	}

	@Override
	public void highJump(boolean right, boolean left)
	{
		
	}
	@Override
	public void longJump(boolean along)
	{
		
	}
	@Override
	public int getLifePoints() 
	{
		return lifePoints;
	}
	
	public World getWorld() {
		
		return world;
	}

	public void reset(){
		//TODO riguardare 
		lifePoints=100;
	}


	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
	public int getY() {
		return currentPosition.getY();
	}
	public void setX(int x){
		this.currentPosition.setX(x);
	}
	public void setY(int y) {
		this.currentPosition.setY(y);
	}

	public ArrayList<Weapon> getWeaponList() {

		return weaponList;
	}

	public void setWeaponList(ArrayList<Weapon> weaponList) {
		this.weaponList = weaponList;
	}

	
}



//TODO fare o non fare update???? 0.o

