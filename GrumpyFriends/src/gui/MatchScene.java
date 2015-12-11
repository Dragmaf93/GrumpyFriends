package gui;

import character.Character;
import element.weaponsManager.Launcher;
import game.MatchManager;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;

public class MatchScene extends Scene {

	private final static double VALUE_MOVE_CAMERA = 20.0;
	
	private MatchPane pane;
	private boolean pressedLaunchKey;
	private float launchPower;

	
	public MatchScene(Parent parent, final MatchManager matchManager, double width, double height) {
		super(parent, width, height);
		this.pane = (MatchPane) parent;

		setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.D) {
					if (!pane.isPaused() && !matchManager.isTheCurrentTurnEnded()
							&& pane.inventoryIsHide())
						matchManager.getCurrentPlayer().move(Character.RIGHT);
				}
				if (event.getCode() == KeyCode.A) {
					if (!pane.isPaused() && !matchManager.isTheCurrentTurnEnded()
							&& pane.inventoryIsHide())
						matchManager.getCurrentPlayer().move(Character.LEFT);
				}
				if (event.getCode() == KeyCode.SPACE) {
					if (!pane.isPaused() && !matchManager.isTheCurrentTurnEnded()
							&& pane.inventoryIsHide())
						matchManager.getCurrentPlayer().jump();
				}
				if (event.getCode() == KeyCode.ALT) {
					if (!pane.isPaused() && !matchManager.isTheCurrentTurnEnded()
							&& pane.inventoryIsHide()) {
						launchPower = 5f;
						pressedLaunchKey = true;
						pane.launchKeyPressed();
					}
				}
				if (event.getCode() == KeyCode.P) {
					if (!pane.isPaused())
						pane.pause();
					else
						pane.restartFromPause();
				}
				if (event.getCode() == KeyCode.I) {
					if (!pane.isPaused()  && !matchManager.isTheCurrentTurnEnded()) {
						if (pane.inventoryIsHide())
							pane.showInventory();
						else
							pane.hideInventory();
					}
				}
				if (event.getCode() == KeyCode.W) {
					if (!pane.isPaused() && !matchManager.isTheCurrentTurnEnded()
							&& pane.inventoryIsHide())
						matchManager.getCurrentPlayer().changeAim(Character.INCREASE);
				}
				if (event.getCode() == KeyCode.S) {
					if (!pane.isPaused() && !matchManager.isTheCurrentTurnEnded()
							&& pane.inventoryIsHide())
						matchManager.getCurrentPlayer().changeAim(Character.DECREASE);
				}
				if (event.getCode() == KeyCode.UP) {
					pane.getCameraPosition().set(pane.getCameraPosition().x, pane.getCameraPosition().y - VALUE_MOVE_CAMERA);
					pane.focusPlayer(false);
				}
				if (event.getCode() == KeyCode.DOWN) {
					pane.getCameraPosition().set(pane.getCameraPosition().x, pane.getCameraPosition().y + VALUE_MOVE_CAMERA);
					pane.focusPlayer(false);
				}
				if (event.getCode() == KeyCode.LEFT) {
					pane.getCameraPosition().set(pane.getCameraPosition().x - VALUE_MOVE_CAMERA, pane.getCameraPosition().y);
					pane.focusPlayer(false);
				}
				if (event.getCode() == KeyCode.RIGHT) {
					pane.getCameraPosition().set(pane.getCameraPosition().x + VALUE_MOVE_CAMERA, pane.getCameraPosition().y);
					pane.focusPlayer(false);
				}
				if (event.getCode() == KeyCode.F)
					pane.focusPlayer(true);
			}
		});

		setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (!pane.isPaused() && !matchManager.isTheCurrentTurnEnded()
						&& pane.inventoryIsHide()) {
					if (event.getCode() == KeyCode.D) {
						matchManager.getCurrentPlayer().stopToMove();
					}
					if (event.getCode() == KeyCode.ALT) {
						if (pressedLaunchKey) {
							pressedLaunchKey = false;
							pane.launchKeyPressed();
							matchManager.getCurrentPlayer().attack(launchPower);
						}
					}
					if (event.getCode() == KeyCode.A) {
						matchManager.getCurrentPlayer().stopToMove();
					}
					if (event.getCode() == KeyCode.W) {
						matchManager.getCurrentPlayer().changeAim(Character.STOP);
					}
					if (event.getCode() == KeyCode.S) {
						matchManager.getCurrentPlayer().changeAim(Character.STOP);
					}
				}
			}
		});

		setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				if (!matchManager.isPaused()) {
					if (event.getDeltaY() > 0) {
						pane.setZoomOutCamera(true);
						pane.getCamera().setTranslateZ(pane.getZoomCamera());
					}
					if (event.getDeltaY() < 0) {
						pane.setZoomOutCamera(false);
						pane.getCamera().setTranslateZ(pane.getZoomCamera());
					}
				}
			}
		});
		
		new AnimationTimer() {

			@Override
			public void handle(long now) {
				if (pressedLaunchKey) {
					launchPower += 0.5f;
					pane.setLauncherPower(launchPower);
					if (launchPower >= Launcher.MAX_LAUNCH_POWER) {
						pressedLaunchKey = false;
						pane.launchKeyPressed();
						matchManager.getCurrentPlayer().attack(launchPower);
					}
				}
				pane.update();

			}
		}.start();
	}
}
