package element.weaponsManager;

import physic.weapon.PhysicalWeapon;

public abstract class AbstractWeapon implements Weapon {

    protected PhysicalWeapon physicalWeapon;
    protected int hit;
    protected double x, y;
    protected double angle;
    protected boolean exploded;
    protected boolean attacked;

    @Override
    public PhysicalWeapon getPhysicalWeapon() {
	return physicalWeapon;
    }

    @Override
    public boolean finishHit() {
	return hit == 0;
    }

    @Override
    public  synchronized boolean attacked() {
	return attacked;
    }

    @Override
    public  synchronized double getX() {
	return x;
    }

    @Override
    public  synchronized double getY() {
	return y;
    }

    @Override
    public  synchronized boolean isExploded() {
	return exploded;
    }

    public  synchronized double getAngle() {
	return angle;
    }

    @Override
    public  synchronized void setAttack(boolean b) {
	attacked = b;
    }

    @Override
    public  synchronized void setAngle(double angle) {
	this.angle = angle;
    }

    @Override
    public  synchronized void setX(double x) {
	this.x = x;
    }

    @Override
    public  synchronized void decreaseHit() {
	hit--;
    }
    
    @Override
    public  synchronized void setY(double y) {
	this.y = y;
    }

    @Override
    public  synchronized void setExploded(boolean exploded) {
	this.exploded = exploded;
    }
}
