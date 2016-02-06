package gui;

import utils.Point;
import game.MatchManager;
import game.TurnPhaseType;
import gui.drawer.BackgroundPane;
import gui.event.KeyboardPressedEventHandler;
import gui.event.KeyboardReleaseEventHandler;
import gui.hud.IndicatorOfEquippedWeapon;
import gui.hud.IndicatorOfLauncher;
import gui.hud.IndicatorOfTeamLife;
import gui.hud.IndicatorOfTime;
import gui.hud.Inventory;
import gui.hud.MessageNextPlayer;
import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.SubScene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

public class MatchPane extends Pane implements UpdatablePane{

	private IndicatorOfTime timerInd;
	private IndicatorOfLauncher launcherIndicator;
	private IndicatorOfEquippedWeapon equippedWeaponInd;
	private IndicatorOfTeamLife teamLifePointsIndicator;
	private Inventory inventory;

	private PausePane pausePane;
	private WinnerPane winnerPane;

	private MatchManager matchManager;

	private FieldPane fieldPane;
	private FieldScene fieldScene;
	
	private BackgroundPane backgroundPane;

	private boolean pause;

	private MessageNextPlayer paneForNextTurn;

	public MatchPane(MatchManager matchManager) {
		this.matchManager = matchManager;

		this.fieldPane = new FieldPane(matchManager);
		this.fieldScene = new FieldScene(fieldPane, matchManager,
				fieldPane.getWidth(), fieldPane.getHeight());
		this.fieldScene.setLayoutX(Screen.getPrimary().getBounds().getWidth()
				/ 2 - fieldPane.getWidth() / 2);
		this.fieldScene.setLayoutY(Screen.getPrimary().getBounds().getHeight()
				/ 2 - fieldPane.getHeight() / 2);

		this.timerInd = new IndicatorOfTime(this, matchManager);
		this.inventory = new Inventory(this, matchManager);
		this.equippedWeaponInd = new IndicatorOfEquippedWeapon(this,
				matchManager);
		this.launcherIndicator = new IndicatorOfLauncher(this, matchManager);
		this.teamLifePointsIndicator = new IndicatorOfTeamLife(this,
				matchManager);
		this.pausePane = new PausePane(this, matchManager);
		this.winnerPane = new WinnerPane(this, matchManager);

		fieldPane.setScene(fieldScene);
		
		backgroundPane = new BackgroundPane(fieldPane.getImageLoader());
//		SubScene sub = new SubScene(backgroundPane, fieldPane.getWidth(),fieldPane.getHeight());
		
		this.getChildren().add(backgroundPane);
		this.getChildren().add(fieldScene);
		this.getChildren().add(timerInd.getNode());
		this.getChildren().add(equippedWeaponInd.getNode());
		this.getChildren().add(launcherIndicator.getNode());
		this.getChildren().add(teamLifePointsIndicator.getNode());

		pause = false;

//		this.setBackground(new Background(new BackgroundImage(fieldPane
//				.getImageLoader().getImageBackgrounds(),
//				BackgroundRepeat.ROUND, BackgroundRepeat.ROUND,
//				BackgroundPosition.DEFAULT, new BackgroundSize(100, 100, true,
//						true, true, false))));
		
		
		
		setOnKeyPressed(new KeyboardPressedEventHandler(this,matchManager));
		setOnKeyReleased(new KeyboardReleaseEventHandler(this, matchManager));
		setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				if (!matchManager.isPaused()) {
					if (event.getDeltaY() > 0) {
						MatchPane.this.setZoomOutCamera(true);
						MatchPane.this.getCamera().setTranslateZ(MatchPane.this.getZoomCamera());
					}
					if (event.getDeltaY() < 0) {
						MatchPane.this.setZoomOutCamera(false);
						MatchPane.this.getCamera().setTranslateZ(MatchPane.this.getZoomCamera());
					}
				}
			}
		});
	}

	public void restartMatch() {
		if (this.getChildren().contains(pausePane)) {
			this.getChildren().remove(pausePane);
			timerInd.getNode().setVisible(true);
			equippedWeaponInd.getNode().setVisible(true);
			teamLifePointsIndicator.getNode().setVisible(true);
			launcherIndicator.getNode().setVisible(true);
		}
		if (this.getChildren().contains(winnerPane))
			this.getChildren().remove(winnerPane);
		inventory.hide();
		pause = false;
		matchManager.restartMatch();
		fieldPane.reset();
		winnerPane.reset();
		equippedWeaponInd.reset();
		teamLifePointsIndicator.reset();
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

	public FieldScene getFieldScene() {
		return fieldScene;
	}

	public void showInventory() {
		this.getChildren().add(inventory.getNode());
		inventory.show();
	}

	public void hideInventory() {
		this.getChildren().remove(inventory.getNode());
		inventory.hide();

	}
	
	@Override
	public void update() {
		if (pause) {
			if (!this.getChildren().contains(pausePane)) {
				this.getChildren().add(pausePane);
				timerInd.getNode().setVisible(false);
				equippedWeaponInd.getNode().setVisible(false);
				teamLifePointsIndicator.getNode().setVisible(false);
				launcherIndicator.getNode().setVisible(false);
			}
		} else {
			if (this.getChildren().contains(pausePane)) {
				this.getChildren().remove(pausePane);
				timerInd.getNode().setVisible(true);
				equippedWeaponInd.getNode().setVisible(true);
				teamLifePointsIndicator.getNode().setVisible(true);
				launcherIndicator.getNode().setVisible(true);
			}

			if (matchManager.isMatchFinished()
					&& matchManager.getCurrentTurnPhase() == TurnPhaseType.END_PHASE
					&& !this.getChildren().contains(winnerPane)) {
				winnerPane.draw();
				this.getChildren().add(winnerPane);

			} else {
				if (fieldScene.getMovementNextPlayer()) {
					if (!this.getChildren().contains(paneForNextTurn)) {
						paneForNextTurn = new MessageNextPlayer(matchManager
								.getCurrentPlayer().getName(), "0a5fbf", false);
						this.getChildren().add(paneForNextTurn);
					}
					fieldScene.update();
				} else {
					fieldScene.update();
					inventory.draw();
					launcherIndicator.draw();
					if (inventory.isHidden() && !inventoryIsHide())
						hideInventory();

					equippedWeaponInd.draw();
					if (paneForNextTurn != null
							&& this.getChildren().contains(paneForNextTurn))
						this.getChildren().remove(paneForNextTurn);
				}
				// matchManager.update();
				timerInd.draw();
			}
			teamLifePointsIndicator.draw();
		}
	}

	public void setLauncherPower(float launchPower) {
		launcherIndicator.setLauncherPower(launchPower);
	}

	public void launchKeyPressed() {
		if (matchManager.getCurrentPlayer().getLauncher().isActivated())
			launcherIndicator.launchKeyPressed();
		
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
