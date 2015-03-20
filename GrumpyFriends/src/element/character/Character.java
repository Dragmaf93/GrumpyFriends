package element.character;

import physicEngine.MovableElement;
import element.Weapon;

public interface Character extends MovableElement
{
	public final static int MAX_HEIGHT=5;
	
	public abstract boolean isDead();
	
	public abstract void move(int direction);
	public abstract void stopToMove();
	public abstract void jump();
	
	public abstract int getLifePoints();
	public abstract boolean equipWeapon(Weapon weapon);
	
}
