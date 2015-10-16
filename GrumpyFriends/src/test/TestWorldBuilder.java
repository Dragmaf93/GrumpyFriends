package test;


import element.character.Chewbacca;
import physic.PhysicalObjectCreator;
import world.World;
import world.WorldBuilder;

public class TestWorldBuilder implements WorldBuilder {

	private World world; 
	
	@Override
	public void initializes(String typeWorld) {
		world = new TestWorld();
		PhysicalObjectCreator.getInstance().setWorld(world);
	}

	@Override
	public void setWorldDimension(float width, float height) {
		world.setDimension(width, height);
	}

	@Override
	public void addLinearGround(float x, float y, float width, float height) {
		world.addLinearGround(x, y, width, height);

	}

	@Override
	public void addInclinedGround(float x, float y, float width, float height, int angleRotation) {
		world.addInclinedGround(x, y, width, height, angleRotation);
	}

	@Override
	public void addCharacter(String name, float x, float y) {
		world.addCharacter(new Chewbacca(name, x, y,null));
	}

	@Override
	public World getWorld(){
		return world;
	}

	@Override
	public org.jbox2d.dynamics.World getPhysicWorld() {
		return world.getPhysicWorld();
	}

	@Override
	public void lastSettings() {
		System.out.println(world);
		((TestWorld)world).setContactListener();
		
	}

}
