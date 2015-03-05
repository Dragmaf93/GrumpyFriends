package element.character;

import java.util.ArrayList;

import element.Element;
import element.Position;
import element.Weapon;
import world.AbstractWorld;
import world.Ground;
import world.Vector;
import world.World;

public abstract class AbstractCharacter implements Character
{
	public final static int RIGHT = 0;
	public final static int LEFT = 1;
	
	protected Vector currentPosition;
	
	protected int height;
	protected int width;
	protected int powerJump;
	protected int lifePoints;
	protected World world;
	protected Team team;
	protected ArrayList<Weapon> weaponList;
	protected Weapon equippedWeapon;
	protected Vector vectorJump;
	protected Vector currentSpeed;
	
	protected boolean inFall;
	protected boolean inMovement;
	protected boolean inJump;
	protected int step;
	
	public AbstractCharacter(int x, int y,
			Team team, ArrayList<Weapon> weaponList) {
		System.out.println("X : "+x+"Y : "+y);
		this.currentPosition= new Vector(x,y);
		this.world = AbstractWorld.getInstance();
		this.team = team;
		this.weaponList = weaponList;
		equippedWeapon = null;
		lifePoints = 100;
		inFall = false;
		inMovement = false;
		inJump = false;
		fall();
	}
	
	public AbstractCharacter(int x, int y,
			int lifePoints, Team team, ArrayList<Weapon> weaponList) {
		System.out.println("X : "+x+" Y : "+y);

		this.currentPosition= new Vector(x,y);
		this.currentSpeed = new Vector(0, 0);
		this.world = AbstractWorld.getInstance();
		this.lifePoints = lifePoints;
		this.team = team;
		this.weaponList = weaponList;
		equippedWeapon = null;
		inFall = false;
		inMovement = false;
		inJump = false;
		fall();
	}

	public int fall() 
	{
		int depth = 0;
		Vector gravity = world.getGravity();
		int xFirst = currentPosition.getX(), yFirst = currentPosition.getY();
		Vector speedFirst = currentSpeed.clone();
		for (int time = 1; isFall(xFirst, yFirst, time, gravity, speedFirst); time++) 
		{
			currentPosition.setX(xFirst + (speedFirst.getX() * time));
			currentPosition.setY((int) (yFirst + (speedFirst.getY() * time) + (0.5 * gravity.getY() * time)));
			currentSpeed.setY(gravity.getY() * time);
		}
		System.out.println("SETTA CADUTA: "+currentPosition.getX()+" "+currentPosition.getY());
		world.update(this, xFirst, yFirst);
		currentSpeed.setX(0);
		currentSpeed.setY(0);
		inJump = false;
		return depth;
	}
	
	public boolean isFall(int x, int y, int time, Vector gravity, Vector speedFirst)
	{
		int coordinateX = x + (speedFirst.getX() * time);
		int coordinateY = (int) (y + (speedFirst.getX() * time) + (0.5 * gravity.getY() * time));
		System.out.println(coordinateX + " "+ coordinateY);
//		if (!inJump)
			if (world.getElement(world.pointToCellX(coordinateX), world.pointToCellY(coordinateY)) != null ||
				world.getElement(world.pointToCellX(coordinateX+width), world.pointToCellY(coordinateY)) != null)
				return false;
//		if(inJump)
//			if (world.getElement(world.pointToCellX(coordinateX), world.pointToCellY(coordinateY)) != null)
//				return false;
		return true;
	}
	
	public Element isThereSomething(){
		for(int i= 1; i <= MAX_HEIGHT ;i++ ){
			if(world.getElement(currentPosition.getX(), currentPosition.getY()+i) != null ){
				return world.getElement(currentPosition.getX(), currentPosition.getY()+i);
			}
			if(world.getElement(currentPosition.getX()+1, currentPosition.getY()+i) !=null){
				return world.getElement(currentPosition.getX()+1, currentPosition.getY()+i);
			}
			if(world.getElement(currentPosition.getX()-1, currentPosition.getY()+i) instanceof AbstractCharacter)
				return world.getElement(currentPosition.getX()-1, currentPosition.getY()+i);
		}
		return null;
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

		if (!inFall || !inJump)
		{
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
	}

	private boolean canSimplyMove(int direction)
	{
		if(direction == RIGHT)
		{
			currentSpeed.setX(step);
			for (int i = 0; i < height; i+=world.SIZE_CELL) 
			{
				if (world.getElement(world.pointToCellX(currentPosition.getX()+width+step), world.pointToCellY(currentPosition.getY()-i)) != null)
					return false;
			}
		}
		else
		{
			currentSpeed.setX(-step);
			for (int i = 0; i < height; i+=world.SIZE_CELL) 
			{
				if (world.getElement(world.pointToCellX(currentPosition.getX())-1, world.pointToCellY(currentPosition.getY()-i)) != null)
					return false;
			}
		}
		
		currentPosition.setX(currentPosition.getX()+currentSpeed.getX());
		currentSpeed.setX(0);
		return true;
	}
	
	private boolean canClimbMove(int direction)
	{
		if(direction == RIGHT)
		{			
			currentSpeed.setX(step);
			currentSpeed.setY(-step);
			for (int i = world.SIZE_CELL; i < height; i+=world.SIZE_CELL) 
			{
				if ((world.getElement(world.pointToCellX(currentPosition.getX()+width), world.pointToCellY(currentPosition.getY())) instanceof Ground ||
						world.getElement(world.pointToCellX(currentPosition.getX()+width), world.pointToCellY(currentPosition.getY())) == null) &&
						world.getElement(world.pointToCellX(currentPosition.getX()+width+step), world.pointToCellY(currentPosition.getY()-i)) != null)
					return false;
			}
		}
		else
		{
			for (int i = world.SIZE_CELL; i < height; i+=world.SIZE_CELL) 
			{
				currentSpeed.setX(-step);
				currentSpeed.setY(-step);
				if ((world.getElement(world.pointToCellX(currentPosition.getX())-1, world.pointToCellY(currentPosition.getY())) instanceof Ground ||
						world.getElement(world.pointToCellX(currentPosition.getX())-1, world.pointToCellY(currentPosition.getY())) == null) &&
						world.getElement(world.pointToCellX(currentPosition.getX())-1, world.pointToCellY(currentPosition.getY()-i)) != null)
					return false;
			}
		}
		currentPosition.setX(currentPosition.getX()+currentSpeed.getX());
		currentPosition.setY(currentPosition.getY()+currentSpeed.getY());
		currentSpeed.setX(0);
		currentSpeed.setY(0);
		return true;
	}
	
	@Override
	public void jump()
	{
		inJump = true;
		int xFirst = currentPosition.getX(), yFirst = currentPosition.getY();
		Vector gravity = world.getGravity();
		currentSpeed.setY(currentSpeed.getY()+vectorJump.getY());
		currentSpeed.setX(20);
		Vector speedFirst = currentSpeed.clone();
//		System.out.println("VELOCITA_FIRST: "+speedFirst.getX()+" "+speedFirst.getY());
		for (int time = 1; !isInMaxHeight(xFirst, yFirst, gravity, speedFirst, time); time++) 
		{
//			System.out.println("VELOCITA_Current: "+currentSpeed.getX()+" "+currentSpeed.getY());
//			System.out.println("FIRST_POSITION: "+currentPosition.getX()+" "+currentPosition.getY());

			currentPosition.setX(xFirst + (speedFirst.getX() * time));
			currentPosition.setY((int) (yFirst + (speedFirst.getY() * time) + (0.5 * gravity.getY() * Math.pow(time, 2))));
			currentSpeed.setY(speedFirst.getY() + (gravity.getY() * time));
//			System.out.println("POSITION: "+currentPosition.getX()+" "+currentPosition.getY());
		}
		currentSpeed.setY(0);
		currentSpeed.setX(0);
		world.update(this, xFirst, yFirst);
		
		System.out.println("FIRST_POSITION_LOL: "+currentPosition.getX()+" "+currentPosition.getY());
		fall();
		System.out.println("POSITION: "+currentPosition.getX()+" "+currentPosition.getY());
	}

	public boolean isInMaxHeight(int xFirst, int yFirst, Vector gravity, Vector speedFirst, int time)
	{
		int timeForMaxHeight = -(speedFirst.getY() / gravity.getY());
		System.out.println("TIME: "+time+"      TIME_HEIGHT: "+timeForMaxHeight);
		System.out.println("VELOCITA: "+speedFirst.getX()+" "+speedFirst.getY());
		System.out.println("CALCOLO: "+(yFirst - 0.5 * Math.pow((speedFirst.getY()), 2) / gravity.getY()));
		if (time == timeForMaxHeight + 1)
			return true;
		
//		for (int i = 0; i < height; i+=world.SIZE_CELL) 
//		{
//			if (speedFirst.getX() > 0)
//				if (world.getElement(world.pointToCellX(currentPosition.getX()+width+speedFirst.getX()), world.pointToCellY(currentPosition.getY()-i-speedFirst.getY())) != null)
//					return true;
//			else
//				if(world.getElement(world.pointToCellX(currentPosition.getX())-1, world.pointToCellY(currentPosition.getY()-i - speedFirst.getY())) != null)
//					return true;
//		}
		
		return false;
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

