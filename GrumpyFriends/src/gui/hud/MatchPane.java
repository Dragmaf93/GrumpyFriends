package gui.hud;

import game.MatchManager;
import gui.FieldPane;
import gui.FieldScene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

public class MatchPane extends Pane {

	private IndicatorOfTime timerInd;
	private IndicatorOfLauncher launcherInd;
	private IndicatorOfEquippedWeapon equippedWeaponInd;
	private IndicatorOfPlayerLife playerLifeInd;
	private Inventory inventory;

	private MatchManager matchManager;

	private FieldPane fieldPane;
	private FieldScene fieldScene;

	boolean pause;

	public MatchPane(MatchManager matchManager) {
		this.matchManager = matchManager;

		this.fieldPane = new FieldPane(matchManager);
		this.fieldScene = new FieldScene(fieldPane, matchManager, Screen.getPrimary().getBounds().getWidth(),
				Screen.getPrimary().getBounds().getHeight());
		this.timerInd = new IndicatorOfTime(matchManager);
		this.inventory = new Inventory(matchManager);

		this.getChildren().add(fieldScene);
		this.getChildren().add(timerInd.getNode());
		pause = false;

	}

	public void pause() {
		matchManager.pauseMatch();
		pause = true;

	}

	public void restartFromPause() {
		matchManager.restartPausedMatch();
		pause = false;
	}

	public boolean isPaused() {
		return pause;
	}

	public boolean inventoryIsHide() {
		return !this.getChildren().contains(inventory.getNode());
	}

	public void showInventory() {
		this.getChildren().add(inventory.getNode());
	}

	public void hideInventory() {
		this.getChildren().remove(inventory.getNode());

	}

	public void update() {
		if (!pause) {
			fieldScene.update();
			inventory.draw();
			matchManager.update();
			timerInd.draw();
		}
	}
}
