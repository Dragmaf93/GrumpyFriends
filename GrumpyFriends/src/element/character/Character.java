package element.character;

import element.Element;
import element.Weapon;

public interface Character extends Element
{
	public final static int MAX_HEIGHT=5;
	
	public abstract boolean isDead();
	
	public abstract void move(int direction);
	
	public abstract void jump();
	
	public abstract int getLifePoints();
	
	public abstract boolean equipWeapon(Weapon weapon);
}
