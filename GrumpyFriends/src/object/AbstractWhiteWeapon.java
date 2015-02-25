package object;

public abstract class AbstractWhiteWeapon implements WhiteWeapon {
	
	protected int maxDamage;
	protected int scope;
	
	
	public AbstractWhiteWeapon(int maxDamage, int scope) {
		super();
		this.maxDamage = maxDamage;
		this.scope = scope;
	}

	public void setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

	@Override
	public int getMaxDamage() {
		
		return maxDamage;
	}

	@Override
	public int getScope() {
		
		return scope;
	}

}
