package gui.weapon;

import element.weaponsManager.Weapon;
import element.weaponsManager.weapons.SimpleMissile;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

public class GuiSimpleMissile extends AbstractGuiWeapon {

	private final static double BAZOOKA_WIDTH = 110;
	private final static double BAZOOKA_HEIGHT = 50;

	private Rotate launcherRotate;
	private Rotate bulletRotate;

	private ImageView launcherImage;
	private ImageView bulletImage;

	private GuiExplosion explosion;

	private boolean finishAnimation;


	public GuiSimpleMissile(Weapon weapon) {
		super(weapon);
		this.weapon = weapon;

		finishAnimation = false;

		bulletImage = new ImageView(new Image("file:image/weapons/bazookaBullet.png"));
		bulletImage.setFitHeight(weapon.getHeight());
		bulletImage.setFitWidth(weapon.getWidth());

		launcherImage = new ImageView(new Image("file:image/weapons/bazooka1.png"));
		
		launcherImage.setFitHeight(BAZOOKA_HEIGHT);
		launcherImage.setFitWidth(BAZOOKA_WIDTH);
		launcherRotate = new Rotate();
		launcherRotate.setAxis(Rotate.Z_AXIS);
		launcherImage.getTransforms().add(launcherRotate);
	}

	@Override
	public Node getWeaponLauncher() {
		if(!launcherRoot.getChildren().contains(launcherImage))
			launcherRoot.getChildren().add(launcherImage);
		return launcherRoot;
	}

	@Override
	public Node getWeaponBullet() {

		if (!bulletRoot.getChildren().contains(bulletImage)) {
			explosion = new GuiExplosion(this);

			bulletRotate = new Rotate();
			bulletRotate.setAxis(Rotate.Z_AXIS);

			bulletImage.getTransforms().add(bulletRotate);

			bulletRoot.getChildren().add(bulletImage);
//			bulletRoot.getChildren().add(explosion.getExplosionNode());
		}
		return bulletRoot;
	}

	@Override
	public void updateLauncher() {
		super.updateLauncher();
		
//		launcherRotate.setAxis(Rotate.Z_AXIS);
//		launcherRotate.setPivotX(launcherImage.getX() + BAZOOKA_WIDTH * 0.3);
//		launcherRotate.setPivotY(launcherImage.getY() + BAZOOKA_HEIGHT * 0.5);
//		launcherRotate.setAngle(Math.toDegrees(-character.getLauncher().getAngle()));
		launcherImage.relocate(character.getX() - BAZOOKA_WIDTH * 0.2, character.getY());
	}

	@Override
	public void resetItem() {
		finishAnimation = false;

	}
	//TODO riaggiungere le esplosioni

	@Override
	public void updateBullet() {
		weapon.update();
		if (!((SimpleMissile) weapon).isExplosed()) {
			bulletRotate.setPivotX(launcherImage.getX());
			bulletRotate.setPivotY(launcherImage.getY());
			bulletRotate.setAngle(Math.toDegrees(-((SimpleMissile) weapon).getAngle()));

			bulletImage.relocate(weapon.getX(), weapon.getY());

		} else {
//			explosion.playExplosionAnimation();
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
