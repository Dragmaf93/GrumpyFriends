package gui;

import character.Character;
import element.weaponsManager.Launcher;
import menu.MenuManager;
import game.AbstractMatchManager;
import gui.event.KeyboardPressedEventHandler;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

public class MainScene extends Scene {

	private boolean pressedLaunchKey;
	
	private float launchPower;

	
	public MainScene(Parent parent, double width, double height) {
		super(parent, width, height);

		new AnimationTimer() {

			@Override
			public void handle(long now) {
				MenuManager.getInstance().update();
//				if (pressedLaunchKey) {
//					launchPower += 0.5f;
////					pane.setLauncherPower(launchPower);
//					if (launchPower >= Launcher.MAX_LAUNCH_POWER) {
//						pressedLaunchKey = false;
////						pane.launchKeyPressed();
////						matchManager.getCurrentPlayer().attack(launchPower);
//					}
//				}
//				pane.update();

			}
		}.start();
	}
	
	public void setEventHandler(Pane pane){
		setOnKeyPressed(pane.getOnKeyPressed());
		setOnKeyReleased(pane.getOnKeyReleased());
	}
	
}
