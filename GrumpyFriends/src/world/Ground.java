package world;

import javafx.scene.image.Image;




public class Ground extends AbstractWorldComponent {
	
	Image image;
	public Ground(int x, int y) {
		super(x, y);
		height = 20;
		width = 20;
	//TODO torniamo
	}
	
	@Override
	public String toString() {
		return "GROU";
	}

	@Override
	public Vector getCurrentSpeed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPosition(Vector position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCurrentSpeed(Vector speed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isFluttering() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMoving() {
		// TODO Auto-generated method stub
		return false;
	}
	

}
