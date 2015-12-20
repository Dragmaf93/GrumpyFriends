package world;


import java.util.HashMap;
import java.util.List;

import character.Character;
import element.Ground;
import utils.Point;
import utils.Vector;

public interface World {

	public static final double DISTANCE_WORLDS_BORDER=700;
	
	abstract public void addCharacter(Character character);
	abstract public void addLinearGround(float x, float y, float width, float height);
	abstract public void addLinearGround(List<Point> points);
	
	abstract public void addRoundGround(Point start, Point end, Point control);
	abstract public void addRoundGround(List<Point> points);
	
	abstract public void addInclinedGround(List<Point> points);
	abstract public void addInclinedGround(float x, float y,float width, float height, int angleRotation);
	
	abstract public void addGenericGround(List<Point> points);
	
	abstract public void setDimension(float width, float height);
	
	abstract public float getWidth();
	abstract public float getHeight();
	abstract public Character getCharacter(String name);
	abstract public List<Ground> getGrounds();
	
	abstract public HashMap<String, Float> getHitCharacter();
	
	abstract public  org.jbox2d.dynamics.World getPhysicWorld();
	public abstract List<Character> getAllCharacters();
	
	public abstract void update();
	
	public abstract List<Character> getCharacatersOutOfWorld();
	public abstract String getType();
	
}
