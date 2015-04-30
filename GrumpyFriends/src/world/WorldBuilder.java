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

	public void addWalls(float fX, float fY, float tX, float tY) {
		float fromX=Utils.toPosX(fX),fromY=Utils.toPosY(fY),toX=Utils.toPosX(tX),toY=Utils.toPosY(tY);
		
		if(fromX % Ground.WIDTH_JD2BOX != 0) fromX-=fromX %Ground.WIDTH_JD2BOX;
		if(fromY % Ground.HEIGHT_JD2BOX != 0) fromY-=fromY %Ground.HEIGHT_JD2BOX;
		if(toX % Ground.WIDTH_JD2BOX != 0) toX-=toX %Ground.WIDTH_JD2BOX;
		if(toY % Ground.HEIGHT_JD2BOX != 0) toY-=toY %Ground.HEIGHT_JD2BOX;
		
		if (fromX == toX) {
			if(fromY > toY){float tmp = fromY; fromY=toY; toY = tmp;}
			
			for (int i = (int) fromY; i <= toY; i += Ground.HEIGHT_JD2BOX*2){
				worldToCreate.addGround(fromX, i);
			}
		} else if (fromY == toY){
			if(fromX > toX){float tmp = fromX;fromX=toX;toX = tmp;}

			for (int i = (int) fromX; i <= toX; i += Ground.WIDTH_JD2BOX*2){
				worldToCreate.addGround(i,fromY);
			}
		}
	}

	public World getWorld() {
		return worldToCreate;
	}

}
