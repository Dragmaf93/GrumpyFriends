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
    public boolean attacked() {
	return attacked;
    }

    @Override
    public double getX() {
	return x;
    }

    @Override
    public double getY() {
	return y;
    }

    @Override
    public boolean isExploded() {
	return exploded;
    }

    public double getAngle() {
	return angle;
    }

    @Override
    public void setAttack(boolean b) {
	attacked = b;
    }

    @Override
    public void setAngle(double angle) {
	this.angle = angle;
    }

    @Override
    public void setX(double x) {
	this.x = x;
    }

    @Override
    public void decreaseHit() {
	hit--;
    }
    
    @Override
    public void setY(double y) {
	this.y = y;
    }

    @Override
    public void setExploded(boolean exploded) {
	this.exploded = exploded;
    }
}
