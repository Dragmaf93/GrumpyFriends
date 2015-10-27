package world;


import java.util.List;

import character.Character;
import element.Ground;

public interface World {

	abstract public void addCharacter(Character character);
	abstract public void addLinearGround(float x, float y, float width, float height);
	abstract public void addInclinedGround(float x, float y,float width, float height, int angleRotation);
	abstract public void setDimension(float width, float height);
	
	abstract public float getWidth();
	abstract public float getHeight();
	abstract public Character getCharacter();
	abstract public List<Ground> getGrounds();
	
	abstract public  org.jbox2d.dynamics.World getPhysicWorld();
	public abstract List<Character> getAllCharacters();
	
	public abstract void update();
	
}
