package physic;


import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.JointDef;

import element.weaponsManager.ExplosiveObject;
import physic.weapon.PhysicalBomb;
import physic.weapon.PhysicalWeapon;

public class PhysicalObjectManager {
	
	private World world;
	
	private static final int NUM_RAYS=32;

	private static final float DEGTORAD = 0.0174532925199432957f;
	
	private static PhysicalObjectManager instance;
	
	private List<Body> toRemove;
	
	private PhysicalObjectManager(){
		toRemove = new ArrayList<Body>();
	}
	public void setWorld(world.World world){
		this.world=world.getPhysicWorld();
	}
	
	public static PhysicalObjectManager getInstance(){
		if(instance==null){
			instance=new PhysicalObjectManager();
					
		}
		return instance;
	}

	public PhysicalObject buildPhysicObject(PhysicalObject objectTobuild){
		objectTobuild.buildSelf(world);
		return objectTobuild;
	}
	
	public PhysicalWeapon buildPhysicWeapon(PhysicalWeapon objectTobuild){
		objectTobuild.buildSelf(world);
		return objectTobuild;
	}
	
	public Joint createJointe(JointDef definition){
		return world.createJoint(definition);
		
	}
	
	public void removePhysicalObject(PhysicalObject object){
//		world.destroyBody(object.getBody());
		toRemove.add(object.getBody());	
	}
	
	public void removePhysicalWeapon(PhysicalWeapon weapon){
		toRemove.add(weapon.getBody());	
//		world.destroyBody(weapon.getBody());
	}
	public void makeAnExplosion(ExplosiveObject explosiveObject){
		if(explosiveObject!=null){
			Vec2 center = explosiveObject.getCenter();
			
			for(int i = 0; i < NUM_RAYS; i++){
				float angle = (i/(float)(NUM_RAYS))*360*DEGTORAD;
				Vec2 rayDir = new Vec2((float)Math.sin(angle),(float)Math.cos(angle));
				Vec2 rayEnd = new Vec2();
				rayEnd.x= center.x + explosiveObject.getBlastPower()*rayDir.x;
				rayEnd.y= center.y + explosiveObject.getBlastPower()*rayDir.y;
				
				RayCastClosestCallback callback = new RayCastClosestCallback();
				world.raycast(callback,center,rayEnd);
				if(callback.body!=null)
					applyBlastImpulse(callback.body, callback.point,explosiveObject);
			
			}
		}
	}
	public void destroyBodies(){
		for (Body body : toRemove) {
			world.destroyBody(body);
		}
		toRemove.clear();
	}
	private void applyBlastImpulse(Body body, Vec2 applyPoint, ExplosiveObject explosiveObject){
		if(body.equals(explosiveObject.getBody()) || body.getType()!=BodyType.DYNAMIC) return;
	
		Vec2 blastDir = new Vec2();
		blastDir.x=applyPoint.x - explosiveObject.getCenter().x;
		blastDir.y=applyPoint.y - explosiveObject.getCenter().y;
	
		float distance = blastDir.normalize();
		
		if(distance==0) return;
		
		float invDistance = 1/distance;
		float impulseMag = explosiveObject.getBlastPower() *invDistance*invDistance;
		impulseMag = MathUtils.min(impulseMag, 500.0f);
		Vec2 impulse = new Vec2();
		impulse.x=blastDir.x*impulseMag;
		impulse.y=blastDir.y*impulseMag;
		System.out.println("Impuls          "+body);
		body.applyLinearImpulse(impulse, applyPoint,true);
	
	}
}
