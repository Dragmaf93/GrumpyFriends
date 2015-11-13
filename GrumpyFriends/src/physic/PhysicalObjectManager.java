package physic;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.JointDef;

import element.weaponsManager.ExplosiveObject;
import physic.weapon.PhysicalBomb;
import physic.weapon.PhysicalWeapon;

public class PhysicalObjectManager {
	
	private World world;
	
	private static final int NUM_RAYS=360;
	private static final int MAX_BLAST_RAYS =64;
	
	private static PhysicalObjectManager instance;
	
	private List<Body> toRemove;

	private HashMap<String, Float> hitCharacters;
	
	
	
	private PhysicalObjectManager(){
		toRemove = new ArrayList<Body>();
		hitCharacters = new HashMap<>();
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
	public void makeAnExplosionWithParticle(ExplosiveObject explosiveObject){
//		for (int i = 0; i < NUM_RAYS; i++) {
//			if(blastParticleBodies[i]!=null){
//				world.destroyBody(blastParticleBodies[i]);
//				blastParticleBodies[i]=null;
//			}
//		}
//		
//		BodyDef bd = new BodyDef();
//		FixtureDef fd = new FixtureDef();
//		if(explosiveObject!=null){
//			Vec2 center = explosiveObject.getCenter();
//			for(int i = 0; i < NUM_RAYS; i++ ){
//				float angle = (i/(float)NUM_RAYS)* 360 *MathUtils.DEG2RAD;
//				System.out.println(Math.toDegrees(angle));
//				Vec2 rayDir = new Vec2(MathUtils.sin(angle),MathUtils.cos(angle));
//				bd.type=BodyType.DYNAMIC;
//				bd.fixedRotation=true;
//				bd.bullet=true;
//				bd.linearDamping=10;
//				bd.gravityScale=0;
//				bd.position=center;
//				bd.linearVelocity.x = 0.125f*explosiveObject.getBlastPower()*rayDir.x;
//				bd.linearVelocity.y = 0.125f*explosiveObject.getBlastPower()*rayDir.y;
//				Body body = world.createBody(bd);
//				
//				CircleShape circleShape = new CircleShape();
//				circleShape.setRadius(0.05f);
//				
//				fd.shape=circleShape;
//				fd.density =60/(float)NUM_RAYS;
//				fd.friction = 0;
//				fd.restitution=0.99f;
//				fd.filter.groupIndex=-1;
//				body.createFixture(fd);
//				
//				blastParticleBodies[i]=body;
//			}
//		}
		
		
	}
	public void makeAnExplosion(ExplosiveObject explosiveObject){
		if(explosiveObject!=null){
			Vec2 center = explosiveObject.getCenter();
			
			for(int i = 0; i < NUM_RAYS; i++){
				float angle = (i/(float)(NUM_RAYS))*360*MathUtils.DEG2RAD;
				Vec2 rayDir = new Vec2((float)Math.sin(angle),(float)Math.cos(angle));
				Vec2 rayEnd = new Vec2();
				rayEnd.x= center.x + explosiveObject.getBlastRadius()*rayDir.x;
				rayEnd.y= center.y + explosiveObject.getBlastRadius()*rayDir.y;
				
				RayCastClosestCallback callback = new RayCastClosestCallback();
				world.raycast(callback,center,rayEnd);
				if(callback.body!=null)
					applyBlastImpulse(callback.body, callback.point,explosiveObject);
			
			}
		}
	}
	
	public HashMap<String, Float> getHitCharacters(){
		return hitCharacters;
	}
	
	public void destroyBodies(){
		for (Body body : toRemove) {
			world.destroyBody(body);
		}
		toRemove.clear();
	}
	private void addHitCharacter(String name, float damage){
		if(hitCharacters.containsKey(name) && hitCharacters.get(name) < damage){
			hitCharacters.put(name, damage);
		}else if(!hitCharacters.containsKey(name)){
			hitCharacters.put(name, damage);			
		}
	}
	
	private void applyBlastImpulse(Body body, Vec2 applyPoint, ExplosiveObject explosiveObject){
		if(body.equals(explosiveObject.getBody()) || body.getType()!=BodyType.DYNAMIC) return;
	
		Vec2 blastDir = new Vec2();
		blastDir.x=applyPoint.x - explosiveObject.getCenter().x;
		blastDir.y=applyPoint.y - explosiveObject.getCenter().y;
		
		float distance = blastDir.normalize();
		if(distance==0) return;	
			
		if(body.getUserData() instanceof String){
			System.out.println( distance+"      "+ explosiveObject.getBlastRadius());
			addHitCharacter((String) body.getUserData(),((PhysicalWeapon)explosiveObject).getMaxDamage()-
					((PhysicalWeapon)explosiveObject).getMaxDamage()*distance/
					explosiveObject.getBlastRadius());
			
		}
		float invDistance = 1/distance;
		float impulseMag = explosiveObject.getBlastPower() *invDistance;
		Vec2 impulse = new Vec2();

		impulse.x=blastDir.x*impulseMag;
		impulse.y=blastDir.y*impulseMag;
		body.applyLinearImpulse(impulse, applyPoint,true);
	
	}
}
