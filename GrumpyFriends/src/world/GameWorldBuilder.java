package world;



public class GameWorldBuilder implements WorldBuilder{

	private AbstractWorld worldToCreate;
	
	@Override
	public void initializes(String typeWorld) {

		if (worldToCreate != null)
			AbstractWorld.setInstanceNull();

		AbstractWorld.initializes(typeWorld);
		worldToCreate = (AbstractWorld) AbstractWorld.getInstance();

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
		// TODO Auto-generated method stub
		
	}

}
