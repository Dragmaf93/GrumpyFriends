package gui.hud;

import game.MatchManager;
import gui.FieldPane;
import gui.FieldScene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class MatchPane extends Pane {

	private final static Color BACKGROUND_COLOR = new Color(30d / 255d, 127d / 255d, 169d / 255d, 0.9d);

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
		this.equippedWeaponInd = new IndicatorOfEquippedWeapon(matchManager);
		this.launcherInd = new IndicatorOfLauncher(matchManager);
		
		this.getChildren().add(fieldScene);
		this.getChildren().add(timerInd.getNode());
		this.getChildren().add(equippedWeaponInd.getNode());
		this.getChildren().add(launcherInd.getNode());
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
		inventory.show();
	}

	public void hideInventory() {
		this.getChildren().remove(inventory.getNode());
		inventory.hide();

	}

	public void update() {
		if (!pause) {
			fieldScene.update();
			inventory.draw();
			launcherInd.draw();
			if(inventory.isHidden() && !inventoryIsHide())
				hideInventory();
			
			equippedWeaponInd.draw();
			
			matchManager.update();
			timerInd.draw();
		}
	}

	public void setLauncherPower(float launchPower) {
		launcherInd.setLauncherPower(launchPower);
	}

	public void launchKeyPressed() {
		if(matchManager.getCurrentPlayer().getLauncher().isActivated())
		launcherInd.launchKeyPressed();
	}
}
