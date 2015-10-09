package element.character;

import element.Weapon;

public interface Character 
{
	public final static int MAX_HEIGHT=5;
	
	public abstract boolean isDead();
	
	public abstract void move(int direction);
	public abstract void stopToMove();
	public abstract void jump();
	public abstract String getName();
	public abstract int getLifePoints();
	public abstract boolean equipWeapon(Weapon weapon);

	public abstract void setGrounded(boolean b);
	public abstract boolean isGrounded();
	
}
