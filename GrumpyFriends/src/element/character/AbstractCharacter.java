package element.character;

import java.util.ArrayList;

import physicEngine.PhysicEngine;
import physicEngine.Time;
import element.Element;
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
	protected PhysicEngine physicEngine;
	protected Team team;
	protected ArrayList<Weapon> weaponList;
	protected Weapon equippedWeapon;
	protected Vector vectorJump;
	protected Vector currentSpeed;
	
	protected boolean inFall;
	protected boolean inMovement;
	protected boolean inFluttering;
	protected boolean inJump;
	protected int step;
	private Vector position0;
	private Vector speed0;
	private long time0;
	
	public AbstractCharacter(int x, int y,
			Team team, ArrayList<Weapon> weaponList) {
		System.out.println("X : "+x+"Y : "+y);
		this.currentPosition= new Vector(x,y);
		this.world = AbstractWorld.getInstance();
		this.physicEngine = PhysicEngine.getInstace();
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
		this.physicEngine = PhysicEngine.getInstace();
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
		Vector nextPosition = currentPosition.clone();
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
		int coordinateY = (int) (y + (speedFirst.getY() * time) + (0.5 * gravity.getY() * time));
		
	
		if (world.getElement(world.pointToCellX(coordinateX), world.pointToCellY(coordinateY)) != null ||
				world.getElement(world.pointToCellX(coordinateX+width), world.pointToCellY(coordinateY)) != null){
				System.out.println("ELEMENT POS "+coordinateX + " "+ coordinateY+" "+world.getElement(world.pointToCellX(coordinateX), world.pointToCellY(coordinateY)));
				System.out.println("ELEMENT POS+WIDTH "+ (coordinateX+width) + " "+ coordinateY+" "+world.getElement(world.pointToCellX(coordinateX+width), world.pointToCellY(coordinateY)));
				return false;
				
		}
			
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
	public Vector getSpeed(){

		return currentSpeed;
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
	public Vector getPosition() {
		return currentPosition;
	}
	
	@Override
	public boolean isFluttering() {
		return inFluttering;
	}
	
	@Override
	public boolean isMoving() {
		return inMovement;
	}
	@Override
	public void move(int direction)
	{
		if (!inFall || !inJump)
		{
			if(direction == RIGHT)
				currentSpeed.setX(step);
			else
				currentSpeed.setX(-step);
		}
		inMovement=true;
		physicEngine.addElementToMove(this);
		
		System.out.println("FIRST_POSITION_LOL: "+currentPosition.getX()+" "+currentPosition.getY());
	}
	
	@Override
	public void stopToMove() {
		inMovement=false;
		currentSpeed.setX(0);
		currentSpeed.setY(0);
	}
	
	@Override
	public void setPosition(Vector position) 
	{
		currentPosition = position;
	}
	
	
	@Override
	public void jump()
	{
		inJump = true;
		if (!inFall)
		{
			currentSpeed.setY(powerJump);
			physicEngine.addElementToMove(this);
		}
		
	}
	
	@Override
	public void setSpeed(Vector speed) {
		currentSpeed.set(speed);
	}
	
	public boolean isInMaxHeight(int xFirst, int yFirst, Vector gravity, Vector speedFirst, int time)
	{
		int timeForMaxHeight = -(speedFirst.getY() / gravity.getY());
		System.out.println("TIME: "+time+"      TIME_HEIGHT: "+timeForMaxHeight);
		System.out.println("VELOCITA: "+speedFirst.getX()+" "+speedFirst.getY());
		System.out.println("CALCOLO: "+(yFirst - 0.5 * Math.pow((speedFirst.getY()), 2) / gravity.getY()));
		if (time == timeForMaxHeight + 1)
			return true;
		
		for (int i = 0; i < height; i+=world.SIZE_CELL) 
		{
			if (speedFirst.getX() > 0)
				if (world.getElement(world.pointToCellX(currentPosition.getX()+width+speedFirst.getX()), world.pointToCellY(currentPosition.getY()-i-speedFirst.getY())) != null)
					return true;
			else
				if(world.getElement(world.pointToCellX(currentPosition.getX())-1, world.pointToCellY(currentPosition.getY()-i - speedFirst.getY())) != null)
					return true;
		}
		
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

	@Override
	public long getTime0() {
		return time0;
	}

	@Override
	public void resetTime0() {
		time0 = System.currentTimeMillis();
	}

	@Override
	public void setSpeed0(Vector speed) {
		if(speed0==null) speed0 = new Vector();
		speed0.set(speed);
	}

	@Override
	public Vector getSpeed0() {
		return speed0;
	}

	@Override
	public void setPosition0(Vector vector) {
		if(position0==null) position0 = new Vector();
		position0.set(vector);
	}

	@Override
	public Vector getPosition0() {
		return position0;
	}

	
}



//TODO fare o non fare update???? 0.o

