package gui.weapon;

import character.Character;
import element.weaponsManager.Weapon;
import element.weaponsManager.weapons.SimpleBomb;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;

public class GuiSimpleBomb extends AbstractGuiWeapon {

	private Rotate launcherRotate;
	private Rotate launcherRotateY;
	// prove
	private GuiExplosion explosion;

	private boolean finishAnimation;

	private ImageView bombLauncherImage;
	private ImageView bullet;

	public GuiSimpleBomb(Weapon weapon) {
		super(weapon);
		this.weapon = weapon;

		finishAnimation = false;

		bombLauncherImage = new ImageView();

		bullet = new ImageView(new Image("file:image/weapons/SimpleBomb.png"));
		bullet.setFitHeight(weapon.getHeight() * 2);
		bullet.setFitWidth(weapon.getWidth() * 2);
		//

		launcherRotate = new Rotate();
		launcherRotateY = new Rotate();
		launcherRotateY.setAxis(Rotate.Y_AXIS);
		launcherRotate.setAxis(Rotate.Z_AXIS);

		bombLauncherImage.getTransforms().add(launcherRotate);
		bombLauncherImage.getTransforms().add(launcherRotateY);

	}

	@Override
	public Node getWeaponLauncher() {
		// aggiungere solo la prima volta

		if (!launcherRoot.getChildren().contains(bombLauncherImage)) {
			launcherRoot.getChildren().add(bombLauncherImage);
		}
		bombLauncherImage.setImage(characterAnimation
				.getCharacterWeaponLauncher(weapon));
		bombLauncherImage.setFitWidth(characterAnimation.getWidth());
		bombLauncherImage.setFitHeight(characterAnimation.getHeight());
		return launcherRoot;
	}

	@Override
	public Node getWeaponBullet() {

		if (!bulletRoot.getChildren().contains(bullet)) {
			explosion = new GuiExplosion(this);
			bulletRoot.getChildren().add(bullet);
			bulletRoot.getChildren().add(explosion.getExplosionNode());
		}
		return bulletRoot;
	}

	@Override
	public void afterAttack() {
		if (finishAnimation == false) {
			finishAnimation = true;
			weapon.afterAttack();
		}
	}

	@Override
	public void updateLauncher() {
		super.updateLauncher();

		if (currentCharacter.getCurrentDirection() == Character.RIGHT) {

			launcherRotateY.setPivotX(bombLauncherImage.getX() + 80);
			launcherRotateY.setAngle(0);

			launcherRotate.setAxis(Rotate.Z_AXIS);
			launcherRotate.setPivotX(bombLauncherImage.getX() + 75);
			launcherRotate.setPivotY(bombLauncherImage.getY() + 85);
			launcherRotate.setAngle(Math.toDegrees(-currentCharacter
					.getLauncher().getAngle()));
		} else if (currentCharacter.getCurrentDirection() == Character.LEFT) {
			launcherRotateY.setPivotX(bombLauncherImage.getX() + 80);
			launcherRotateY.setAngle(180);
			launcherRotate.setAxis(Rotate.Z_AXIS);
			launcherRotate.setPivotX(bombLauncherImage.getX() + 85);
			launcherRotate.setPivotY(bombLauncherImage.getY() + 85);
			launcherRotate.setAngle(Math.toDegrees(-currentCharacter
					.getLauncher().getAngle()) - 180);
		}
		bombLauncherImage.relocate(
				currentCharacter.getX() + characterAnimation.getValueX(),
				currentCharacter.getY() + characterAnimation.getValueY()); //
	}

	@Override
	public void resetItem() {
		finishAnimation = false;
	}

	@Override
	public void updateBullet() {

		bullet.relocate(weapon.getX(), weapon.getY());
		weapon.update();

		if (((SimpleBomb) weapon).isExploded()) {
			explosion.playExplosionAnimation();
		}
	}

	@Override
	public boolean finishAnimation() {
		return finishAnimation;
	}

	@Override
	public Weapon getWeapon() {
		return weapon;
	}

	@Override
	public void removeBullet() {
		bulletRoot.getChildren().remove(bullet);
	}

}
