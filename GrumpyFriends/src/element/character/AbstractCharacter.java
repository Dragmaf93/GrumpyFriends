package element.character;

import java.util.ArrayList;

import element.Position;
import element.Weapon;
import world.AbstractWorld;
import world.Ground;
import world.World;

public abstract class AbstractCharacter implements Character
{
	public final static int RIGHT = 0;
	public final static int LEFT = 1;

	
	protected Position currentPosition;
	protected int height;
	protected int width;
	protected int powerJump;
	protected int lifePoints;
	protected World world;
	protected Team team;
	protected ArrayList<Weapon> weaponList;
	protected Weapon equippedWeapon;

	
	public AbstractCharacter(int x, int y,
			Team team, ArrayList<Weapon> weaponList) {
		
		this.currentPosition= new Position(x,y);
		this.world = AbstractWorld.getInstance();
		this.team = team;
		this.weaponList = weaponList;
		equippedWeapon = null;
		lifePoints = 100;
	}
	
	
	public AbstractCharacter(int x, int y,
			int lifePoints, Team team, ArrayList<Weapon> weaponList) {
		
		this.currentPosition= new Position(x,y);
		this.world = AbstractWorld.getInstance();
		this.lifePoints = lifePoints;
		this.team = team;
		this.weaponList = weaponList;
		equippedWeapon = null;
	}

	public int fall() 
	{
		int depth = 0;
		int xFirst = currentPosition.getX(), yFirst = currentPosition.getY();
		int heightElement=0;
		
		for (int i=1; (heightElement= isThereSomething())==0; i++){
			currentPosition.setY(currentPosition.getY()+1);
			depth++;
		}
		System.out.println(heightElement);
		if(heightElement ==-1){
			depth+=MAX_HEIGHT-1;
			currentPosition.setY(currentPosition.getY()+MAX_HEIGHT-1);
		}
		world.update(this, xFirst, yFirst);
		return depth;
	}
	
	public int isThereSomething(){
		for(int i= 1; i <= MAX_HEIGHT;i++ ){
			if((world.getElement(currentPosition.getX(), currentPosition.getY()+i) != null &&
					!(world.getElement(currentPosition.getX(), currentPosition.getY()+i) instanceof Ground))||
					(world.getElement(currentPosition.getX()+1, currentPosition.getY()+i) !=null &&
					!(world.getElement(currentPosition.getX()+1, currentPosition.getY()+i) instanceof Ground))){
				return i;
			}
			else if((world.getElement(currentPosition.getX(), currentPosition.getY()+i) instanceof Ground)||
					(world.getElement(currentPosition.getX()+1, currentPosition.getY()+i) instanceof Ground)){
						return i;
					}
		}
		return 0;
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
	public void move(int direction)
	{
		int xFirst = currentPosition.getX(), yFirst = currentPosition.getY();
		
		if (canSimplyMove(direction))
		{
			world.update(this, xFirst, yFirst);
			fall();
			return;
		}
		if (canClimbMove(direction))
		{
			world.update(this, xFirst, yFirst);
			fall();
			return;
		}

	}

	private boolean canSimplyMove(int direction)
	{
		if(direction == RIGHT)
		{
			for (int i = 0; i < height; i++) 
			{
				if (world.getElement(currentPosition.getX()+width, currentPosition.getY()-i) != null)
					return false;
			}
			currentPosition.setX(currentPosition.getX()+1);
			return true;
		}
		for (int i = 0; i < height; i++) 
		{
			if (world.getElement(currentPosition.getX()-1, currentPosition.getY()-i) != null)
				return false;
		}
		currentPosition.setX(currentPosition.getX()-1);
		return true;
	}
	
	private boolean canClimbMove(int direction)
	{
		if(direction == RIGHT)
		{
			for (int i = 1; i <= height; i++) 
			{
				if (world.getElement(currentPosition.getX()+width, currentPosition.getY()) instanceof Ground &&
						world.getElement(currentPosition.getX()+width, currentPosition.getY()-i) != null)
					return false;
			}
			currentPosition.setX(currentPosition.getX()+1);
			currentPosition.setY(currentPosition.getY()-1);
			return true;
		}
		
		for (int i = 1; i <= height; i++) 
		{
			if (world.getElement(currentPosition.getX()-width, currentPosition.getY()) instanceof Ground &&
					world.getElement(currentPosition.getX()-1, currentPosition.getY()-i) != null)
				return false;
		}
		currentPosition.setX(currentPosition.getX()-1);
		currentPosition.setY(currentPosition.getY()-1);
		return true;
	}
	
	@Override
	public void jump()
	{
		int xFirst = currentPosition.getX(), yFirst = currentPosition.getY();
		for (int i = 1; i <= powerJump; i++) 
		{
			for(int j = 1; j < height+i; j++)
			{
				if ((world.getElement(currentPosition.getX(), currentPosition.getY()-j) != null) ||
						(world.getElement(currentPosition.getX()+1, currentPosition.getY()-j) != null))
					return;
			}
			
			currentPosition.setY(currentPosition.getY()-1);
			world.update(this, xFirst, yFirst);
			yFirst = currentPosition.getY();
		}
		fall();
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

