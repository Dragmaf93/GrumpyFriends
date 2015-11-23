package gui.drawer;

import java.awt.PointerInfo;

import character.Character;
import element.weaponsManager.Weapon;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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

	private StackPane damageLifePointsPane;

	private StackPane lifePointsPane;
	private Text lifePointsText;
	private Text damagePoint;
	private Rectangle lifePointsRectangle;

	private boolean entry;
	private boolean lifePointsUpdate;
	private boolean finishedLifePointsUpdate;
	private int pointCounter;

	private boolean canStart = true;

	private int turn;

	private boolean deathAnimation;

	private boolean deathAnimationEnd;

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

		damagePoint = new Text();
		damagePoint.setFill(Color.BLACK);
		damagePoint.setFont(Font.font(15));

		lifePointsRectangle = new Rectangle(50, 30);
		lifePointsRectangle.setFill(character.getTeam().getColorTeam());
		lifePointsRectangle.setArcHeight(10);
		lifePointsRectangle.setArcWidth(10);
		lifePointsRectangle.setStroke(Color.BLACK);
		lifePointsRectangle.setStrokeWidth(2);

		lifePointsPane = new StackPane();
		lifePointsPane.getChildren().add(lifePointsRectangle);
		lifePointsPane.getChildren().add(lifePointsText);

		damageLifePointsPane = new StackPane();
		damageLifePointsPane.getChildren().add(new Rectangle(50, 30, Color.TRANSPARENT));
		damageLifePointsPane.getChildren().add(damagePoint);
		damageLifePointsPane.setCache(true);
		damageLifePointsPane.setCacheHint(CacheHint.SCALE_AND_ROTATE);

		lifePointsPane.setCache(true);
		lifePointsPane.setCacheHint(CacheHint.SCALE_AND_ROTATE);

		root.getChildren().add(lifePointsPane);
		root.getChildren().add(damageLifePointsPane);
		pane.getChildren().add(root);
		turn = character.getTeam().getMatchManager().getTurn();

	}

	public void draw() {
		if (root != null) {
			// System.out.println("CIAO");
			if (turn < character.getTeam().getMatchManager().getTurn()) {
				turn = character.getTeam().getMatchManager().getTurn();
				canStart = true;
			}
			// System.out.println("PRIMA DEL IF : ABILITA ARMA");
			if (character.getLauncher().isActivated() && !root.getChildren().contains(currentLauncherWeapon)
					&& !weaponDrawer.attackEnded() && !character.getLauncher().attacked()) {
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

			if (lifePointsUpdate) {
				pointCounter += 1;
				int lf = Integer.parseInt(lifePointsText.getText());
				lf -= 1;
				lifePointsText.setText(Integer.toString(lf));
				if (pointCounter >= character.getLastDamagePoints()) {
					finishedLifePointsUpdate = true;
					lifePointsUpdate = false;
					lifePointsText.setText(Integer.toString(character.getLifePoints()));
					damagePoint.setText("");
				}
			}

			if (character.isSleeping()) {

				lifePointsPane.relocate(
						character.getX() + characterBody.getWidth() / 2 - lifePointsRectangle.getWidth() / 2,
						character.getY() - 70);
				damageLifePointsPane.relocate(
						character.getX() + characterBody.getWidth() / 2 - lifePointsRectangle.getWidth() / 2,
						character.getY() - 110);
				lifePointsPane.setVisible(true);

			} else {

				lifePointsPane.setVisible(false);
			}

			if (deathAnimation) {
				pane.getChildren().remove(root);
				deathAnimation=false;
				deathAnimationEnd=true;
				root = null;
			}
		}
		// System.out.println("CIAOOOOOOOOOOOOoooo");
	}

	public void startLifePointsUpdate() {
		lifePointsUpdate = true;
		finishedLifePointsUpdate = false;
		pointCounter = 0;
		canStart = false;
		damagePoint.setText(Integer.toString(-character.getLastDamagePoints()));

	}

	public boolean canStartUpdate() {
		return canStart;
	}

	public boolean finishedLifePointUpdate() {
		return finishedLifePointsUpdate;
	}

	public void startDeathAnimation() {
		deathAnimation = true;
		deathAnimationEnd = false;
	}

	public boolean isDeathAnimationFinished() {
		return deathAnimation;
	}

}
