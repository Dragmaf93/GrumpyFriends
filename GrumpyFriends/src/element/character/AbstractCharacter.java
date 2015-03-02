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
	protected Vector jump;
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
	}

//	public int fall() 
//	{
//		int depth=0;
//		int gravity = world.getGravity();
//		int xFirst = currentPosition.getX(), yFirst = currentPosition.getY();
//		
//		Element something = isThereSomething();
//		
//		if(something == null){
////			currentPosition.setY(yFirst+gravity);
//			depth = gravity;
//		}
//		else if(something instanceof Ground){
//			for(int i = 1; i <= gravity; i++, depth++){
//				if(world.getElement(currentPosition.getX(), currentPosition.getY()+i) != null ){
//					break;
//				}
//				if(world.getElement(currentPosition.getX()+1, currentPosition.getY()+i) != null )
//					break;
//			}
//		}
//		else if(something instanceof AbstractCharacter){
//			
//			depth+=MAX_HEIGHT-1;
//			
//		}
//		currentPosition.setY(currentPosition.getY()+depth);
//		
//		System.out.println(yFirst+" "+currentPosition.getY());
//		world.update(this, xFirst, yFirst);
//		return depth;
//	}
//	
	private Position positionAfterFall(Element something){
		int direction=-1;
		
		if(world.getElement(currentPosition.getX()+1, currentPosition.getY()+something.getHeight()) != null ){
				direction=RIGHT;
		}
		else if(world.getElement(currentPosition.getX()-1, currentPosition.getY()+something.getHeight()) != null){
				direction=LEFT;
			}
		else if(world.getElement(currentPosition.getX(), currentPosition.getY()+something.getHeight()) !=null){
				direction=-1;				
		}
		
		if(direction==-1){
			if(canSimplyMove(LEFT))
				move(LEFT);
			else if(canSimplyMove(RIGHT))
				move(RIGHT);
		}
		else{
			if(canSimplyMove(direction))
				move(direction);
		}
		return null;
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
	//			fall();
				return;
			}
			if (canClimbMove(direction))
			{
				world.update(this, xFirst, yFirst);
	//			fall();
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
				System.out.println("X "+currentPosition.getX()+" "+world.pointToCellX(currentPosition.getX()+width)+ " Y " +currentPosition.getY()+" "+(world.pointToCellY(currentPosition.getY()-i)));
				if (world.getElement(world.pointToCellX(currentPosition.getX()+width), world.pointToCellY(currentPosition.getY()-i)) != null)
					return false;
			}
			currentPosition.setX(currentPosition.getX()+currentSpeed.getX());
			currentSpeed.setX(0);
			return true;
		}
		currentSpeed.setX(-width/2);
		for (int i = 0; i < height; i+=world.SIZE_CELL) 
		{
			if (world.getElement(world.pointToCellX(currentPosition.getX())-1, world.pointToCellY(currentPosition.getY()-i)) != null)
				return false;
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
			currentSpeed.setY(step);
			for (int i = world.SIZE_CELL; i < height; i+=world.SIZE_CELL) 
			{
				if (world.getElement(world.pointToCellX(currentPosition.getX()+width), world.pointToCellY(currentPosition.getY())) instanceof Ground &&
						world.getElement(world.pointToCellX(currentPosition.getX()+width), world.pointToCellY(currentPosition.getY()-i)) != null)
					return false;
			}
			currentPosition.setX(currentPosition.getX()+currentSpeed.getX());
			currentPosition.setY(currentPosition.getY()-currentSpeed.getY());
			currentSpeed.setX(0);
			currentSpeed.setY(0);
			return true;
		}
		
		for (int i = 1; i <= height; i++) 
		{
			if (world.getElement(currentPosition.getX()-width, currentPosition.getY()) instanceof Ground &&
					world.getElement(currentPosition.getX()-1, currentPosition.getY()-i) != null)
				return false;
		}
		currentPosition.setX(currentPosition.getX()+currentSpeed.getX());
		currentPosition.setY(currentPosition.getY()+currentSpeed.getY());
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
//		fall();
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

