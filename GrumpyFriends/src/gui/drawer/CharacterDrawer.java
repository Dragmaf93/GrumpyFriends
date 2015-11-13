package gui.drawer;

import character.Character;
import element.weaponsManager.Weapon;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class CharacterDrawer {

	private Group pane;

	private Character character;
	private ImageView imageView;
	private WeaponDrawer weaponDrawer;

	private Group root;
	private Rectangle characterBody;
	private Weapon equipedWeapon;
	private Node currentLauncherWeapon;
	private Node currentBulletWeapon;
	private int currentPlayerDirection;

	private StackPane lifePointsPane;
	private Text lifePointsText;
	private Rectangle lifePointsRectangle;

	private boolean entry;

	public CharacterDrawer(Group pane, Character character) {
		this.pane = pane;
		this.character = character;
		this.weaponDrawer = new WeaponDrawer(character);

		root = new Group();
		characterBody = new Rectangle(character.getX(), character.getY(), character.getWidth(), character.getHeight());
		root.getChildren().add(characterBody);

		lifePointsText = new Text();
		lifePointsText.setFill(Color.BLACK);
		lifePointsText.setFont(Font.font(15));
		lifePointsText.setText(Integer.toString(character.getLifePoints()));

		
		lifePointsRectangle = new Rectangle(50, 30);
		lifePointsRectangle.setFill(character.getTeam().getColorTeam());
		lifePointsRectangle.setArcHeight(10);
		lifePointsRectangle.setArcWidth(10);
		lifePointsRectangle.setStroke(Color.BLACK);
		lifePointsRectangle.setStrokeWidth(2);

		lifePointsPane = new StackPane();
		lifePointsPane.getChildren().add(lifePointsRectangle);
		lifePointsPane.getChildren().add(lifePointsText);
		lifePointsPane.setCache(true);
		lifePointsPane.setCacheHint(CacheHint.SCALE_AND_ROTATE);
		
		root.getChildren().add(lifePointsPane);
		pane.getChildren().add(root);

	}

	public void draw() {

		// System.out.println("PRIMA DEL IF : ABILITA ARMA");
		if (character.getLauncher().isActivated() && !root.getChildren().contains(currentLauncherWeapon)
				&& !weaponDrawer.attackEnded()) {
			equipedWeapon = character.getEquipWeapon();
			// System.out.println("DENTRO DEL IF : ABILITA ARMA" +
			// equipedWeapon);
			currentLauncherWeapon = weaponDrawer.getLauncherWeapon(equipedWeapon);
			root.getChildren().add(currentLauncherWeapon);
			entry = false;

		}
		// System.out.println("FINE DEL IF : ABILITA ARMA");

		// System.out.println("PRIMA DEL IF : CAMBIA ARMA");
		if (character.getLauncher().isActivated() && root.getChildren().contains(currentLauncherWeapon)
				&& !weaponDrawer.attackEnded() && equipedWeapon != null && character.getEquipWeapon() != null
				&& !character.getEquipWeapon().getName().equals(equipedWeapon.getName())) {

			entry = false;
			root.getChildren().remove(currentLauncherWeapon);
			equipedWeapon = character.getEquipWeapon();
			currentLauncherWeapon = weaponDrawer.getLauncherWeapon(equipedWeapon);
			root.getChildren().add(currentLauncherWeapon);
		}
		// System.out.println("FINE DEL IF : CAMBIA ARMA");

		// System.out.println("PRIMA DEL IF : DISABILITA ARMA");
		if (!character.getLauncher().isActivated() && root.getChildren().contains(currentLauncherWeapon)
				&& !weaponDrawer.attackEnded()) {
			root.getChildren().remove(currentLauncherWeapon);
		}
		// System.out.println("FINE DEL IF : DISABILITA ARMA");

		// System.out.println("PRIMA DEL IF : AGGIORNA MIRA");
		if (character.getLauncher().isActivated() && !weaponDrawer.attackEnded()) {
			weaponDrawer.updateLauncherAim();
		}
		// System.out.println("FINE DEL IF : AGGIORNA MIRA");

		// System.out.println("PRIMA DEL IF : INIZIO ATTACCO");
		if (weaponDrawer.bulletLaunched() && !weaponDrawer.attackEnded()) {

			character.getLauncher().disable();
			if (currentBulletWeapon == null) {
				currentBulletWeapon = weaponDrawer.drawAttack();
				pane.getChildren().add(currentBulletWeapon);
			}
			weaponDrawer.updateBullet();
		}
		// System.out.println("FINE DEL IF : INIZIO ATTACCO");

		// System.out.println("PRIMA DEL IF : FINE ATTACCO");
		if (weaponDrawer.attackEnded()) {
			pane.getChildren().remove(currentBulletWeapon);
			root.getChildren().remove(currentLauncherWeapon);
			weaponDrawer.resetDrawer();
			currentBulletWeapon = null;
			equipedWeapon = null;
			currentLauncherWeapon = null;

		}
		// System.out.println("FINE DEL IF : FINE ATTACCO");

		// System.out.println("PRIMA DEL IF : FINE TURNO");
		if (character.finishedTurn() && !weaponDrawer.attackEnded() && !entry) {
			entry = true;
			pane.getChildren().remove(currentBulletWeapon);
			root.getChildren().remove(currentLauncherWeapon);
			weaponDrawer.resetDrawer();
			currentBulletWeapon = null;
			equipedWeapon = null;
			currentLauncherWeapon = null;
		}
		// System.out.println("FINE DEL IF : FINE TURNO");

		// System.out.println("PRIMA DEL IF : CAMBIO DIREZIONE");
		// if (currentPlayerDirection != character.getCurrentDirection()) {
		// currentPlayerDirection = character.getCurrentDirection();
		// }
		// System.out.println("FINE DEL IF : CAMBIO DIREZIONE");

		characterBody.relocate(character.getX(), character.getY());
		
		if(character.sufferedDamage()){
			lifePointsText.setText(Integer.toString(character.getLifePoints()));
		}
		if (character.isSleeping()){
			
			lifePointsPane.relocate(
					character.getX() + characterBody.getWidth() / 2 - lifePointsRectangle.getWidth() / 2,
					character.getY() - 70);
			lifePointsPane.setVisible(true);
		}
		else{
			
			lifePointsPane.setVisible(false);
		}
	}

}
