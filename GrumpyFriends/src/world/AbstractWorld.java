package world;


import java.util.HashMap;

import object.Element;
import object.Position;
import object.character.AbstractCharacter;


public abstract class AbstractWorld implements World 
{
	protected int height,width;
	protected HashMap<Position, Element> worldContainer;
	
	public AbstractWorld() 
	{	
		worldContainer = new HashMap<>();
	}
	
	@Override
	public Element getElement(int x, int y) 
	{
		return worldContainer.get(new Position(x, y));
	}

	@Override
	public void update(AbstractCharacter character, int xFirst, int yFirst) 
	{
		worldContainer.remove(new Position(xFirst,yFirst));
		worldContainer.put(character.getPosition(), character);
	}

	@Override
	public int getHeight() 
	{
		return height;
	}

	@Override
	public int getWidth() 
	{
		return width;
	}
}
