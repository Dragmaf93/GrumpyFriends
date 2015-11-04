package gui.weapon;

import character.Character;
import element.weaponsManager.Weapon;
import element.weaponsManager.weapons.SimpleBomb;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class GuiSimpleBomb implements WeaponGui {

	private Group launcherRoot;
	private Group bulletRoot;

	private Character character;
	private SimpleBomb bomb;

	private Rotate launcherRotate;

	// prove
	private Circle circle;
	private Circle bullet;

	private GuiExplosion explosion;

	private boolean finishAnimation;

	ImageView bombImage;

	public GuiSimpleBomb(Weapon bomb, Character character) {
		this.bomb = (SimpleBomb) bomb;
		this.character = character;
		bulletRoot = new Group();
		launcherRoot = new Group();
		finishAnimation = false;
		//
		// bombImage = new ImageView(new
		// Image("file:image/weapons/SimpleBomb.png",0, bomb.getHeight(), true,
		// true));

		//

	}

	@Override
	public Node getWeaponLauncher() {
		if (circle == null) {
			circle = new Circle(character.getX() + character.getWidth(), character.getY() + character.getHeight() * 0.2,
					bomb.getWidth());
			circle.setFill(Color.RED);
			launcherRoot.getChildren().add(circle);
			// launcherRoot.getChildren().add(bombImage);
			launcherRotate = new Rotate();
			launcherRotate.setAxis(Rotate.Z_AXIS);
			circle.getTransforms().add(launcherRotate);

		}
		return launcherRoot;
	}

	@Override
	public Node getWeaponBullet() {
		
		launcherRoot.getChildren().remove(circle);
		bullet = new Circle(bomb.getX(), bomb.getY(), bomb.getWidth(),Color.RED);
		explosion = new GuiExplosion(this);
		bulletRoot.getChildren().add(bullet);
		bulletRoot.getChildren().add(explosion.getExplosionNode());
		return bulletRoot;
	}

	@Override
	public void afterAttack() {
		if (finishAnimation == false) {
			finishAnimation = true;
			bomb.afterAttack();
		}
	}

	@Override
	public void updateLauncher() {
		launcherRotate.setPivotX(character.getX());
		launcherRotate.setPivotY(circle.getCenterY());
		launcherRotate.setAngle(Math.toDegrees(-character.getLauncher().getAngle()));
		circle.relocate(character.getX() + character.getWidth() + 5, character.getY() + character.getHeight() * 0.2);
		// bombImage.relocate(character.getX()+character.getWidth()+5,
		// character.getY()+character.getHeight()*0.2);
		//
	}

	@Override
	public void updateBullet() {

		bullet.relocate(bomb.getX(), bomb.getY());

		bomb.update();

		if (bomb.isExploded()) {
			explosion.playExplosionAnimation();
		}
	}

	@Override
	public boolean finishAnimation() {
		return finishAnimation;
	}

	@Override
	public Weapon getWeapon() {
		return bomb;
	}

	@Override
	public void removeBullet() {
		bulletRoot.getChildren().remove(bullet);
	}

}
