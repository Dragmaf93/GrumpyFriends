package world;

import java.util.List;

import physic.PhysicalContactListener;
import physic.PhysicalObjectManager;
import utils.Point;
import utils.Vector;


public class GameWorldBuilder implements WorldBuilder{

	
	private AbstractWorld worldToCreate;
	
	@Override
	public void initializes(String typeWorld) {

		if (worldToCreate == null)
			AbstractWorld.setInstanceNull();

		AbstractWorld.initializes(typeWorld);
		worldToCreate = (AbstractWorld) AbstractWorld.getInstance();
		PhysicalObjectManager.getInstance().setWorld(worldToCreate);

	}
	@Override
	public void setWorldDimension(float width, float height) {
		worldToCreate.setDimension(width, height);
	}

	@Override
	public void addLinearGround(float x, float y, float width, float height){ 
		worldToCreate.addLinearGround(x, y, width, height);
	}
	
	@Override
	public World getWorld() {
		return worldToCreate;
	}
	
	
	@Override
	public void addCharacter(String name, float x, float y) {
		
	}
	@Override
	public void addInclinedGround(float x, float y, float width, float height, int angleRotation) {
		worldToCreate.addInclinedGround(x, y, width, height, angleRotation);
	}
	@Override
	public org.jbox2d.dynamics.World getPhysicWorld() {
		return worldToCreate.getPhysicWorld();
	}
	
	@Override
	public void lastSettings() {
		worldToCreate.setContactListener();
	}
	@Override
	public void addLinearGround(List<Point> points) {
		worldToCreate.addLinearGround(points);
	}
	@Override
	public void addGenericGround(List<Point> points) {
		worldToCreate.addGenericGround(points);
	}
	@Override
	public void addInclinedGround(List<Point> points) {
		worldToCreate.addInclinedGround(points);
	}
	@Override
	public void addRoundGround(List<Point> points) {
		
	}
	@Override
	public void addRoundGround(Point start, Point end, Point control) {
		worldToCreate.addRoundGround(start, end, control);
	}


}
