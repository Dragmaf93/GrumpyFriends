package gui.weapon;

import character.Character;
import element.weaponsManager.Weapon;
import element.weaponsManager.weapons.SimpleBomb;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;

public class GuiSimpleBomb implements WeaponGui {

	private Group launcherRoot;
	private Group bulletRoot;
	private Character character;
	
	private Rotate launcherRotate;
	private SimpleBomb bomb;
	private Circle circle;
	private Circle bullet;
	private Weapon currentWeapon;
	
	ImageView bombImage;
	
	public GuiSimpleBomb(Weapon bomb, Character character) {
		this.bomb = (SimpleBomb) bomb;
		this.character = character;
		this.currentWeapon = bomb;
		bulletRoot= new Group();
		launcherRoot = new Group();
//		
//		bombImage = new ImageView(new Image("file:image/weapons/SimpleBomb.png",0, bomb.getHeight(), true, true));

//		
		
	}
	@Override
	public Node getWeaponLauncher() {
		if(circle==null){
			circle = new Circle(character.getX()+character.getWidth(),
					character.getY()+character.getHeight()*0.2,bomb.getWidth());
			circle.setFill(Color.RED);
			launcherRoot.getChildren().add(circle);
//			launcherRoot.getChildren().add(bombImage);
			launcherRotate = new Rotate();
			launcherRotate.setAxis(Rotate.Z_AXIS);
			circle.getTransforms().add(launcherRotate);
		
		}
		return launcherRoot;
	}

	@Override
	public Node getWeaponBullet() {
		launcherRoot.getChildren().remove(circle);
		bullet = new Circle(bomb.getX(),bomb.getY(),bomb.getWidth());
		bulletRoot.getChildren().add(bullet);
		return bulletRoot;
	}

	@Override
	public void afterAttack() {
	}

	@Override
	public void updateLauncher() {
		launcherRotate.setPivotX(character.getX());
		launcherRotate.setPivotY(circle.getCenterY());
		launcherRotate.setAngle(Math.toDegrees(-character.getLauncher().getAngle()));
		circle.relocate(character.getX()+character.getWidth()+5,
				character.getY()+character.getHeight()*0.2);
//		bombImage.relocate(character.getX()+character.getWidth()+5,
//				character.getY()+character.getHeight()*0.2);
//		
	}

	@Override
	public void updateBullet() {
		bullet.relocate(bomb.getX(), bomb.getY());
		
		if(bomb.getTimer().timeEnded()){
			bomb.afterCountDown();
		}
		
	}

}
