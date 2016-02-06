package test;


import java.util.List;

import character.WhiteStormtrooper;
import physic.PhysicalObjectManager;
import utils.Point;
import utils.Vector;
import world.World;
import world.WorldBuilder;

public class TestWorldBuilder implements WorldBuilder {

	private World world; 
	
	@Override
	public void initializes(String typeWorld) {
		world = new TestWorld();
		PhysicalObjectManager.getInstance().setWorld(world);
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
		world.addCharacter(new WhiteStormtrooper(name, x, y,null,world));
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
		((TestWorld)world).setContactListener();
		
	}

	@Override
	public void addGenericGround(List<Point> points) {
		world.addGenericGround(points);
	}

	@Override
	public void addInclinedGround(List<Point> points) {
		world.addInclinedGround(points);
	}

	@Override
	public void addLinearGround(List<Point> points) {
		world.addLinearGround(points);
	}

	@Override
	public void addRoundGround(List<Point> points) {
		world.addRoundGround(points);
	}

	@Override
	public void addRoundGround(Point start, Point end, Point control) {
		world.addRoundGround(start, end, control);
	}

}
