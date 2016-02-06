package gui.event;

import game.MatchManager;
import gui.MatchPane;
import character.Character;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyboardPressedEventHandler implements EventHandler<KeyEvent> {
	
	private MatchPane pane;
	private MatchManager matchManager;
	
	public KeyboardPressedEventHandler(MatchPane matchPane,MatchManager matchManager) {
		this.pane = matchPane;
		this.matchManager = matchManager;
	}
	
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
//				launchPower = 5f;
//				pressedLaunchKey = true;
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
			pane.getFieldScene().cameraMovesUP();
			pane.focusPlayer(false);
		}
		if (event.getCode() == KeyCode.DOWN) {
			pane.getFieldScene().cameraMovesDOWN();
			pane.focusPlayer(false);
		}
		if (event.getCode() == KeyCode.LEFT) {
			pane.getFieldScene().cameraMovesLEFT();
			pane.focusPlayer(false);
		}
		if (event.getCode() == KeyCode.RIGHT) {
			pane.getFieldScene().cameraMovesRIGTH();
			pane.focusPlayer(false);
		}
		if (event.getCode() == KeyCode.F)
			pane.focusPlayer(true);
	}		
	

}
