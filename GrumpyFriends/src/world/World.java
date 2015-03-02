package world;

import element.Element;
import element.character.AbstractCharacter;

public interface World 
{
	public static final int SIZE_CELL = 20;

	public abstract Element getElement(int x, int y);

	public abstract void update(AbstractCharacter character, int xFirst, int yFirst);

	public abstract int getHeight();

	public abstract int getWidth();

	public abstract Vector getGravity();
	
	public abstract int getNumberRow();

	public abstract int getNumberColumn();
			
	public abstract int pointToCellX(int x);
	
	public abstract int pointToCellY(int y);
	
}
