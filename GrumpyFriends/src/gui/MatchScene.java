package gui;

import character.Character;
import game.MatchManager;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MatchScene extends Scene {

	private MatchManager matchManager;
		
	public MatchScene(Parent parent,MatchManager matchManager, double width, double height) {
		super(parent,width,height);
		this.matchManager=matchManager;
	
		setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.D) {
					matchManager.getCurrentPlayer().move(Character.RIGHT);
				}
				if (event.getCode() == KeyCode.A) {
					matchManager.getCurrentPlayer().move(Character.LEFT);
				}
				if (event.getCode() == KeyCode.W) {
					matchManager.getCurrentPlayer().jump();
				}
			}
		});

		setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.D) {
					matchManager.getCurrentPlayer().stopToMove();
				}
				if (event.getCode() == KeyCode.A) {
					matchManager.getCurrentPlayer().stopToMove();
				}
			}
		});
	}

}
