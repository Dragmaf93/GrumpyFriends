package world;

import world.World;

public interface WorldBuilder {
	
	abstract public void initializes(String typeWorld);

	abstract public void setWorldDimension(float width, float height);
	
	abstract public void addLinearGround(float x, float y, float width, float height);
	
	abstract public void addInclinedGround(float x, float y, float width, float height, int angleRotation);
	
	abstract public void addCharacter(String name,float x, float y);
	
	abstract public void lastSettings();
	
	abstract public World getWorld();
	abstract public org.jbox2d.dynamics.World getPhysicWorld();
	
}
