package world;

import object.Element;
import object.character.AbstractCharacter;

public interface World 
{
	public abstract Element getElement(int x, int y);

	public abstract void update(AbstractCharacter character, int xFirst, int yFirst);

	public abstract int getHeight();

	public abstract int getWidth();
			
}
