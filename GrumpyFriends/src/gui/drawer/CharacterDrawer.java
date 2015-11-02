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
//		l = new Rectangle(character.getWidth(), character.getHeight() * 0.2, Color.WHITE);
//		l.setStroke(Color.RED);
//		rotate = new Rotate();
//		rotate.setAxis(Rotate.Z_AXIS);
//		l.getTransforms().add(rotate);
		pane.getChildren().add(root);
		// pane.getChildren().add(imageView);
	}

	public void draw() {
		System.out.println(character.getLauncher().isActivated());
		System.out.println("PRIMA PRIMO IF");
		if (character.getLauncher().isActivated() && !root.getChildren().contains(currentLauncherWeapon)
				&& !weaponDrawer.attackEnded()) {
			System.out.println("DENTRO PRIMO IF");
			equipedWeapon = character.getEquipWeapon();
			currentLauncherWeapon = weaponDrawer.getLauncherWeapon(equipedWeapon);
			System.out.println("DENTRO PRIMO IF");
			root.getChildren().add(currentLauncherWeapon);
//			System.out.println("ENTRATO");
		}

		System.out.println("PRIMA SECONDO IF");
		if (!character.getLauncher().isActivated() && root.getChildren().contains(currentLauncherWeapon)
				&& !weaponDrawer.attackEnded()) {
			System.out.println("DENTRO SECONDO IF");
			root.getChildren().remove(currentLauncherWeapon);
		}
		System.out.println("PRIMA TERZO IF");
		if (character.getLauncher().isActivated() && !weaponDrawer.attackEnded()) {
			System.out.println("DENTRO TERZO IF");
			weaponDrawer.updateLauncherAim();
//			rotate.setPivotX(0);
//			rotate.setPivotY(l.getHeight() / 2);
//			System.out.println("ANGLE       "+-Math.toDegrees(character.getLauncher().getAngle()));
//			rotate.setAngle(Math.toDegrees(-character.getLauncher().getAngle()));
//			l.relocate(character.getX() + character.getWidth() * 0.5, character.getY() + character.getHeight() * 0.1);
//			l.relocate(character.getLauncher().getX(), character.getLauncher().getY());
		}
		System.out.println("PRIMA QUARTO IF");
		if(weaponDrawer.bulletLaunched() && !weaponDrawer.attackEnded()){
			System.out.println("DENTRO QUARTO IF");
			
			character.getLauncher().disable();
			
			if(currentBulletWeapon==null){
				currentBulletWeapon=weaponDrawer.drawAttack();
				pane.getChildren().add(currentBulletWeapon);
			}
			weaponDrawer.updateBullet();
		}
		
		System.out.println("PRIMA QUINTO IF");
		if(weaponDrawer.attackEnded()){
			System.out.println("DENTRO QUINTO IF");
				pane.getChildren().remove(currentBulletWeapon);
				root.getChildren().remove(currentLauncherWeapon);
			weaponDrawer.resetDrawer();
			currentBulletWeapon=null;
			equipedWeapon=null;
			currentLauncherWeapon=null;
			
		}
		// imageView.relocate(character.getX(), character.getY());
		characterBody.relocate(character.getX(), character.getY());
	}

}
