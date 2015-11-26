package gui;

import utils.Point;
import game.MatchManager;
import gui.hud.IndicatorOfEquippedWeapon;
import gui.hud.IndicatorOfLauncher;
import gui.hud.IndicatorOfPlayerLife;
import gui.hud.IndicatorOfTime;
import gui.hud.Inventory;
import gui.hud.MessageNextPlayer;
import javafx.scene.Camera;
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

	private MessageNextPlayer paneForNextTurn;

	public MatchPane(MatchManager matchManager) {
		this.matchManager = matchManager;

		this.fieldPane = new FieldPane(matchManager);
		this.fieldScene = new FieldScene(fieldPane, matchManager, fieldPane.getWidth(),
				fieldPane.getHeight());
//		this.fieldScene = new FieldScene(fieldPane, matchManager, Screen.getPrimary().getBounds().getWidth(),Screen.getPrimary().getBounds().getHeight());
		this.fieldScene.setLayoutX(Screen.getPrimary().getBounds().getWidth()/2-fieldPane.getWidth()/2);
		this.fieldScene.setLayoutY(Screen.getPrimary().getBounds().getHeight()/2-fieldPane.getHeight()/2);
		this.timerInd = new IndicatorOfTime(matchManager);
		this.inventory = new Inventory(matchManager);
		this.equippedWeaponInd = new IndicatorOfEquippedWeapon(matchManager);
		this.launcherInd = new IndicatorOfLauncher(matchManager);
		
		fieldPane.setScene(fieldScene);
		
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
			if (fieldScene.getMovementNextPlayer())
			{
				if (!this.getChildren().contains(paneForNextTurn))
				{
					paneForNextTurn = new MessageNextPlayer(matchManager.getCurrentPlayer().getName(), "0a5fbf", false);
					this.getChildren().add(paneForNextTurn);
				}
				fieldScene.update();
			}
			else
			{
				fieldScene.update();
				inventory.draw();
				launcherInd.draw();
				if(inventory.isHidden() && !inventoryIsHide())
					hideInventory();
				
				equippedWeaponInd.draw();
				if (paneForNextTurn != null && this.getChildren().contains(paneForNextTurn))
					this.getChildren().remove(paneForNextTurn);
			}
//			matchManager.update();
			timerInd.draw();
		}
		else {
			if (!this.getChildren().contains(paneForNextTurn))
			{
				paneForNextTurn = new MessageNextPlayer("Pause","d95208", true);
				this.getChildren().add(paneForNextTurn);
			}
		}
	}

	public void setLauncherPower(float launchPower) {
		launcherInd.setLauncherPower(launchPower);
	}

	public void launchKeyPressed() {
		if(matchManager.getCurrentPlayer().getLauncher().isActivated())
		launcherInd.launchKeyPressed();
	}
	
	public Point getCameraPosition() {
		return fieldScene.getCameraPosition();
	}
	
	public void focusPlayer(boolean focus) {
		fieldScene.setFocusPlayer(focus);
	}
	
	public Camera getCamera() {
		return fieldScene.getCamera();
	}
	
	public double getZoomCamera() {
		return fieldScene.getZoom();
	}
	
	public void setZoomOutCamera(boolean zoom) {
		fieldScene.setZoom(zoom);
	}
}
