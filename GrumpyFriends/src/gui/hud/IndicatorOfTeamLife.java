package gui.hud;

import org.jbox2d.common.Rot;

import character.Team;

import com.sun.glass.ui.Screen;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import game.MatchManager;
import game.TurnPhaseType;

public class IndicatorOfTeamLife extends AbstractHudElement {

	private final static double DISTANCE_SCREEN_RIGHT = 230;
	private final static double DISTANCE_SCREEN_BOTTOM = 150;

	private final static double MAX_WIDTH_BAR = 200;
	private final static double HEIGHT_BAR = 30;

	private Rectangle barTeamA;
	private Rectangle barTeamB;

	private Text pointsTeamA;
	private Text pointsTeamB;

	private Pane paneTeamA;
	private Pane paneTeamB;

	private Team teamA;
	private Team teamB;

	private Team teamWithMaxLifePoints;

	private int maxLifePointTeam;
	
	private boolean modifyWidthBar;

	public IndicatorOfTeamLife(MatchManager matchManager) {
		super(matchManager);

		teamA = matchManager.getTeamA();
		teamB = matchManager.getTeamB();

		barTeamA = new Rectangle(MAX_WIDTH_BAR, HEIGHT_BAR,
				teamA.getColorTeam());
		barTeamB = new Rectangle(MAX_WIDTH_BAR, HEIGHT_BAR,
				teamB.getColorTeam());
		barTeamA.setStroke(Color.BLACK);
		barTeamB.setStroke(Color.BLACK);
		barTeamA.setStrokeWidth(3);
		barTeamB.setStrokeWidth(3);

		pointsTeamA = new Text();
		pointsTeamB = new Text();

		pointsTeamA.setFont(Font.font(20));
		pointsTeamB.setFont(Font.font(20));

		maxLifePointTeam = teamA.getTeamLifePoint();

		pointsTeamA.setText(Integer.toString(teamA.getTeamLifePoint()));
		pointsTeamB.setText(Integer.toString(teamB.getTeamLifePoint()));

		paneTeamA = new Pane(barTeamA, pointsTeamA);
		paneTeamB = new Pane(barTeamB, pointsTeamB);

		paneTeamA.relocate(0, 50);

		teamWithMaxLifePoints = teamB;

		pointsTeamA.relocate(MAX_WIDTH_BAR
				- pointsTeamA.getLayoutBounds().getWidth() * 1.3, 0);
		pointsTeamB.relocate(MAX_WIDTH_BAR
				- pointsTeamB.getLayoutBounds().getWidth() * 1.3, 0);

		root.getChildren().addAll(paneTeamA, paneTeamB);

		Rotate rotateBarA = new Rotate();
		rotateBarA.setAxis(Rotate.Y_AXIS);
		rotateBarA.setPivotX(barTeamA.getX());
		rotateBarA.setAngle(180);

		Rotate rotateBarB = new Rotate();
		rotateBarB.setAxis(Rotate.Y_AXIS);
		rotateBarB.setPivotX(barTeamB.getX());
		rotateBarB.setAngle(180);

		barTeamA.getTransforms().add(rotateBarA);
		barTeamB.getTransforms().add(rotateBarB);

		barTeamA.relocate(MAX_WIDTH_BAR, 0);
		barTeamB.relocate(MAX_WIDTH_BAR, 0);
		
		

	}

	private double getWidthBar(Team team) {
		double tmp = (MAX_WIDTH_BAR - HEIGHT_BAR) * team.getTeamLifePoint()
				/ team.getMaxLifePoints();

		if (tmp < 25)
			return 25;
		return tmp;
	}

	private void translateAnimation() {
		if (teamA.getTeamLifePoint() < teamB.getTeamLifePoint()
				&& teamWithMaxLifePoints == teamA) {
			TranslateTransition translateTransitionA = new TranslateTransition(
					Duration.seconds(1), paneTeamA);
			translateTransitionA.setToY(50);
			TranslateTransition translateTransitionB = new TranslateTransition(
					Duration.seconds(1), paneTeamB);
			translateTransitionB.setToY(50);
		

			ParallelTransition pt = new ParallelTransition(
					translateTransitionA, translateTransitionB);
//			pt.setOnFinished(new EventHandler<ActionEvent>() {
//				
//				@Override
//				public void handle(ActionEvent arg0) {
//					paneTeamA.relocate(0, 50);
//					paneTeamB.relocate(0, 0);
//				}
//			});
//			
			pt.play();
			
			teamWithMaxLifePoints = teamB;
		
		} else if (teamB.getTeamLifePoint() < teamA.getTeamLifePoint()
				&& teamWithMaxLifePoints == teamB) {
			TranslateTransition translateTransitionA = new TranslateTransition(
					Duration.seconds(1), paneTeamA);
			translateTransitionA.setToY(-50);
			TranslateTransition translateTransitionB = new TranslateTransition(
					Duration.seconds(1), paneTeamB);
			translateTransitionB.setToY(50);

			ParallelTransition pt = new ParallelTransition(
					translateTransitionA, translateTransitionB);
//			pt.setOnFinished(new EventHandler<ActionEvent>() {
//				
//				@Override
//				public void handle(ActionEvent arg0) {
//					paneTeamB.relocate(0, 50);
//					paneTeamA.relocate(0, 0);
//				}
//			});
			pt.play();

			teamWithMaxLifePoints = teamA;
		}

	}

	@Override
	public void draw() {
		Scene scene = root.getScene();
		// root.relocate(scene.getWidth()-DISTANCE_SCREEN_RIGHT,
		// scene.getHeight()-DISTANCE_SCREEN_BOTTOM);
		root.relocate(scene.getWidth() - DISTANCE_SCREEN_RIGHT,
				scene.getHeight() - DISTANCE_SCREEN_BOTTOM);

		if (matchManager.getCurrentTurnPhase() == TurnPhaseType.DAMAGE_PHASE) {
			
			pointsTeamA.setText(Integer.toString(teamA.getTeamLifePoint()));
			pointsTeamB.setText(Integer.toString(teamB.getTeamLifePoint()));

			
			barTeamA.setWidth(getWidthBar(teamA));
			barTeamB.setWidth(getWidthBar(teamB));

			translateAnimation();

			pointsTeamA.relocate(MAX_WIDTH_BAR
					- pointsTeamA.getLayoutBounds().getWidth() * 1.3, 0);
			pointsTeamB.relocate(MAX_WIDTH_BAR
					- pointsTeamB.getLayoutBounds().getWidth() * 1.3, 0);
		}
	}

}
