package element.character;

import element.Element;
import element.Weapon;

public interface Character extends Element
{
	public abstract boolean isDead();
	
	public abstract void moveLeft();
	
	public abstract void moveRight();
	
	public abstract void highJump(boolean right, boolean left);
	
	public abstract void longJump(boolean along);
	
	public abstract int getLifePoints();
	
	public abstract boolean equipWeapon(Weapon weapon);
}
