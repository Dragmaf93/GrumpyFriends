package character;



import com.fasterxml.jackson.databind.JsonNode;

import element.Element;
import element.weaponsManager.Launcher;
import element.weaponsManager.Weapon;
import element.weaponsManager.WeaponsManager;
import physic.PhysicalObject;
import utils.Vector;
import world.World;

public interface Character extends Element
{
	public final static int RIGHT = 1;
	public final static int LEFT = -1;
	
	public final static int STOP = 0;
	public final static int INCREASE_AIM =1;
	public final static int DECREASE_AIM =-1;
	public final static int MAX_HEIGHT=5;
	
	public abstract boolean isDead();
	public abstract boolean isOutWorld();
	
	
	public abstract void move(int direction);
	public abstract int getCurrentDirection();
	public abstract void stopToMove();
	public abstract void jump();
	public abstract String getName();
	public abstract int getLifePoints();
	
	public abstract Launcher getLauncher();
	public abstract void equipWeapon(String weaponName);
	public abstract void unequipWeapon();
	public abstract void attack(float power);
	public abstract void changeAim(float direction);
	public abstract Vector getAim();
	
	public abstract String getLastEquippedWeapon();
	
	public abstract void setGrounded(boolean b);
	public abstract boolean isGrounded();

	public abstract boolean attacked();
	
	public abstract void prepareForTurn();
	public abstract void decreaseLifePoints(int points);
	
	public abstract boolean sufferedDamage();
	public abstract void endTurn();
	public abstract PhysicalObject getPhysicalObject();

	public abstract Weapon getEquipWeapon();

	public abstract boolean finishedTurn();

	public abstract boolean isSleeping();

	public abstract int getLastDamagePoints();

	public abstract WeaponsManager getInventoryManager();

	public abstract Team getTeam();
	
	public abstract void afterDeath();

	public abstract void setStartedPosition(float x, float y);

	public abstract void setDied(boolean bool);
	
	public abstract void setPosition(double x, double y);
	
	public abstract void update();
	
	public abstract boolean isActiveLauncher();
	public abstract boolean isMoving();
	public abstract boolean isJumping();
	public abstract boolean isFalling();
	public abstract void setWorld(World battlefield);
	public abstract void reset();
	
	public abstract float getPowerJump();
	public abstract Vector getSpeedVector();
	
	public abstract void setMoving(boolean moving);
	public abstract void setJumping(boolean jumping);
	public abstract void setFalling(boolean falling);
	public abstract void setOutWorld(boolean out);
	public abstract void setEndTurn(boolean endTurn);
	public abstract void setActiveLauncher(boolean activeLauncher);
	public abstract void setSufferedDamage(boolean asBoolean);
	public abstract void setLifePoints(int asInt);
	public abstract void setCurrentDirection(int asInt);
	public abstract void setSpeedVector(float asDouble, float asDouble2);
	public abstract void setDamagePoints(int asInt);
	public abstract void setLastEquippedWeaponName(String asText);
	
}
