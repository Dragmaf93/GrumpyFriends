package menu.teamMenu;

import game.GameManager;
import gui.ImageLoader;
import gui.Popup;
import menu.MenuButton;
import menu.MenuManager;
import menu.MenuPage;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

public class TeamPage extends Pane implements MenuPage {

	private final static double PADDING_HEIGHT = 100;
	private final static double PADDING_WIDTH = 150;
	
	private final static double SPACING_BUTTON = 20;
	
	private final static double BUTTON_HEIGHT = 60;
	private final static double BUTTON_WIDTH = 	250;

	private VBox buttons;
	private Pane root;
	
	private TeamName teamName;
	private TeamTypeSelector teamTypeSelector;

	private MenuButton continueButton;
	private MenuButton backButton;
	private PlayerName playerName;

	private Scene scene;
	
	private ImageLoader imageLoader;
	private GameManager gameManager;
	private MenuManager menuManager;
	
	private boolean teamA;
	
	private Popup messagePopup;
	
	public TeamPage(boolean teamA) {

		this.teamA = teamA;
		
		this.setStyle("-fx-background: #ece6e6; -fx-background-color: rgba(25,81,81,0.6);");
	
		this.imageLoader = new ImageLoader();
		this.gameManager = GameManager.getIstance();
		this.menuManager = MenuManager.getIstance();
		
		teamName = new TeamName(this);
		teamTypeSelector = new TeamTypeSelector(this);
		playerName = new PlayerName(this, imageLoader);
		
		continueButton = new MenuButton(BUTTON_WIDTH, BUTTON_HEIGHT, "CONTINUE");
		backButton = new MenuButton(BUTTON_WIDTH, BUTTON_HEIGHT, "BACK");

		buttons = new VBox(SPACING_BUTTON);
		buttons.getChildren().addAll(continueButton,backButton);
		
		root = new Pane(teamName,teamTypeSelector,playerName,buttons);
		root.setPrefSize(Screen.getPrimary().getBounds().getWidth()-PADDING_WIDTH*2, Screen.getPrimary().getBounds().getHeight()-PADDING_HEIGHT*2);
		teamTypeSelector.relocate(teamTypeSelector.getLayoutX(),
				root.getPrefHeight()-teamTypeSelector.getHeightComponent());
		playerName.relocate(root.getPrefWidth()-playerName.getWidthComponent(), 
				playerName.getLayoutY());
		buttons.relocate(root.getPrefWidth()-BUTTON_WIDTH-BUTTON_WIDTH/2-30 , root.getPrefHeight()-(BUTTON_HEIGHT*2+SPACING_BUTTON)-10);
		
		
		StackPane p = new StackPane(root);
		p.setPadding(new Insets(PADDING_HEIGHT-20,PADDING_WIDTH,PADDING_HEIGHT,PADDING_WIDTH));
		
		this.getChildren().add(new StackPane(p));
		
		scene = new Scene(this,Screen.getPrimary().getBounds().getWidth(),
				Screen.getPrimary().getBounds().getHeight());
		
		menuManager.setPlayerName(playerName);
		
		
		backButton.setOnMouseReleased(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				backPage();
			}
		});
		
		continueButton.setOnMouseReleased(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				nextPage();
			}
		});
		
	}
	
	public MenuManager getMenuManager() {
		return menuManager;
	}
	
	public PlayerName getPlayerName() {
		return playerName;
	}
	
	public TeamName getTeamName() {
		return teamName;
	}
	
	public TeamTypeSelector getTeamType() {
		return teamTypeSelector;
	}
	
	public boolean isAllInsert() {
		if (playerName.isInsert() && teamName.isInsert())
			return true;
		return false;
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
	}

	@Override
	public void nextPage() {
		if (isAllInsert())
		{
			messagePopup = new Popup(300, 200, "Are you sure?", "Cancel", "Accept");
			messagePopup.changeColor();
			root.getChildren().add(messagePopup);
			messagePopup.getRightButton().setOnMouseReleased(new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					if (teamA)
						menuManager.setSceneForChangePage(menuManager.getTeamPageBScene());
					else
						menuManager.setSceneForChangePage(menuManager.getWorldScene());
					
					root.getChildren().remove(messagePopup);
				}
			});
			messagePopup.getLeftButton().setOnMouseReleased(new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					root.getChildren().remove(messagePopup);
				}
			});
		}
		else
		{
			messagePopup = new Popup(300, 200, "Missing Value", "", "Ok");
			messagePopup.changeColorForMissingValue();
			root.getChildren().add(messagePopup);
			messagePopup.getRightButton().setOnMouseReleased(new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					root.getChildren().remove(messagePopup);
				}
			});
		}
	}

	@Override
	public void backPage() {
		if (!teamA)
			menuManager.setSceneForChangePage(menuManager.getTeamPageAScene());
		else
			menuManager.setMenuScene();
	}

}
