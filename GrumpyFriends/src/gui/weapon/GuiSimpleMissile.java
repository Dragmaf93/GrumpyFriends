package gui.weapon;


import character.Character;
import element.weaponsManager.Weapon;
import element.weaponsManager.weapons.SimpleMissile;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

public class GuiSimpleMissile extends  AbstractGuiWeapon {

	private final static double BAZOOKA_WIDTH = 110;
	private final static double BAZOOKA_HEIGHT = 50;

//	private Group launcherRoot;
//	private Group bulletRoot;
//
//	private Character character;
//	private SimpleMissile weapon;

	private Rotate launcherRotate;
	private Rotate bulletRotate;

	private ImageView launcherImage;
	private ImageView bulletImage;

	private GuiExplosion explosion;
	
	private boolean finishAnimation;
	
//	private Rotate bazookaRotate3;

	public GuiSimpleMissile(Weapon weapon, Character character) {
		super(weapon, character);
		this.weapon =  weapon;
		this.character = character;

		finishAnimation = false;

		bulletImage = new ImageView(new Image("file:image/weapons/bazookaBullet.png"));
		bulletImage.setFitHeight(weapon.getHeight());
		bulletImage.setFitWidth(weapon.getWidth());
	}

	@Override
	public Node getWeaponLauncher() {
		if (launcherImage==null) {
			launcherImage = new ImageView(new Image("file:image/weapons/bazooka1.png"));
			
			launcherImage.setFitHeight(BAZOOKA_HEIGHT);
			launcherImage.setFitWidth(BAZOOKA_WIDTH);
			
			launcherRoot.getChildren().add(launcherImage);
			launcherRotate = new Rotate();
//			bazookaRotate3 = new Rotate();
			launcherRotate.setAxis(Rotate.Z_AXIS);

			launcherImage.getTransforms().add(launcherRotate);
//			bazookaImage.getTransforms().add(bazookaRotate3);
		}
		return launcherRoot;
	}

	@Override
	public Node getWeaponBullet() {
		explosion = new GuiExplosion(this);

		bulletRotate = new Rotate();
		bulletRotate.setAxis(Rotate.Z_AXIS);

		bulletImage.getTransforms().add(bulletRotate);

		bulletRoot.getChildren().add(bulletImage);
		bulletRoot.getChildren().add(explosion.getExplosionNode());

		return bulletRoot;
	}

	@Override
	public void updateLauncher() {
		// if(bazookaRotate.getAngle()<-92){
		//// bazookaRotate2.setAxis(Rotate.Y_AXIS);
		//// bazookaRotate2.setPivotX(bazookaImage.getX()+BAZOOKA_WIDTH*0.5);
		// bazookaRotate3.setAxis(Rotate.X_AXIS);
		// bazookaRotate3.setPivotY(bazookaImage.getY()+BAZOOKA_HEIGHT*0.5);
		//// bazookaRotate2.setAngle(180);
		// bazookaRotate3.setAngle(180);
		// bazookaRotate.setAxis(Rotate.Z_AXIS);
		//
		// bazookaRotate.setPivotX(bazookaImage.getX()+BAZOOKA_WIDTH*0.3);
		// bazookaRotate.setPivotY(bazookaImage.getY()+BAZOOKA_HEIGHT*0.5);
		//
		// bazookaRotate.setAngle(Math.toDegrees(-character.getLauncher().getAngle()));
		// bazookaImage.relocate(character.getX()-BAZOOKA_WIDTH*0.2,
		// character.getY());
		// return;
		// }

		super.updateLauncher();
		launcherRotate.setAxis(Rotate.Z_AXIS);
		launcherRotate.setPivotX(launcherImage.getX() + BAZOOKA_WIDTH * 0.3);
		launcherRotate.setPivotY(launcherImage.getY() + BAZOOKA_HEIGHT * 0.5);
		launcherRotate.setAngle(Math.toDegrees(-character.getLauncher().getAngle()));
		launcherImage.relocate(character.getX() - BAZOOKA_WIDTH * 0.2, character.getY());
//		viewfinder.relocate(character.getX()+character.getLauncher().getX() + 150, character.getY()+character.getLauncher().getY()-bulletImage.getFitHeight()-25);
	}

	@Override
	public void updateBullet() {
		weapon.update();

		if (!((SimpleMissile) weapon).isExplosed()) {
			bulletRotate.setPivotX(launcherImage.getX());
			bulletRotate.setPivotY(launcherImage.getY());
			bulletRotate.setAngle(Math.toDegrees(-((SimpleMissile) weapon).getAngle()));

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
