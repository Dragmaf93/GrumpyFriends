package gui.drawer;

import character.Character;
import element.weaponsManager.Weapon;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

public class GuiViewfinder {

	private static final String IMAGE_PATH="file:image/weapons/viewfinder.png";
	private static final double SIZE=50;
	private static final double DISTANCE_LAUNCHER=150;
	
	private ImageView image;
	private Rotate rotation;
	
	public GuiViewfinder(Group root) {
		image= new ImageView(new Image(IMAGE_PATH));
		image.setFitHeight(SIZE);
		image.setFitWidth(SIZE);
		image.setSmooth(true);
		
		rotation = new Rotate();
		rotation.setAxis(Rotate.Z_AXIS);
		
		image.getTransforms().add(rotation);
		root.getChildren().add(image);
		

	}
	
	public void update(Character character,Weapon weapon){
		rotation.setPivotX(image.getX()-DISTANCE_LAUNCHER );
		rotation.setPivotY(image.getY()+weapon.getHeight());
		rotation.setAngle(Math.toDegrees(-character.getLauncher().getAngle()));
		image.relocate(character.getLauncher().getX() + DISTANCE_LAUNCHER,
			character.getLauncher().getY()-weapon.getHeight()-SIZE/2);
	}
	
	
}
