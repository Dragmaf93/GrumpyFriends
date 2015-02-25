package element;

public abstract class AbstractFireWeapon implements FireWeapon {
	
	protected int maxDamage;
	protected int range;
	protected int damageArea;
	
	public AbstractFireWeapon(int maxDamage, int range, int damageArea){
		
		this.damageArea=damageArea;
		this.range=range;
		this.maxDamage=maxDamage;
	}

	public void setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public void setDamageArea(int damageArea) {
		this.damageArea = damageArea;
	}

	@Override
	public int getMaxDamage() {

		return maxDamage;
	}

	@Override
	public int getRange() {

		return range;
	}

	@Override
	public int getDamageArea() {

		return damageArea;
	}

}
