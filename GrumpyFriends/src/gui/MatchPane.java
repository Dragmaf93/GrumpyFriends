package gui;

import character.Team;
import utils.Point;
import game.MatchManager;
import game.TurnPhaseType;
import gui.hud.IndicatorOfEquippedWeapon;
import gui.hud.IndicatorOfLauncher;
import gui.hud.IndicatorOfTeamLife;
import gui.hud.IndicatorOfTime;
import gui.hud.Inventory;
import gui.hud.MessageNextPlayer;
import javafx.scene.Camera;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class MatchPane extends Pane {

	private final static Color BACKGROUND_COLOR = new Color(30d / 255d, 127d / 255d, 169d / 255d, 0.9d);

	private IndicatorOfTime timerInd;
	private IndicatorOfLauncher launcherInd;
	private IndicatorOfEquippedWeapon equippedWeaponInd;
	private IndicatorOfTeamLife teamLifeInd;
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
		this.teamLifeInd = new IndicatorOfTeamLife(matchManager);
		
		fieldPane.setScene(fieldScene);
		
		this.getChildren().add(fieldScene);
		this.getChildren().add(timerInd.getNode());
		this.getChildren().add(equippedWeaponInd.getNode());
		this.getChildren().add(launcherInd.getNode());
		this.getChildren().add(teamLifeInd.getNode());
		pause = false;
		
		this.setBackground(new Background(new BackgroundImage(fieldPane.getImageLoader().getImageBackgrounds("Planet"),BackgroundRepeat.ROUND,
				BackgroundRepeat.ROUND, BackgroundPosition.DEFAULT,
				new BackgroundSize(100,100,true,true,true,false))));

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
		if (pause) {
			if (!this.getChildren().contains(paneForNextTurn))
			{
				paneForNextTurn = new MessageNextPlayer("Pause","d95208", true);
				this.getChildren().add(paneForNextTurn);
			}
		}
		else if (matchManager.isMatchFinished() && matchManager.getCurrentTurnPhase() == TurnPhaseType.END_PHASE){
			String winnerString ="";
			if(matchManager.hasWinnerTeam())
				winnerString = "Il vincitore è :"+ matchManager.getWinnerTeam().getName();
			
			else{
				Team winner = matchManager.getTeamWithMaxLifePoints();
				
				if(winner == null) winnerString = "Pareggio";
				else
					winnerString = "Il vincitore è :"+ matchManager.getWinnerTeam().getName();
			}
			this.getChildren().add(new MessageNextPlayer(winnerString,"d95208" , true));

			
			
		}
		else {
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
		teamLifeInd.draw();
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
