package physic;


import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.JointDef;

public class PhysicalObjectCreator {
	
	private World world;
	
	private static PhysicalObjectCreator instance;
	
	private PhysicalObjectCreator(){
		
	}
	public void setWorld(world.World world){
		this.world=world.getPhysicWorld();
	}
	
	public static PhysicalObjectCreator getInstance(){
		if(instance==null){
			instance=new PhysicalObjectCreator();
					
		}
		return instance;
	}

	public PhysicalObject buildPhysicObject(PhysicalObject objectTobuild){
		objectTobuild.buildSelf(world);
		return objectTobuild;
	}
	
	public Joint createJointe(JointDef definition){
		return world.createJoint(definition);
		
	}
}
