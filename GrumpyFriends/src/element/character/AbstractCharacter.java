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
	
	protected Vector position;
	
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
	protected Vector speed;
	
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
		this.position= new Vector(x,y);
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

		this.position= new Vector(x,y);
		this.position0= new Vector(x,y);
		this.speed = new Vector(0, 0);
		this.speed0 = new Vector(0, 0);
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
		int xFirst = position.getX(), yFirst = position.getY();
		Vector speedFirst = speed.clone();
		Vector nextPosition = position.clone();
		for (int time = 1; isFall(xFirst, yFirst, time, gravity, speedFirst); time++) 
		{
			position.setX(xFirst + (speedFirst.getX() * time));
			position.setY((int) (yFirst + (speedFirst.getY() * time) + (0.5 * gravity.getY() * time)));
			speed.setY(gravity.getY() * time);
		}
		
		int cellY = world.pointToCellY(position.getY());
		position.setY(world.cellYToPoint(cellY+1)-1);
//		System.out.println("POINT: "+world.pointToCellX(position.getX())+" "+world.pointToCellY(position.getY()));
//		System.out.println("CELL: "+world.cellXToPoint(world.pointToCellX(position.getX()))+" "+world.cellYToPoint(world.pointToCellY(position.getY())));

		System.out.println("SETTA CADUTA: "+position.getX()+" "+position.getY());
		world.update(this, xFirst, yFirst);
		speed.setX(0);
		speed.setY(0);
		inJump = false;
		return depth;
	}
	
	public boolean isFall(int x, int y, int time, Vector gravity, Vector speedFirst)
	{
		int coordinateX = x + (speedFirst.getX() * time);
		int coordinateY = (int) (y + (speedFirst.getY() * time) + (0.5 * gravity.getY() * time));
		
//		System.out.println("POINT: "+world.pointToCellX(coordinateX)+" "+world.pointToCellY(coordinateY));
//		System.out.println("CELL: "+world.cellXToPoint(world.pointToCellX(coordinateX))+" "+world.cellYToPoint(world.pointToCellY(coordinateY)));
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
			if(world.getElement(position.getX(), position.getY()+i) != null ){
				return world.getElement(position.getX(), position.getY()+i);
			}
			if(world.getElement(position.getX()+1, position.getY()+i) !=null){
				return world.getElement(position.getX()+1, position.getY()+i);
			}
			if(world.getElement(position.getX()-1, position.getY()+i) instanceof AbstractCharacter)
				return world.getElement(position.getX()-1, position.getY()+i);
		}
		return null;
	}
	
	@Override
	public int getX() {
	
		return position.getX();
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

		return speed;
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
		return position;
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
				speed.setX(step);
			else
				speed.setX(-step);
			
			inMovement=true;
			physicEngine.addElementToMove(this);
		}
		
		System.out.println("Pos0: "+position.getX()+" "+position.getY());
	}
	
	@Override
	public void stopToMove()
	{
		if (!inJump || ! inFall)
		{
			inMovement=false;
			speed.setX(0);
			speed.setY(0);
			physicEngine.removeElement(this);
		}
		System.out.println("Pos_Stap: "+position.getX()+" "+position.getY());
	}
	
	@Override
	public void setPosition(Vector position) 
	{
		this.position = position;
	}
	
	@Override
	public void doSingleMove() 
	{
		if((!inJump || !inFall) && inMovement){
			physicEngine.moveOnGround(this);
		}
		else if(inJump || inFall){
			physicEngine.moveOnAir(this);
		}
	}
	@Override
	public void jump()
	{
		if (!inFall)
		{
			inJump = true;
			speed.setY(powerJump);
			physicEngine.addElementToMove(this);
		}
	}
	
	@Override
	public void setSpeed(Vector speed) {
		speed.set(speed);
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
		return position.getY();
	}
	public void setX(int x){
		this.position.setX(x);
	}
	public void setY(int y) {
		this.position.setY(y);
	}

	public ArrayList<Weapon> getWeaponList() {

		return weaponList;
	}
	@Override
	public void afterCollision() 
	{
		// TODO Auto-generated method stub
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

	@Override
	public void setFall(boolean fall) 
	{
		this.inFall = fall;
	}
	
	@Override
	public void setJump(boolean jump) 
	{
		this.inJump = jump;
	}
	
	@Override
	public void setMovement(boolean movement) 
	{
		this.inMovement = movement;
	}
}



//TODO fare o non fare update???? 0.o

