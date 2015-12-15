package gui.weapon;

import element.weaponsManager.Weapon;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class GuiExplosion {

	private final static double INITIAL_RADIUS = 5.0;

	private ScaleTransition transition;
	private FadeTransition fadeTransition;

	private Weapon weapon;

//	private Circle explosion;
	private ImageView explosion;

	private boolean ranning;

	public GuiExplosion(final WeaponGui weaponGui) {

//		explosion = new Circle(INITIAL_RADIUS,Color.YELLOW);
//		explosion.setFill(new ImagePattern(new Image("file:image/expl.gif")));
		explosion = new ImageView("file:image/explosion.gif");
		explosion.setLayoutX(weaponGui.getWeapon().getBlastRadius() / INITIAL_RADIUS);
		explosion.setLayoutY(weaponGui.getWeapon().getBlastRadius() / INITIAL_RADIUS);
		explosion.setFitWidth(INITIAL_RADIUS*8);
		explosion.setFitHeight(INITIAL_RADIUS*8);
		
		weapon = weaponGui.getWeapon();
		transition = new ScaleTransition(Duration.millis(50), explosion);
		transition.setFromX(1.0);
		transition.setFromY(1.0);
		transition.setToX(weaponGui.getWeapon().getBlastRadius() / INITIAL_RADIUS);
		transition.setToY(weaponGui.getWeapon().getBlastRadius() / INITIAL_RADIUS);
		transition.setCycleCount(1);
		transition.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				weaponGui.removeBullet();
				fadeTransition.play();
			}
		});

		fadeTransition = new FadeTransition();
		fadeTransition.setNode(explosion);
		fadeTransition.setDuration(new Duration(1000));
		fadeTransition.setFromValue(1.0);
		fadeTransition.setToValue(0.0);
		fadeTransition.setCycleCount(1);
		fadeTransition.setAutoReverse(true);
		fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				weaponGui.afterAttack();
			}
		});

	}

	public Node getExplosionNode() {
		return explosion;
	}

	public void playExplosionAnimation() {
		if (!ranning) {
			explosion.relocate(weapon.getX(), weapon.getY());
			transition.play();
			ranning = true;
		}
	}

	public boolean started() {
		return ranning;
	}

}
