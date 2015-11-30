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

	// prove
	private GuiExplosion explosion;

	private boolean finishAnimation;

	private ImageView bombImage;
	private ImageView bullet;

	public GuiSimpleBomb(Weapon weapon) {
		super(weapon);
		this.weapon = weapon;

		finishAnimation = false;
		//
		bombImage = new ImageView(new Image("file:image/weapons/SimpleBomb.png"));
		bombImage.setFitHeight(weapon.getHeight() * 2);
		bombImage.setFitWidth(weapon.getWidth() * 2);

		bullet = new ImageView(new Image("file:image/weapons/SimpleBomb.png"));
		bullet.setFitHeight(weapon.getHeight() * 2);
		bullet.setFitWidth(weapon.getWidth() * 2);
		//

	}

	@Override
	public Node getWeaponLauncher() {
		// aggiungere solo la prima volta
		
		if (!launcherRoot.getChildren().contains(bombImage)) {
			launcherRoot.getChildren().add(bombImage);
			launcherRotate = new Rotate();
			launcherRotate.setAxis(Rotate.Z_AXIS);
			bombImage.getTransforms().add(launcherRotate);
		}
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

//		launcherRotate.setPivotX(character.getX()+character.getWidth());
//		launcherRotate.setPivotY(character.getY()+character.getHeight()*0.2);
//		launcherRotate.setAngle(Math.toDegrees(-character.getLauncher().getAngle()));
		bombImage.relocate(character.getX() + character.getWidth() + 5, character.getY() + character.getHeight() * 0.2);
		//
	}
	@Override
	public void resetItem() {
		finishAnimation=false;
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
