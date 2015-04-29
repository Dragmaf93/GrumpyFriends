package element.character;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import element.Weapon;
import world.AbstractDynamicElement;
import world.AbstractWorld;
import world.Utils;
import world.Vector;


public abstract class AbstractCharacter extends AbstractDynamicElement implements Character
{
	public final static int RIGHT = 1;
	public final static int LEFT = -1;
	
	
	
	protected String name;
	protected float height;
	protected float width;
	
	protected float powerJump;
	protected int lifePoints;
	protected World world;
	protected Team team;
	protected ArrayList<Weapon> weaponList;
	protected Weapon equippedWeapon;
	
	protected boolean inFall;
	protected boolean inMovement;
	protected boolean inFluttering;
	protected boolean inJump;
	protected int currentDirection;

	
	public AbstractCharacter(String name,float x, float y,float height, float width,
			Team team, ArrayList<Weapon> weaponList) {
		super(x, y);
		
		this.name = name;
		this.team = team;
		this.weaponList = weaponList;
		this.height = height;
		this.width = width;
		
		equippedWeapon = null;
		lifePoints = 100;
		inFall = false;
		inMovement = false;
		inJump = false;
		
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(width,height);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		
		body.createFixture(fixtureDef);
		
		
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
	
	public void setBoolean(){
		Vec2 vec = body.getLinearVelocity();
		
		if(vec.x==0f) inMovement = false;
		else
			inMovement=true;
		
		if(vec.y==0f) inJump =false;
		else
			inJump=true;
	}
	
	@Override
	public void move(int direction)
	{
		setBoolean();
		if((!inMovement && !inJump) 
				|| (inMovement && currentDirection!=direction)){
			currentDirection = direction;
			inMovement=true;
			body.setLinearVelocity(new Vec2(10f*currentDirection,0f));
		}
	}
	
	@Override
	public void stopToMove()
	{
		setBoolean();
		if(inMovement && !inJump){
			currentDirection=0;
			inMovement=false;
			body.setLinearVelocity(new Vec2(0f,0f));
		}
	}
	

	@Override
	public void jump()
	{
		setBoolean();
		
		if(!inJump){
			inJump=true;
			body.setLinearVelocity(new Vec2(body.getPosition().x*currentDirection,5f));
		}
	}
	
	@Override
	public int getLifePoints() 
	{
		return lifePoints;
	}
	
	public World getWorld() {
		
		return world;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public float getHeight() {
		return Utils.toPixelHeight(height);
	}

	@Override
	public float getWidth() {
		return Utils.toPixelWidth(width);
	}

	public ArrayList<Weapon> getWeaponList() {

		return weaponList;
	}

	public void setWeaponList(ArrayList<Weapon> weaponList) {
		this.weaponList = weaponList;
	}

}

