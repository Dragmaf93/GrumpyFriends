package world;

import element.character.Character;

public interface World {

	abstract public void addCharacter(Character character);
	abstract public void addLinearGround(float x, float y, float width, float height);
	abstract public void addInclinedGround(float x, float y,float width, float height, int angleRotation);
	abstract public void setDimension(float width, float height);
	
	abstract public float getWidth();
	abstract public float getHeight();
}
