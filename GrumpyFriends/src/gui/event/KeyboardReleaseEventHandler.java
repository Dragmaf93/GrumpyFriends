package gui.event;

import character.Character;
import game.AbstractMatchManager;
import game.MatchManager;
import gui.MatchPane;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class KeyboardReleaseEventHandler implements EventHandler<KeyEvent> {

	private MatchPane pane;
	private MatchManager matchManager;
	
	public KeyboardReleaseEventHandler(MatchPane matchPane,MatchManager matchManager) {
		this.pane = matchPane;
		this.matchManager = matchManager;
	}
	
	@Override
	public void handle(KeyEvent event) {
		if (!pane.isPaused() && !matchManager.isTheCurrentTurnEnded()
				&& pane.inventoryIsHide()) {
			if (event.getCode() == KeyCode.D) {
				matchManager.stopToMoveCurrentPlayer();
			}
			if (event.getCode() == KeyCode.ALT) {
					pane.launchKeyPressed();
			}
			if (event.getCode() == KeyCode.A) {
				matchManager.stopToMoveCurrentPlayer();
			}
			if (event.getCode() == KeyCode.W) {
				matchManager.changeAimCurrentPlayer(Character.STOP);
			}
			if (event.getCode() == KeyCode.S) {
				matchManager.changeAimCurrentPlayer(Character.STOP);
			}
		}
	}

}
