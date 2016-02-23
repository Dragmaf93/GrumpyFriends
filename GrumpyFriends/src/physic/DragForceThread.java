package physic;

import org.jbox2d.common.Vec2;

import element.weaponsManager.ExplosiveObject;
import element.weaponsManager.Weapon;
import element.weaponsManager.weapons.SimpleMissile;
import physic.weapon.PhysicalWeapon;


public class DragForceThread extends Thread {

	
	private Weapon bullet;
	public DragForceThread(Weapon simpleMissile) {
		bullet=simpleMissile;
	}
	
	@Override
	public void run() {
		while(!((ExplosiveObject) bullet.getPhysicalWeapon()).isExplosed())
		{
			Vec2 flightDirection = bullet.getPhysicalWeapon().getBody().getLinearVelocity();
			float angleDirection = (float) Math.atan2(flightDirection.y, flightDirection.x);
			bullet.getPhysicalWeapon().setTransform(bullet.getPhysicalWeapon().getBody().getPosition(), angleDirection);
		}
		bullet.afterAttack();
	}
}
