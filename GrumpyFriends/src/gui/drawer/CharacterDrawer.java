package gui.drawer;

import character.Character;
import character.WhiteStormtrooper;
import element.weaponsManager.Weapon;
import game.AbstractMatchManager;
import game.MatchManager;
import gui.animation.CharacterAnimation;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class CharacterDrawer {

	private final static String PATH_PACKAGE = "gui.animation.";

	private Group pane;

	private Character character;
	private WeaponDrawer weaponDrawer;

	private Group root;

	private Rectangle characterBody;
	private ImageView characterImage;

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

	private MatchManager matchManager;

	private boolean canStart = true;

	private int turn;

	private boolean deathAnimation;

	private boolean deathAnimationEnd;

	private CharacterAnimation characterAnimation;

	private Rotate rotateImage;

	private int lastDirection;

	public CharacterDrawer(Group pane, Character character,
			WeaponDrawer weaponDrawer, MatchManager matchManager) {
		this.pane = pane;
		this.character = character;
		this.matchManager = matchManager;
		// this.weaponDrawer = new WeaponDrawer(character);
		this.weaponDrawer = weaponDrawer;

		root = new Group();
		characterBody = new Rectangle(character.getX(), character.getY(),
				character.getWidth(), character.getHeight());
		characterImage = new ImageView();
		characterAnimation = loadCharacterAnimation(character);
		// new StormtrooperAnimation();
		// root.getChildren().add(characterBody);
		root.getChildren().add(characterImage);

		characterImage.setFitHeight(characterAnimation.getHeight());
		characterImage.setFitWidth(characterAnimation.getWidth());

		rotateImage = new Rotate();
		characterImage.getTransforms().add(rotateImage);
		lastDirection = character.getCurrentDirection();

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
		damageLifePointsPane.getChildren().add(
				new Rectangle(50, 30, Color.TRANSPARENT));
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

	public void reset(){
		equipedWeapon=null;
		currentLauncherWeapon=null;
		currentBulletWeapon=null;
		lifePointsText.setText(Integer.toString(character.getLifePoints()));
		lifePointsRectangle.setStroke(Color.BLACK);
		
		pane.getChildren().remove(root);
		
		root = new Group(characterImage,lifePointsPane,damageLifePointsPane);
		pane.getChildren().add(root);
	}
	
	protected CharacterAnimation loadCharacterAnimation(Character character) {
		String name = PATH_PACKAGE + character.getClass().getSimpleName()
				+ "Animation";
		Class<?> classDefinition;
		try {
			classDefinition = Class.forName(name);
			return (CharacterAnimation) classDefinition.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void draw() {
		if (root != null) {
			if (turn < character.getTeam().getMatchManager().getTurn()) {
				turn = character.getTeam().getMatchManager().getTurn();
				canStart = true;
			}
			if (character == matchManager.getCurrentPlayer()) {
				lifePointsRectangle.setStroke(Color.GOLD);
				if (character.isMoving())
					characterImage.setImage(characterAnimation
							.getCharacterMoveAnimation());

				if (character.getCurrentDirection() != lastDirection) {
					lastDirection = character.getCurrentDirection();
					rotateImage.setAxis(Rotate.Y_AXIS);
					rotateImage.setPivotX(characterImage.getX() + 80);
					if (lastDirection == Character.LEFT)
						rotateImage.setAngle(180);
					else if (lastDirection == Character.RIGHT)
						rotateImage.setAngle(0);
				}
				if (character.isJumping())
					characterImage.setImage(characterAnimation
							.getCharacterJumpAnimation());

				if (character.getLauncher().isActivated()
						&& !root.getChildren().contains(currentLauncherWeapon)
						&& !weaponDrawer.attackEnded()
						&& !character.getLauncher().attacked()) {
					equipedWeapon = character.getEquipWeapon();
					currentLauncherWeapon = weaponDrawer.getLauncherWeapon(
							equipedWeapon, character, characterAnimation);

					root.getChildren().add(currentLauncherWeapon);
					characterImage.setImage(characterAnimation
							.getCharacterBodyWithWeapon(equipedWeapon));
					entry = false;

				}

				if (character.getLauncher().isActivated()
						&& root.getChildren().contains(currentLauncherWeapon)
						&& !weaponDrawer.attackEnded()
						&& equipedWeapon != null
						&& character.getEquipWeapon() != null
						&& !character.getEquipWeapon().getName()
								.equals(equipedWeapon.getName())) {

					entry = false;
					root.getChildren().remove(currentLauncherWeapon);
					equipedWeapon = character.getEquipWeapon();
					currentLauncherWeapon = weaponDrawer.getLauncherWeapon(
							equipedWeapon, character, characterAnimation);

					root.getChildren().add(currentLauncherWeapon);
					characterImage.setImage(characterAnimation
							.getCharacterBodyWithWeapon(equipedWeapon));
				}

				if (!character.getLauncher().isActivated()
						&& root.getChildren().contains(currentLauncherWeapon)
						&& !weaponDrawer.attackEnded()) {
					root.getChildren().remove(currentLauncherWeapon);
				}

				if (character.getLauncher().isActivated()
						&& !weaponDrawer.attackEnded()) {
					weaponDrawer.updateLauncherAim();
				}

				if (weaponDrawer.bulletLaunched()
						&& !weaponDrawer.attackEnded()) {

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

				if (character.finishedTurn() && !weaponDrawer.attackEnded()
						&& !entry) {
					entry = true;
					pane.getChildren().remove(currentBulletWeapon);

					root.getChildren().remove(currentLauncherWeapon);
					weaponDrawer.resetDrawer();
					currentBulletWeapon = null;
					equipedWeapon = null;
					currentLauncherWeapon = null;
				}
			}

			characterBody.relocate(character.getX(), character.getY());
			// root.relocate(character.getX()+characterAnimation.getValueX(),
			// character.getY()+characterAnimation.getValueY());

			lifePointsPane.relocate(character.getX() + characterBody.getWidth()
					/ 2 - lifePointsRectangle.getWidth() / 2,
					character.getY() - 70);
			damageLifePointsPane.relocate(
					character.getX() + characterBody.getWidth() / 2
							- lifePointsRectangle.getWidth() / 2,
					character.getY() - 110);
			characterImage.relocate(
					character.getX() + characterAnimation.getValueX(),
					character.getY() + characterAnimation.getValueY());

			if (lifePointsUpdate) {
				pointCounter += 1;
				int lf = Integer.parseInt(lifePointsText.getText());
				lf -= 1;
				lifePointsText.setText(Integer.toString(lf));
				if (pointCounter >= character.getLastDamagePoints()) {
					finishedLifePointsUpdate = true;
					lifePointsUpdate = false;
					lifePointsText.setText(Integer.toString(character
							.getLifePoints()));
					damagePoint.setText("");
				}
			}
			if (character.isFalling())
				characterImage.setImage(characterAnimation
						.getCharacterFallAnimation());

			if (character.isSleeping()
					&& !character.getLauncher().isActivated()) {
				characterImage.setImage(characterAnimation
						.getCharacterIdleAnimation());
			}

			if (character.isOutWorld()) {
				pane.getChildren().remove(root);
				root = null;
			} else if (deathAnimation) {
				pane.getChildren().remove(root);
				deathAnimation = false;
				deathAnimationEnd = true;
				root = null;
			}

			if (character.finishedTurn())
				lifePointsRectangle.setStroke(Color.BLACK);
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
		return finishedLifePointsUpdate || character.isOutWorld();
	}

	public void startDeathAnimation() {
		deathAnimation = true;
		deathAnimationEnd = false;
	}

	public boolean isDeathAnimationFinished() {
		return deathAnimation;
	}
}
