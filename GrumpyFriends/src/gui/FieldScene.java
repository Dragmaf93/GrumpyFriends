package gui;

import game.MatchManager;
import javafx.animation.PathTransition;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import utils.Vector;

public class FieldScene extends SubScene {

	private final static int MAX_ZOOM = 10000;
	private final static int MIN_ZOOM = -700;
	private final static int INCR_ZOOM = 50;
	private final static double MAX_T = 100;

	private MatchManager matchManager;
	private PerspectiveCamera camera;
	private FieldPane pane;

	private PathTransition pathTransition;
	private Circle cameraPosition;
	private int zoom;

	public boolean running;
	private double t, vX, vY;

	public FieldScene(Parent parent, MatchManager matchManager, double width, double height) {
		super(parent, width, height);
		this.pane = (FieldPane) parent;
		this.matchManager = matchManager;
		this.camera = new PerspectiveCamera(true);
		zoom = MIN_ZOOM;
		camera.setTranslateZ(zoom);
		camera.setNearClip(0.1);
		camera.setFarClip(MAX_ZOOM);
		camera.setFieldOfView(35);
		setCamera(camera);

		setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				if (!matchManager.isPaused()) {
					if (event.getDeltaY() > 0 && zoom + INCR_ZOOM <= MIN_ZOOM) {
					zoom += INCR_ZOOM;
					camera.setTranslateZ(zoom);
				}
				if (event.getDeltaY() < 0) {
					zoom -= INCR_ZOOM;
					camera.setTranslateZ(zoom);
				}
			}
			}
		});

	}

	public void update() {
		pane.update();
		if (matchManager.isTheCurrentTurnEnded() && matchManager.allCharacterAreSpleeping()) {

			if (!running) {
				running = true;
				t = 1;
				vX = (matchManager.nextPlayer().getX() - matchManager.getCurrentPlayer().getX()) / MAX_T;
				vY = (matchManager.nextPlayer().getY() - matchManager.getCurrentPlayer().getY()) / MAX_T;
			} else {
				t += 1;
				camera.setTranslateX(matchManager.getCurrentPlayer().getX() + t * vX);
				camera.setTranslateY(matchManager.getCurrentPlayer().getY() + t * vY);
				if (t >= MAX_T) {
					matchManager.startNextTurn();
					running = false;
				}
			}
		} else {
			camera.setTranslateX(matchManager.getCurrentPlayer().getX());
			camera.setTranslateY(matchManager.getCurrentPlayer().getY());

		}

	}

}
