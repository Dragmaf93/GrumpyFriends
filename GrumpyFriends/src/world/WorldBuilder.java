package world;

import org.jbox2d.dynamics.World;

public class WorldBuilder {

	private AbstractWorld worldToCreate;

	public void initializes(String typeWorld) {

		if (worldToCreate != null)
			AbstractWorld.setInstanceNull();

		AbstractWorld.initializes(typeWorld);
		worldToCreate = (AbstractWorld) AbstractWorld.getInstance();

	}

	public void setWorldDimension(int height, int width) {
		worldToCreate.setDimension(height, width);
	}

	public void addWalls(float fromX, float fromY, float toX, float toY) {
		if(fromX % Ground.WIDTH != 0) fromX-=fromX %Ground.WIDTH;
		if(fromY % Ground.HEIGHT != 0) fromY-=fromY %Ground.HEIGHT;
		if(toX % Ground.WIDTH != 0) toX-=toX %Ground.WIDTH;
		if(toY % Ground.HEIGHT != 0) toY-=toY %Ground.HEIGHT;
		
		if (fromX == toX) {
			if(fromY > toY){float tmp = fromY; fromY=toY; toY = tmp;}
			
			for (int i = (int) fromY; i <= toY; i += Ground.HEIGHT){
				worldToCreate.addGround(fromX, i);
			}
		} else if (fromY == toY){
			if(fromX > toX){float tmp = fromX;fromX=toX;toX = tmp;}

			for (int i = (int) fromX; i <= toX; i += Ground.WIDTH){
				worldToCreate.addGround(i,fromY);
			}
		}
	}

	public World getWorld() {
		return worldToCreate;
	}

}
