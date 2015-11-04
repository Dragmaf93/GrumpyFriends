package gui.drawer;

import character.Character;
import element.weaponsManager.Weapon;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class CharacterDrawer {

	private Pane pane;
	private Character character;
	private ImageView imageView;
	private WeaponDrawer weaponDrawer;

	private Group root;
	private Rectangle characterBody;
	private Weapon equipedWeapon;
	private Node currentLauncherWeapon;
	private Node currentBulletWeapon;

	public CharacterDrawer(Pane pane, Character character) {
		this.pane = pane;
		this.character = character;
		this.weaponDrawer = new WeaponDrawer(character, character.getLauncher());

		imageView = new ImageView();
		imageView.setImage(new Image("file:images.png", 0, character.getHeight(), true, true));
		imageView.relocate(character.getX(), character.getY());
		root = new Group();
		characterBody = new Rectangle(character.getX(), character.getY(), character.getWidth(), character.getHeight());
		root.getChildren().add(characterBody);
		// l = new Rectangle(character.getWidth(), character.getHeight() * 0.2,
		// Color.WHITE);
		// l.setStroke(Color.RED);
		// rotate = new Rotate();
		// rotate.setAxis(Rotate.Z_AXIS);
		// l.getTransforms().add(rotate);
		pane.getChildren().add(root);
		// pane.getChildren().add(imageView);
	}

	public void draw() {
		if (character.getLauncher().isActivated() && !root.getChildren().contains(currentLauncherWeapon)
				&& !weaponDrawer.attackEnded()) {
			equipedWeapon = character.getEquipWeapon();
			currentLauncherWeapon = weaponDrawer.getLauncherWeapon(equipedWeapon);
			root.getChildren().add(currentLauncherWeapon);
		}

		if (!character.getLauncher().isActivated() && root.getChildren().contains(currentLauncherWeapon)
				&& !weaponDrawer.attackEnded()) {
			root.getChildren().remove(currentLauncherWeapon);
		}

		if (character.getLauncher().isActivated() && !weaponDrawer.attackEnded()) {
			weaponDrawer.updateLauncherAim();
		}

		if (weaponDrawer.bulletLaunched() && !weaponDrawer.attackEnded()) {

			character.getLauncher().disable();
			if (currentBulletWeapon == null) {
				currentBulletWeapon = weaponDrawer.drawAttack();
				pane.getChildren().add(currentBulletWeapon);
			}
			weaponDrawer.updateBullet();
		}

		if (weaponDrawer.attackEnded()) {
			pane.getChildren().remove(currentBulletWeapon);
			root.getChildren().remove(currentLauncherWeapon);
			weaponDrawer.resetDrawer();
			currentBulletWeapon = null;
			equipedWeapon = null;
			currentLauncherWeapon = null;

		}
		// imageView.relocate(character.getX(), character.getY());
		characterBody.relocate(character.getX(), character.getY());
	}

}
