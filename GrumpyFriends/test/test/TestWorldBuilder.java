package test;


import world.World;
import world.WorldBuilder;

public class TestWorldBuilder implements WorldBuilder {

	private World world; 
	
	@Override
	public void initializes(String typeWorld) {
		world = new TestWorld();
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
		
		world.addCharacter(new TestCharacter(name, x, y));
	}

	@Override
	public World getWorld(){
		((TestWorld) world).complete();
		return world;
	}

}
