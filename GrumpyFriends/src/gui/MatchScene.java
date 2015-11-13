package gui;

import character.Character;
import element.weaponsManager.Launcher;
import game.MatchManager;
import gui.hud.MatchPane;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MatchScene extends Scene {

	private MatchPane pane;
	private boolean pressedLaunchKey;
	private float launchPower;

	public MatchScene(Parent parent, MatchManager matchManager, double width, double height) {
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
