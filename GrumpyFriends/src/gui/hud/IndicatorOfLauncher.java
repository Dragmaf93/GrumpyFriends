package gui.hud;

import character.Character;
import element.weaponsManager.Launcher;
import game.MatchManager;
import gui.MatchPane;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class IndicatorOfLauncher extends AbstractHudElement {

	private final static Color BACKGROUND_COLOR = new Color(30d / 255d,
			127d / 255d, 169d / 255d, 0.8d);
	private final static Color BACKGROUND_COLOR_DISABLE = new Color(19d / 255d,
			73d / 255d, 95d / 255d, 0.8d);

	private final static Color BACKGROUND_BAR_COLOR = new Color(30d / 255d,
			127d / 255d, 169d / 255d, 1d);
	private final static Color BACKGROUND_BAR_COLOR_DISABLE = new Color(
			19d / 255d, 73d / 255d, 95d / 255d, 1d);

	private final static double DISTANCE_SCREEN_LEFT = 40;
	private final static double DISTANCE_SCREEN_BOT = 190;

	private float launcherPower;
	private boolean keyPressed;

	private final static double BAR_HEIGHT = 136;
	private final static double BAR_WIDTH = 50;
	private final static double RADIUS_LAUNCHER = 68;
	// private StackPane pane;

	private ImageView coloredPowerBar;
	private ImageView powerBar;
	private Rectangle bar;
	private Rectangle shadowBar;

	private ImageView launcherIndicator;
	private ImageView arrow;
	private Arc background;

	private Rotate rotationArrow;

	private DropShadow shadow;
	private boolean readyAttack;

	public IndicatorOfLauncher(MatchPane matchPane, MatchManager matchManager) {
		super(matchPane, matchManager);

		bar = new Rectangle(BAR_WIDTH, BAR_HEIGHT, BACKGROUND_COLOR_DISABLE);
		shadowBar = new Rectangle(BAR_WIDTH, BAR_HEIGHT,
				BACKGROUND_COLOR_DISABLE);
		// shadowBar.setStroke(Color.BLACK);

		background = new Arc(0, 0, RADIUS_LAUNCHER, RADIUS_LAUNCHER, -90, 180);
		background.setType(ArcType.ROUND);

		background.setFill(BACKGROUND_COLOR_DISABLE);
		background.setStrokeWidth(2);
		background.setStroke(Color.BLACK);

		coloredPowerBar = new ImageView(new Image(
				"file:image/indicatorePotenza.png"));
		powerBar = new ImageView(new Image("file:image/barraPotenza.png"));
		arrow = new ImageView(new Image("file:image/freccia.png"));
		launcherIndicator = new ImageView(new Image(
				"file:image/indicatoreLaucher.png"));

		coloredPowerBar.setFitHeight(BAR_HEIGHT);
		coloredPowerBar.setFitWidth(BAR_WIDTH);

		powerBar.setFitHeight(BAR_HEIGHT);
		powerBar.setFitWidth(BAR_WIDTH);

		arrow.setFitHeight(15);
		arrow.setFitWidth(65);

		launcherIndicator.setFitHeight(BAR_HEIGHT);
		launcherIndicator.setFitWidth(RADIUS_LAUNCHER);

		launcherIndicator.relocate(BAR_WIDTH / 2 + 1,
				-launcherIndicator.getFitHeight() / 2 + 1);
		background.relocate(BAR_WIDTH / 2,
				-launcherIndicator.getFitHeight() / 2);

		coloredPowerBar.relocate(-coloredPowerBar.getFitWidth() + 1,
				-coloredPowerBar.getFitHeight() / 2);
		powerBar.relocate(-coloredPowerBar.getFitWidth() + 1,
				-coloredPowerBar.getFitHeight() / 2);
		bar.relocate(-coloredPowerBar.getFitWidth() + 1,
				-coloredPowerBar.getFitHeight() / 2);
		shadowBar.relocate(-coloredPowerBar.getFitWidth() + 1,
				-coloredPowerBar.getFitHeight() / 2);

		arrow.relocate(-arrow.getFitHeight() / 2 + BAR_WIDTH / 2,
				-arrow.getFitHeight() / 2);
		root.getChildren().addAll(shadowBar, background, coloredPowerBar, bar,
				powerBar, launcherIndicator, arrow);

		coloredPowerBar.setVisible(false);

		rotationArrow = new Rotate();

		arrow.getTransforms().add(rotationArrow);
		rotationArrow.setAxis(Rotate.Z_AXIS);
		rotationArrow.setPivotY(arrow.getY() + arrow.getFitHeight() / 2);
		rotationArrow.setPivotX(arrow.getX() + arrow.getFitHeight() / 2);

		shadow = new DropShadow(3, Color.BLACK);
		shadow.setOffsetX(2);
		shadow.setOffsetY(-2);
		shadowBar.setEffect(shadow);
		background.setEffect(shadow);

		root.relocate(DISTANCE_SCREEN_LEFT, screenHeight - DISTANCE_SCREEN_BOT);

	}

	public void setLauncherPower(float power) {
		this.launcherPower = power;
	}

	@Override
	public void draw() {
		rotationArrow.setPivotY(arrow.getY() + arrow.getFitHeight() / 2);
		rotationArrow.setPivotX(arrow.getX() + arrow.getFitHeight() / 2);

		if (matchManager.getCurrentPlayer().getLauncher().isActivated()) {
			if (!root.getChildren().contains(arrow)) {
				root.getChildren().add(arrow);
				background.setFill(BACKGROUND_COLOR);
				bar.setFill(BACKGROUND_COLOR);
			}
			if (matchManager.getCurrentPlayer().getCurrentDirection() == Character.RIGHT) {
				rotationArrow.setAngle(-Math.toDegrees(matchManager
						.getCurrentPlayer().getLauncher().getAngle()));
			} else {
				rotationArrow
						.setAngle(Math
								.toDegrees(matchManager.getCurrentPlayer()
										.getLauncher().getAngle() - 45 - 90));
			}
		} else {
			background.setFill(BACKGROUND_COLOR_DISABLE);
			bar.setFill(BACKGROUND_COLOR_DISABLE);

			root.getChildren().remove(arrow);
		}

		if (keyPressed) {
			launcherPower += 1f;
			readyAttack=true;
			bar.setFill(BACKGROUND_BAR_COLOR);
			coloredPowerBar.setVisible(true);
			bar.setHeight(BAR_HEIGHT - BAR_HEIGHT * launcherPower
					/ Launcher.MAX_LAUNCH_POWER);

			if (launcherPower >= Launcher.MAX_LAUNCH_POWER) {
				keyPressed = false;
				coloredPowerBar.setVisible(false);
				bar.setHeight(BAR_HEIGHT);
			}
		}else if(readyAttack){
			matchManager.getCurrentPlayer().attack(launcherPower);
			readyAttack=false;
			bar.setHeight(BAR_HEIGHT);
			coloredPowerBar.setVisible(false);
			launcherPower=5f;
		}
	}

	public void launchKeyPressed() {
		keyPressed = !keyPressed;
	}

}
