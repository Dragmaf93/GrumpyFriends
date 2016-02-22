package gui.weapon;

import character.Character;
import element.weaponsManager.Weapon;
import element.weaponsManager.weapons.SimpleMissile;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;

public class GuiSimpleMissile extends AbstractGuiWeapon {

	private final static double BAZOOKA_WIDTH = 110;
	private final static double BAZOOKA_HEIGHT = 50;

	private Rotate launcherRotate;
	private Rotate launcherRotateY;
	private Rotate bulletRotate;

	private ImageView launcherImage;
	private ImageView bulletImage;

	private GuiExplosion explosion;

	private boolean finishAnimation;

	public GuiSimpleMissile(Weapon weapon) {
		super(weapon);
		this.weapon = weapon;

		finishAnimation = false;

		bulletImage = new ImageView(new Image(
				"file:image/weapons/bazookaBullet.png"));
		bulletImage.setFitHeight(weapon.getHeight());
		bulletImage.setFitWidth(weapon.getWidth());

		launcherImage = new ImageView();
		// //

		launcherRotate = new Rotate();
		launcherRotateY = new Rotate();
		launcherRotateY.setAxis(Rotate.Y_AXIS);
		launcherRotate.setAxis(Rotate.Z_AXIS);

		launcherImage.getTransforms().add(launcherRotate);
		launcherImage.getTransforms().add(launcherRotateY);

		bulletRotate = new Rotate();
		bulletRotate.setAxis(Rotate.Z_AXIS);
		bulletImage.getTransforms().add(bulletRotate);
	}

	@Override
	public Node getWeaponLauncher() {
		if (!launcherRoot.getChildren().contains(launcherImage))
			launcherRoot.getChildren().add(launcherImage);
		launcherImage.setImage(characterAnimation
				.getCharacterWeaponLauncher(weapon));
		launcherImage.setFitWidth(characterAnimation.getWidth());
		launcherImage.setFitHeight(characterAnimation.getHeight());
		return launcherRoot;
	}

	@Override
	public Node getWeaponBullet() {

		if (!bulletRoot.getChildren().contains(bulletImage)) {
			explosion = new GuiExplosion(this);
			bulletRoot.getChildren().add(bulletImage);
			bulletRoot.getChildren().add(explosion.getExplosionNode());
		}
		return bulletRoot;
	}

	@Override
	public void updateLauncher() {
		super.updateLauncher();

		if (currentCharacter.getCurrentDirection() == Character.RIGHT) {
			
			launcherRotateY.setPivotX(launcherImage.getX()+80);
			launcherRotateY.setAngle(0);
			
			launcherRotate.setAxis(Rotate.Z_AXIS);
			launcherRotate.setPivotX(launcherImage.getX() + 75);
			launcherRotate.setPivotY(launcherImage.getY() + 85);
			launcherRotate.setAngle(Math.toDegrees(-currentCharacter
					.getLauncher().getAngle()));
		} else if (currentCharacter.getCurrentDirection() == Character.LEFT) {
			launcherRotateY.setPivotX(launcherImage.getX()+80);
			launcherRotateY.setAngle(180);
			launcherRotate.setAxis(Rotate.Z_AXIS);
			launcherRotate.setPivotX(launcherImage.getX() + 75);
			launcherRotate.setPivotY(launcherImage.getY() + 85);
			launcherRotate.setAngle(Math.toDegrees(-currentCharacter
					.getLauncher().getAngle())-180);
		}
		launcherImage.relocate(
				currentCharacter.getX() + characterAnimation.getValueX(),
				currentCharacter.getY() + characterAnimation.getValueY());
	}

	@Override
	public void resetItem() {
		finishAnimation = false;

	}

	@Override
	public void updateBullet() {
		weapon.update();
		if (!((SimpleMissile) weapon).isExploded()) {
			bulletRotate.setPivotX(launcherImage.getX());
			bulletRotate.setPivotY(launcherImage.getY());
			bulletRotate.setAngle(Math.toDegrees(-((SimpleMissile) weapon)
					.getAngle()));

			bulletImage.relocate(weapon.getX(), weapon.getY());

		} else {
			explosion.playExplosionAnimation();
		}
	}

	@Override
	public boolean finishAnimation() {
		return finishAnimation;
	}

	@Override
	public void afterAttack() {
		finishAnimation = true;
		weapon.afterAttack();
	}

	@Override
	public Weapon getWeapon() {
		return weapon;
	}

	@Override
	public void removeBullet() {
		bulletRoot.getChildren().remove(bulletImage);
	}

}
