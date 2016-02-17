package menu.teamMenu;

import menu.AbstractMenuPage;
import menu.GameBean;
import menu.MenuButton;
import menu.MenuManager;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

public class TeamPage extends AbstractMenuPage {

	private final static double SPACING_BUTTON = 20;
	
	private final static double BUTTON_HEIGHT = 60;
	private final static double BUTTON_WIDTH = 	250;

	private VBox buttons;
	
	private TeamName teamName;
	private TeamTypeSelector teamTypeSelector;

	private MenuButton continueButton;
	private MenuButton backButton;
	private PlayerName playerName;

	
	public TeamPage() {
		
		teamName = new TeamName(this);
		teamTypeSelector = new TeamTypeSelector(this);
		playerName = new PlayerName(this);
		
		continueButton = new MenuButton(BUTTON_WIDTH, BUTTON_HEIGHT, "CONTINUE");
		backButton = new MenuButton(BUTTON_WIDTH, BUTTON_HEIGHT, "BACK");

		buttons = new VBox(SPACING_BUTTON);
		buttons.getChildren().addAll(continueButton,backButton);
		
		root.getChildren().addAll(teamName,teamTypeSelector,playerName,buttons);
//		root.setPrefSize(Screen.getPrimary().getBounds().getWidth()-PADDING_WIDTH*2, Screen.getPrimary().getBounds().getHeight()-PADDING_HEIGHT*2);
		teamTypeSelector.relocate(teamTypeSelector.getLayoutX(),
				root.getPrefHeight()-teamTypeSelector.getHeightComponent());
		playerName.relocate(root.getPrefWidth()-playerName.getWidthComponent(), 
				playerName.getLayoutY());
		buttons.relocate(root.getPrefWidth()-BUTTON_WIDTH-BUTTON_WIDTH/2-30 , root.getPrefHeight()-(BUTTON_HEIGHT*2+SPACING_BUTTON)-10);
		
		changeHeadType(teamTypeSelector.getType());
		
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
	
	public String getTeamType() {
		return teamTypeSelector.getType();
	}
	
	public boolean isAllInsert() {
		if (playerName.isInsert() && teamName.isInsert())
			return true;
		return false;
	}
	
	@Override
	public void reset() {
		teamTypeSelector.reset();
		playerName.reset();
		teamName.reset();
	}

	@Override
	public void nextPage() {
//		if (isAllInsert())
//		{
//			messagePopup = new Popup(300, 200, "Are you sure?", "Cancel", "Accept");
//			messagePopup.changeColor();
//			root.getChildren().add(messagePopup);
//			messagePopup.getRightButton().setOnMouseReleased(new EventHandler<Event>() {
//
//				@Override
//				public void handle(Event event) {
//					if (teamA)
//						menuManager.setSceneForChangePage(menuManager.getTeamPageBScene());
//					else
//						menuManager.setSceneForChangePage(menuManager.getWorldScene());
//					
//					root.getChildren().remove(messagePopup);
//				}
//			});
//			messagePopup.getLeftButton().setOnMouseReleased(new EventHandler<Event>() {
//
//				@Override
//				public void handle(Event event) {
//					root.getChildren().remove(messagePopup);
//				}
//			});
//		}
//		else
//		{
//			messagePopup = new Popup(300, 200, "Missing Value", "", "Ok");
//			messagePopup.changeColorForMissingValue();
//			root.getChildren().add(messagePopup);
//			messagePopup.getRightButton().setOnMouseReleased(new EventHandler<Event>() {
//
//				@Override
//				public void handle(Event event) {
//					root.getChildren().remove(messagePopup);
//				}
//			});
//		}
		
		MenuManager.getInstance().nextPage();
	}

	@Override
	public void backPage() {
		MenuManager.getInstance().previousPage();
	}
	
	@Override
	public GameBean getGameBean() {
	
		GameBean bean = new GameBean("Team");
		
		bean.addData("TeamName",teamName.getValues()[0]);
		
		String[] playersName = playerName.getValues();
		for (int i = 0; i <playersName.length;i++) {
			bean.addData("PlayerName"+i, playersName[i]);
		}
		
		bean.addData("TypeTeam", teamTypeSelector.getValues()[0]);
		System.out.println("TEAM PAGE "+bean.toJSON());
		return bean;
	}

	public void changeHeadType(String typeCharacter) {
		playerName.checkType(typeCharacter);
	}

	@Override
	public void update() {
		teamTypeSelector.update();
	}

}
